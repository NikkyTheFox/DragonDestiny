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

@Component({
  selector: 'app-game-controls-options',
  templateUrl: './game-controls-options.component.html',
  styleUrls: ['./game-controls-options.component.css']
})
export class GameControlsOptionsComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  round!: Round;
  TAKE_CARD_FLAG: boolean = false;
  numberOfCardsToBeDrawn: number = 0;
  LOSE_ROUND_FLAG: boolean = false;
  numberOfTurnsToBeLost: number = 0;
  BRIDGE_FIELD_FLAG: boolean = false; // WHILE ON BOSS => Go to bridge field & then fight it | OTHERWISE attack bridge guardian
  attackBridgeGuardianFlag: boolean = false;
  BOSS_FIELD_FLAG: boolean = false; // WHILE ON BRIDGE => Go to boss field & Then fight it | OTHERWISE attack boss
  attackBossFlag: boolean = false;
  FIGHT_WITH_PLAYER_FLAG: boolean = false;
  FIGHT_WITH_ENEMY_ON_FIELD_FLAG: boolean = false;

  playersToAttack: Player[] = [];
  playersToAttackCharacters: Character[] = [];
  // enemiesToAttack: EnemyCard[] = [];
  enemyToAttack!: EnemyCard | null;
  enemiesToAttackFromEngine: any[] = [];
  enemyToAttackFromEngine!: any;

  // Actions done flags:
  actionButtonClickFlag: boolean = true;
  fetchOptionsFlag: boolean = false;

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService,private shared: SharedService){
  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.resetOptions();
    this.fetchRound();
    // this.handleOptions(); // ONLY FOR TEST PURPOSES (SO YOU DONT HAVE TO HIT SPECIAL FIELD) [not anymore?]
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
  }

  fetchRound(){
    this.toDeleteSubscription.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        this.round = data;
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
      this.toDeleteSubscription.push(
        this.playedGameService.getPlayersToFightWith(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: PlayerList) => {
          this.playersToAttack = data.playerList;
          this.fetchPlayersCharacter();
        })
      );
    }
  }

  handleFightEnemyFlag(){
    // if(this.FIGHT_WITH_ENEMY_ON_FIELD_FLAG || this.BOSS_FIELD_FLAG || this.BRIDGE_FIELD_FLAG){
    //   this.toDeleteSubscription.push(
    //     this.playedGameService.getEnemiesToFightWith(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: EnemyCardList) => {
    //       this.enemiesToAttack = data.enemyCardList;
    //       this.fetchEnemies();
    //     })
    //   );
    // }
    if(this.BOSS_FIELD_FLAG && this.attackBossFlag){
      // means that a character stands on the field!
    }
    if(this.BRIDGE_FIELD_FLAG && this.attackBridgeGuardianFlag){
      // means that a character stands on the field!
      this.enemyToAttack = this.round.activePlayer.character.field!.enemy;
      this.fetchEnemy(this.enemyToAttack)
    }
  }

  fetchPlayersCharacter(){
    this.playersToAttack.forEach( (player: Player) => {
      this.toDeleteSubscription.push(
        this.gameEngineService.getCharacter(player.character.id).subscribe( (character: Character) => {
          this.playersToAttackCharacters.push(character);
        })
      );
    });
  }

  fetchEnemies(){
    // this.enemiesToAttack.forEach( (enemy: EnemyCard) => {
    //   this.toDeleteSubscription.push(
    //     this.gameEngineService.getCard(enemy.id).subscribe( (card: Card) => {
    //       this.enemiesToAttackFromEngine.push(card);
    //     })
    //   );
    // });
  }

  fetchEnemy(enemyCard: EnemyCard | null){
    if(enemyCard != null){
      this.toDeleteSubscription.push(
        this.gameEngineService.getCard(enemyCard.id).subscribe( (data: Card) => {
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
          }
          console.log(this.attackBridgeGuardianFlag);
          break;
        case 'BOSS_FIELD':
          this.BOSS_FIELD_FLAG = true;
          if(this.round.activePlayer.character.field!.type == FieldType.BOSS_FIELD){
            // WHILE ON BOSS => Attack Boss = TRUE
            this.attackBossFlag = true;
          }
          break;
        case 'FIGHT_WITH_PLAYER':
          this.FIGHT_WITH_PLAYER_FLAG = true;
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

  attackPlayedClick(enemyPlayedLogin: string){
    this.toDeleteSubscription.push(
      this.playedGameService.selectRoundOpiton(
        this.requestStructure.game!.id,
        this.requestStructure.player!.login,
        FieldOptionEnum.FIGHT_WITH_PLAYER).subscribe( () => {
          this.shared.sendFightPlayerClickEvent(enemyPlayedLogin);
          this.actionButtonClickFlag = true;
        }
      )
    );
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

  resetOptions(){
    this.TAKE_CARD_FLAG = false;
    this.numberOfCardsToBeDrawn = 0;
    this.LOSE_ROUND_FLAG = false;
    this.numberOfTurnsToBeLost = 0;
    this.BRIDGE_FIELD_FLAG = false;
    this.BOSS_FIELD_FLAG = false;
    this.FIGHT_WITH_PLAYER_FLAG = false;
    this.FIGHT_WITH_ENEMY_ON_FIELD_FLAG = false;

    this.playersToAttack= [];
    this.playersToAttackCharacters = [];
    // this.enemiesToAttack = [];
    this.enemiesToAttackFromEngine = [];
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
