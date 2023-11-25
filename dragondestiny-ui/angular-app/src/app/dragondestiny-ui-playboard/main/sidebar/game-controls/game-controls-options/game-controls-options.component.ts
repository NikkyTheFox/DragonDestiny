import { FieldOptionEnum } from './../../../../../interfaces/played-game/field/field-option-enum';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../../services/played-game/played-game-service';
import { SharedService } from '../../../../../services/shared.service';
import { Player } from '../../../../../interfaces/played-game/player/player';
import { Character } from '../../../../../interfaces/game-engine/character/character';
import { GameEngineService } from '../../../../../services/game-engine/game-engine.service';
import { GameDataStructure } from '../../../../../interfaces/game-data-structure';
import { EnemyCard } from '../../../../../interfaces/played-game/card/enemy-card/enemy-card';
import { EnemyCard as EngineEnemyCard } from '../../../../../interfaces/game-engine//card/enemy-card/enemy-card';
import { Card } from '../../../../../interfaces/game-engine/card/card/card';
import { EnemyCardList } from '../../../../../interfaces/played-game/card/enemy-card/enemy-card-list';
import { Subscription } from 'rxjs';
import { PlayerList } from 'src/app/interfaces/played-game/player/player-list';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { RoundState } from 'src/app/interfaces/played-game/round/round-state';
import { FieldOption } from 'src/app/interfaces/played-game/field/field-option';
import { FieldType } from 'src/app/interfaces/game-engine/field/field-type';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { UpdateEnum } from 'src/app/interfaces/played-game/notification/update-enum';

@Component({
  selector: 'app-game-controls-options',
  templateUrl: './game-controls-options.component.html',
  styleUrls: ['./game-controls-options.component.css']
})
export class GameControlsOptionsComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  round!: Round;

  // GameUpdates & Defender POV in Fight
  messageData!: NotificationMessage;
  playerAttackedFlag: boolean = false;
  cardStolenFlag: boolean = false;

  // Option Contidion Flags
  TAKE_CARD_FLAG: boolean = false;
  numberOfCardsToBeDrawn: number = 0;
  LOSE_ROUND_FLAG: boolean = false;
  numberOfTurnsToBeLost: number = 0;
  BRIDGE_FIELD_FLAG: boolean = false; // WHILE ON BOSS => Go to bridge field & then fight it | OTHERWISE attack bridge guardian
  attackBridgeGuardianFlag: boolean = false;
  BOSS_FIELD_FLAG: boolean = false; // WHILE ON BRIDGE => Go to boss field & Then fight it | OTHERWISE attack boss
  attackBossFlag: boolean = false;
  FIGHT_WITH_PLAYER_FLAG: boolean = false;
  player2Login!: string;
  FIGHT_WITH_ENEMY_ON_FIELD_FLAG: boolean = false;

  // Option Data
  playerToAttack!: Player;
  playerToAttackCharacter!: Character;
  enemyToAttack!: EnemyCard | null;
  enemyToAttackFromEngine!: any;

  // Actions Done Flags:
  actionButtonClickFlag: boolean = true;
  fetchOptionsFlag: boolean = false;

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService,private shared: SharedService){
  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.resetOptions();
    this.fetchRound();
    this.toDeleteSubscription.push(
      this.shared.getEndTurnEvent().subscribe( () => {
        this.resetOptions();
        this.fetchRound();
      })
    );
    this.toDeleteSubscription.push(
      this.shared.getMoveCharacterClickEvent().subscribe( () => {
        this.resetOptions();
        this.fetchRound();
      })
    );
    this.toDeleteSubscription.push(
      this.shared.getSocketMessage().subscribe( (data: any) => {
        this.messageData = this.shared.parseNotificationMessage(data);
        this.playerAttackedFlag = this.messageData.notificationOption == NotificationEnum.PLAYER_ATTACKED;
        if(this.playerAttackedFlag){
          // needs to be handled after round data is fetched
          this.fetchRound()
        }
        if(this.messageData.notificationOption == NotificationEnum.PLAYER_WON_GAME){
          this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_WON_GAME, this.messageData.name, null, null, null);
        }
        if(this.messageData.notificationOption == NotificationEnum.PLAYER_DIED){
          this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_DIED, this.messageData.name, null, null, null);
        }
        if(this.messageData.notificationOption == NotificationEnum.PLAYER_BLOCKED){
          this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_BLOCKED, this.messageData.name, null, null, this.messageData.number);
        }
        if(this.messageData.notificationOption == NotificationEnum.PLAYER_FIGHT){
          let winnerLogin: string | null = null;
          let loserLogin: string | null = null;
          let cardId: number | null = null;
          if(this.messageData.bool){ // info about winner player
            winnerLogin = this.messageData.name
            if(this.messageData.number){ // check whether the fight was with enemy card
              cardId = this.messageData.number;
            }
          }
          if(!this.messageData.bool){ // info about loser player
            loserLogin = this.messageData.name;
            if(this.messageData.number){ // check whether the fight was with enemy card
              cardId = this.messageData.number;
            }
          }
          this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_FIGHT, winnerLogin, loserLogin, cardId, null);
        }
        if(this.messageData.notificationOption == NotificationEnum.PLAYER_GOT_ITEM){
          this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_GOT_ITEM, this.messageData.name, null, this.messageData.number, null);
        }
        this.cardStolenFlag = this.messageData.notificationOption == NotificationEnum.CARD_STOLEN;
        if(this.cardStolenFlag){
          // needs to be handled after round data is fetched
          this.fetchRound()
        }
      })
    );
  }

  fetchRound(){
    this.toDeleteSubscription.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        this.round = data;
        // If any player is attacked
        if(this.playerAttackedFlag){
          // If logged in player is attacked -> proceed to the notification-defend
          if(this.round.enemyPlayerFought.login == this.requestStructure.player!.login){ 
            this.handleDefenderPlayer(this.round.activePlayer.login)
          }
          // notify OTHER players (notification-update)
          else {            
            this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_ATTACKED, this.round.activePlayer.login, this.round.enemyPlayerFought.login, null, null);
          }
        }
        else if(this.cardStolenFlag){
          if(this.round.enemyPlayerFought.login == this.requestStructure.player!.login || this.round.activePlayer.login == this.requestStructure.player!.login){
            // Reload HAND cards for fight participants
            this.shared.sendEquipItemCardClickEvent();
          }
          else{
            let winnerLogin: string | null = null;
            let loserLogin: string | null = null;
            if(this.round.activePlayer.character.strength + this.round.playerFightRoll > this.round.enemyPlayerFought.character.strength + this.round.playerFightRoll){
              winnerLogin = this.round.activePlayer.login;
              loserLogin = this.round.enemyPlayerFought.login;
            }
            else{
              winnerLogin = this.round.enemyPlayerFought.login;
              loserLogin = this.round.activePlayer.login;
            }
            // WAIT FOR BACKEND CARD_ID IMPLEMENTATION
            // this.shared.sendUpdateGameEvent(UpdateEnum.CARD_STOLEN, winnerLogin, loserLogin, )
          }
        }
        else{
          if(data.roundState == RoundState.WAITING_FOR_FIELD_OPTIONS){
            /*
            1. WAITING_FOR_FIELD_OPTIONS => getPlayersPossibleActions()
            2. WAITING_FOR_FIELD_ACTION_CHOICE => selectRoundOption()
            */
            this.actionButtonClickFlag = false;
            this.handleOptions();
          }
          else{
            this.handleGameContinue(data);
          }
        }
        
      })
    );
  }

  handleOptions(){
    this.getFieldOptions();
  }

  handleGameContinue(round: Round){
    console.log('Round w control-options')
    console.log(round)
    if(round.roundState == RoundState.WAITING_FOR_FIELD_ACTION_CHOICE){
      this.actionButtonClickFlag = false;
      this.checkOptionsFlags(round.fieldOptionList.possibleOptions)
      this.handleOptionFlags();
    }
    if(round.roundState == RoundState.WAITING_FOR_CARD_DRAWN){ // Draw card
      /*
      1. WAITING_FOR_CARD_DRAWN => drawRandomCard()
      (EnemyCard):
        2. WAITING_FOR_FIGHT_ROLL => rollDice()
        3. WAITING_FOR_ENEMY_ROLL => rollDice()
        4. WAITING_FOR_FIGHT_RESULT => handleFightWithEnemyCard()
      (ItemCard):
        2. WAITING_FOR_CARD_TO_HAND => moveItemCardFromDeckToPlayerHand()
          3. (optional) WAITING_FOR_CARD_TO_USED => moveCardFromPlayerHandToUsedCardDeck() | only when hand is full, queued by method above
      */
      this.shared.sendDrawCardClickEvent(round.fieldOptionChosen.numOfCardsToTake - round.playerNumberOfCardsTaken);      
    }
    if(round.roundState == RoundState.WAITING_FOR_ENEMIES_TO_FIGHT){ // Fight Field Enemy [Boss/Bridge]
      /*
      1. WAITING_FOR_ENEMIES_TO_FIGHT => getEnemiesToFightWith()  | HANDLE HERE
      2. WAITING_FOR_FIGHT_ROLL => rollDie()
      3. WAITING_FOR_ENEMY_ROLL => rollDie()
      4. WAITING_FOR_FIGHT_RESULT => handleFightWithEnemyCard()
      */ 
      this.toDeleteSubscription.push(
        this.playedGameService.getEnemiesToFightWith(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: EnemyCardList) => {
          this.shared.sendFightEnemyCardClickEvent(data.enemyCardList[0].id); // check
        })
      );
      // this.shared.sendFightEnemyCardClickEvent(round.enemyFought.id); // Check
    }
    if(round.roundState == RoundState.WAITING_FOR_PLAYER_TO_FIGHT){ // Fight Player
      /*
      1. WAITING_FOR_PLAYER_TO_FIGHT => getPlayersToFightWith() | HANDLE HERE | SENDS websocked PLAYER_ATTACKED
      2. WAITING_FOR_FIGHT_ROLL => rollDie()
      3. WAITING_FOR_ENEMY_PLAYER_ROLL =>
      4. WAITING_FOR_FIGHT_RESULT => handleFightWithPlayer() | this sends info via websocket
      5. WAITING_FOR_CARD_THEFT => 
      */
      this.toDeleteSubscription.push(
        this.playedGameService.getPlayersToFightWith(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: PlayerList) => {
          this.shared.sendFightPlayerClickEvent(data.playerList[0].login);
        })
      );
    }
    if(round.roundState == RoundState.WAITING_FOR_FIGHT_ROLL || // Enemy Or EnemyCard Or Player
      round.roundState == RoundState.WAITING_FOR_ENEMY_ROLL || // Enemy Or EnemyCard
      round.roundState == RoundState.WAITING_FOR_FIGHT_RESULT || // Enemy Or EnemyCard OrPlayer
      round.roundState == RoundState.WAITING_FOR_ENEMY_PLAYER_ROLL || // Player
      round.roundState == RoundState.WAITING_FOR_CARD_TO_HAND || // Item Card 
      round.roundState == RoundState.WAITING_FOR_CARD_TO_USED || // Item Card but hand is full
      round.roundState == RoundState.WAITING_FOR_CARD_TO_TROPHIES || // EnemyCard
      round.roundState == RoundState.WAITING_FOR_CARD_THEFT // Player
      ){
        this.shared.sendContinuteGameEvent(round); // Process all data from 'middle of action' in notification
      }
  }

  getFieldOptions(){
    this.toDeleteSubscription.push(
      this.playedGameService.getPlayersPossibleActions(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: any) => {
        this.checkOptionsFlags(data.possibleOptions);
        console.log(data);
        this.handleOptionFlags();
      })
    );
  }

  handleFightPlayerFlag(){
    if(this.FIGHT_WITH_PLAYER_FLAG){
      this.fetchPlayersCharacter();
    }
  }

  handleFightEnemyFlag(){
    if(this.BOSS_FIELD_FLAG || this.BRIDGE_FIELD_FLAG){
      // means that a character stands on the field!
      this.enemyToAttack = this.round.activePlayer.character.field!.enemy;
      this.fetchEnemy()
    }
  }

  fetchPlayersCharacter(){
    this.toDeleteSubscription.push(
      this.gameEngineService.getCharacter(this.playerToAttack.character.id).subscribe( (character: Character) => {
        this.playerToAttackCharacter = character;
      })
    );
  }

  fetchEnemy(){
    if(this.enemyToAttack != null){
      this.toDeleteSubscription.push(
        this.gameEngineService.getCard(this.enemyToAttack.id).subscribe( (data: Card) => {
          this.enemyToAttackFromEngine = data;
        })
      );
    }
  }

  checkOptionsFlags(optionList: FieldOption[]){
    optionList.forEach( (option) => {
      switch (option.fieldOptionEnum){
        case 'TAKE_ONE_CARD':
          this.TAKE_CARD_FLAG = true;
          this.numberOfCardsToBeDrawn = 1;
          break;
        case 'TAKE_TWO_CARDS':
          this.TAKE_CARD_FLAG = true;
          this.numberOfCardsToBeDrawn = 2;
          break;
        case 'LOSE_ONE_ROUND':
          this.LOSE_ROUND_FLAG = true;
          this.numberOfTurnsToBeLost = 1;
          break;
        case 'LOSE_TWO_ROUNDS':
          this.LOSE_ROUND_FLAG = true;
          this.numberOfTurnsToBeLost = 2;
          break;
        case 'BRIDGE_FIELD':
          this.BRIDGE_FIELD_FLAG = true;
          console.log('bridge values check');
          console.log(this.BRIDGE_FIELD_FLAG);
          if(this.round.activePlayer.character.field!.type == FieldType.BRIDGE_FIELD){
            // WHILE ON BRIDGE => Attack Guardian = TRUE
            this.attackBridgeGuardianFlag = true;
            // OTHERWISE can move to bridge
          }
          break;
        case 'BOSS_FIELD':
          this.BOSS_FIELD_FLAG = true;
          if(this.round.activePlayer.character.field!.type == FieldType.BOSS_FIELD){
            // WHILE ON BOSS => Attack Boss = TRUE
            this.attackBossFlag = true;
            // OTHERWISE can move to boss
          }
          break;
        case 'FIGHT_WITH_PLAYER':
          this.FIGHT_WITH_PLAYER_FLAG = true;
          this.playerToAttack = option.enemyPlayer;
          break;
        case 'FIGHT_WITH_ENEMY_ON_FIELD':
          this.FIGHT_WITH_ENEMY_ON_FIELD_FLAG = true;
          break;
      }
    });
  }

  handleOptionFlags(){
    this.handleFightPlayerFlag();
    this.handleFightEnemyFlag();
  }

  takeCardClick(){
    if(this.numberOfCardsToBeDrawn == 1){
      this.toDeleteSubscription.push(
        this.playedGameService.selectRoundOpiton(
          this.requestStructure.game!.id, 
          this.requestStructure.player!.login, 
          FieldOptionEnum.TAKE_ONE_CARD).subscribe( () => {
            this.shared.sendDrawCardClickEvent(this.numberOfCardsToBeDrawn);
            this.actionButtonClickFlag = true;
        })
      );
    };
    if(this.numberOfCardsToBeDrawn == 2){
      this.toDeleteSubscription.push(
        this.playedGameService.selectRoundOpiton(
          this.requestStructure.game!.id, 
          this.requestStructure.player!.login, 
          FieldOptionEnum.TAKE_TWO_CARDS).subscribe( () => {
            this.shared.sendDrawCardClickEvent(this.numberOfCardsToBeDrawn);
            this.actionButtonClickFlag = true;
        })
      );
    };
  }

  loseTurnClick(){
    if(this.numberOfTurnsToBeLost == 1){
      this.toDeleteSubscription.push(
        this.playedGameService.selectRoundOpiton(
          this.requestStructure.game!.id,
          this.requestStructure.player!.login,
          FieldOptionEnum.LOSE_ONE_ROUND).subscribe( () => {
            this.toDeleteSubscription.push(
              this.playedGameService.automaticallyBlockTurnsOfPlayer(
                this.requestStructure.game!.id, 
                this.requestStructure.player!.login).subscribe( () => {
                  this.shared.sendBlockTurnEvent();
                  this.actionButtonClickFlag = true;
                })
            );
          })
      );
    };
    if(this.numberOfTurnsToBeLost == 2){
      this.toDeleteSubscription.push(
        this.playedGameService.selectRoundOpiton(
          this.requestStructure.game!.id,
          this.requestStructure.player!.login,
          FieldOptionEnum.LOSE_TWO_ROUNDS).subscribe( () => {
            this.toDeleteSubscription.push(
              this.playedGameService.automaticallyBlockTurnsOfPlayer(
                this.requestStructure.game!.id, 
                this.requestStructure.player!.login).subscribe( () => {
                  this.shared.sendBlockTurnEvent();
                  this.actionButtonClickFlag = true;
                })
            );
          })
      );
    };
  }

  attackPlayedClick(defenderPlayerLogin: string){
    this.toDeleteSubscription.push(
      this.playedGameService.selectRoundOpiton(
        this.requestStructure.game!.id,
        this.requestStructure.player!.login,
        FieldOptionEnum.FIGHT_WITH_PLAYER).subscribe( () => {
          this.shared.sendNotifyAttackerPlayerEvent(defenderPlayerLogin);
          this.actionButtonClickFlag = true;
        }
      )
    );
  }

  handleDefenderPlayer(attackerPlayerLogin: string){
    this.shared.sendNotifyDefenderPlayerEvent(attackerPlayerLogin);
  }

  attackEnemyClick(enemyCardId: number){
    this.toDeleteSubscription.push(
      this.playedGameService.selectRoundOpiton(
        this.requestStructure.game!.id,
        this.requestStructure.player!.login,
        FieldOptionEnum.FIGHT_WITH_ENEMY_ON_FIELD).subscribe( () => {
          this.shared.sendFightEnemyCardClickEvent(enemyCardId);
          this.actionButtonClickFlag = true;
        })
    );
  }

  attackBridgeGuardian(enemyCardId: number){
    this.toDeleteSubscription.push(
      this.playedGameService.selectRoundOpiton(
        this.requestStructure.game!.id,
        this.requestStructure.player!.login,
        FieldOptionEnum.BRIDGE_FIELD).subscribe( () => {
          this.shared.sendFightEnemyCardClickEvent(enemyCardId);
          this.actionButtonClickFlag = true;
        })
    );
  }

  moveToBridge(){
    // TO DISCUSS WITH BACKEND (does it move a character?)
  }

  attackBoss(enemyCardId: number){
    this.toDeleteSubscription.push(
      this.playedGameService.selectRoundOpiton(
        this.requestStructure.game!.id,
        this.requestStructure.player!.login,
        FieldOptionEnum.BOSS_FIELD).subscribe( () => {
          this.shared.sendFightEnemyCardClickEvent(enemyCardId);
          this.actionButtonClickFlag = true;
        })
    );
  }

  moveToBoss(){
    // TO DISCUSS WITH BACKEND (does it move a character?)
  }

  resetOptions(){
    // GameUpdates & Defender POV in Fight
    this.playerAttackedFlag = false;
    this.cardStolenFlag = false;

    // Option Contidion Flags
    this.TAKE_CARD_FLAG = false;
    this.numberOfCardsToBeDrawn = 0;
    this.LOSE_ROUND_FLAG = false;
    this.numberOfTurnsToBeLost = 0;
    this.BRIDGE_FIELD_FLAG = false;
    this.BOSS_FIELD_FLAG = false;
    this.FIGHT_WITH_PLAYER_FLAG = false;
    this.FIGHT_WITH_ENEMY_ON_FIELD_FLAG = false;
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
