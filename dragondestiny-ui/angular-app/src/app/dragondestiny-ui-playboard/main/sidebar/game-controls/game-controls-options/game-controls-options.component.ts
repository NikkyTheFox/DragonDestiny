import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../../services/played-game/played-game-service';
import { SharedService } from '../../../../../services/shared.service';
import { Player } from '../../../../../interfaces/played-game/player/player';
import { Character } from '../../../../../interfaces/game-engine/character/character';
import { GameEngineService } from '../../../../../services/game-engine/game-engine.service';
import { GameDataStructure } from '../../../../../interfaces/game-data-structure';
import { EnemyCard } from '../../../../../interfaces/played-game/card/enemy-card/enemy-card';
import { Card } from '../../../../../interfaces/game-engine/card/card/card';
import { EnemyCardList } from '../../../../../interfaces/played-game/card/enemy-card/enemy-card-list';
import { Subscription } from 'rxjs';
import { PlayerList } from 'src/app/interfaces/played-game/player/player-list';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { RoundState } from 'src/app/interfaces/played-game/round/round-state';
import { FieldOption } from 'src/app/interfaces/played-game/field/field-option';

@Component({
  selector: 'app-game-controls-options',
  templateUrl: './game-controls-options.component.html',
  styleUrls: ['./game-controls-options.component.css']
})
export class GameControlsOptionsComponent implements OnInit, OnDestroy{
  moveClickEventSubscription!: Subscription;
  checkActionsSubscription!: Subscription;
  playersFightSubscription!: Subscription;
  enemyFightSubscription!: Subscription;
  roundSubscription!: Subscription;
  tempSubscription!: Subscription;
  endTurnSubscription!: Subscription;
  selectOptionSubscription!: Subscription;
  loseRoundSubscription!: Subscription;
  characterSubscriptionList: Subscription[] = [];
  enemySubscriptionList: Subscription[] = [];
  requestStructure!: GameDataStructure;

  TAKE_CARD_FLAG: boolean = false;
  numberOfCardsToBeDrawn: number = 0;
  LOSE_ROUND_FLAG: boolean = false;
  numberOfTurnsToBeLost: number = 0;
  BRIDGE_FIELD_FLAG: boolean = false;
  BOSS_FIELD_FLAG: boolean = false;
  FIGHT_WITH_PLAYER_FLAG: boolean = false;
  FIGHT_WITH_ENEMY_ON_FIELD_FLAG: boolean = false;

  playersToAttack: Player[] = [];
  playersToAttackCharacters: Character[] = [];
  enemiesToAttack: EnemyCard[] = [];
  enemiesToAttackFromEngine: any[] = [];

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
    this.endTurnSubscription = this.shared.getEndTurnEvent().subscribe( () => {
      this.resetOptions();
      this.fetchRound();
    });
    this.moveClickEventSubscription = this.shared.getMoveCharacterClickEvent().subscribe( () => {
      this.resetOptions();
      this.fetchRound();
    });
  }

  fetchRound(){
    this.roundSubscription = this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
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
    });
  }

  handleOptions(){
    this.getFieldOptions();
  }

  handleGameContinue(round: Round){
    console.log(round)
    if(round.roundState == RoundState.WAITING_FOR_FIELD_ACTION_CHOICE){
      this.actionButtonClickFlag = false;
      this.checkOptions(round.fieldOptionList.possibleOptions as [])
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
      if(round.fieldOptionChosen == FieldOption.TAKE_ONE_CARD){ // Draw one card field
        this.shared.sendDrawCardClickEvent(1 - round.playerNumberOfCardsTaken);
      }
      else{ // Draw two cards field
        this.shared.sendDrawCardClickEvent(2 - round.playerNumberOfCardsTaken);
      }
    }
    if(round.roundState == RoundState.WAITING_FOR_ENEMIES_TO_FIGHT){ // Fight Field Enemy [Boss/Bridge]
      /*
      1. WAITING_FOR_ENEMIES_TO_FIGHT => getEnemiesToFightWith()
      2. WAITING_FOR_FIGHT_ROLL => rollDie()
      3. WAITING_FOR_ENEMY_ROLL => rollDie()
      4. WAITING_FOR_FIGHT_RESULT => handleFightWithEnemyCard()
      */ 
    }
    if(round.roundState == RoundState.WAITING_FOR_PLAYER_TO_FIGHT){ // Fight Player
      /*
      1. WAITING_FOR_PLAYER_TO_FIGHT => getPlayersToFightWith() | should notifiy chosen player by 
      2. WAITING_FOR_FIGHT_ROLL => rollDie()
      3. WAITING_FOR_ENEMY_PLAYER_ROLL =>
      4. WAITING_FOR_FIGHT_RESULT => handleFightWithPlayer() | this sends info via websocket
      5. WAITING_FOR_CARD_THEFT => 
      */
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

      }
  }

  getFieldOptions(){
    this.checkActionsSubscription = this.playedGameService.getPlayersPossibleActions(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: any) => {
      this.checkOptions(data.possibleOptions);
      console.log(data);
      this.handleOptionFlags();
    });
  }

  handleFightPlayerFlag(){
    if(this.FIGHT_WITH_PLAYER_FLAG){
      this.playersFightSubscription = this.playedGameService.getPlayersToFightWith(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: PlayerList) => {
        this.playersToAttack = data.playerList;
        this.fetchPlayersCharacter();
      });
    }
  }

  handleFightEnemyFlag(){
    if(this.FIGHT_WITH_ENEMY_ON_FIELD_FLAG){
      this.enemyFightSubscription = this.playedGameService.getEnemiesToFightWith(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: EnemyCardList) => {
        this.enemiesToAttack = data.enemyCardList;
        this.fetchEnemies();
      });
    }
  }

  fetchPlayersCharacter(){
    this.playersToAttack.forEach( (player: Player) => {
      this.tempSubscription = this.gameEngineService.getCharacter(player.character.id).subscribe( (character: Character) => {
        this.playersToAttackCharacters.push(character);
      });
      this.characterSubscriptionList.push(this.tempSubscription);
    });
  }

  fetchEnemies(){
    this.enemiesToAttack.forEach( (enemy: EnemyCard) => {
      this.tempSubscription = this.gameEngineService.getCard(enemy.id).subscribe( (card: Card) => {
        this.enemiesToAttackFromEngine.push(card);
      });
      this.enemySubscriptionList.push(this.tempSubscription);
    });
  }

  checkOptions(optionList: []){
    optionList.forEach( (option) => {
      switch (option){
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
          break;
        case 'BOSS_FIELD':
          this.BOSS_FIELD_FLAG = true;
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
      this.selectOptionSubscription = this.playedGameService.selectRoundOpiton(
        this.requestStructure.game!.id, 
        this.requestStructure.player!.login, 
        FieldOption.TAKE_ONE_CARD).subscribe( () => {
          this.shared.sendDrawCardClickEvent(this.numberOfCardsToBeDrawn);
          this.actionButtonClickFlag = true;
      });
    };
    if(this.numberOfCardsToBeDrawn == 2){
      this.selectOptionSubscription = this.playedGameService.selectRoundOpiton(
        this.requestStructure.game!.id, 
        this.requestStructure.player!.login, 
        FieldOption.TAKE_TWO_CARDS).subscribe( () => {
          this.shared.sendDrawCardClickEvent(this.numberOfCardsToBeDrawn);
          this.actionButtonClickFlag = true;
      });
    };
  }

  loseTurnClick(){
    this.selectOptionSubscription = this.playedGameService.selectRoundOpiton(
      this.requestStructure.game!.id,
      this.requestStructure.player!.login,
      FieldOption.LOSE_ONE_ROUND).subscribe( () => {
        this.loseRoundSubscription = this.playedGameService.automaticallyBlockTurnsOfPlayer(
          this.requestStructure.game!.id, 
          this.requestStructure.player!.login).subscribe( () => {
            this.shared.sendBlockTurnEvent();
            this.actionButtonClickFlag = true;
          });
      });
    

  }

  attackPlayedClick(enemyPlayedLogin: string){
    this.selectOptionSubscription = this.playedGameService.selectRoundOpiton(
      this.requestStructure.game!.id,
      this.requestStructure.player!.login,
      FieldOption.FIGHT_WITH_PLAYER).subscribe( () => {
        this.shared.sendFightPlayerClickEvent(enemyPlayedLogin);
        this.actionButtonClickFlag = true;
      }
    );
  }

  attackEnemyClick(enemyCardId: number){
    this.selectOptionSubscription = this.playedGameService.selectRoundOpiton(
      this.requestStructure.game!.id,
      this.requestStructure.player!.login,
      FieldOption.FIGHT_WITH_ENEMY_ON_FIELD).subscribe( () => {
        this.shared.sendFightEnemyCardClickEvent(enemyCardId);
        this.actionButtonClickFlag = true;
      });
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
    this.enemiesToAttack = [];
    this.enemiesToAttackFromEngine = [];
  }

  ngOnDestroy(): void {
    this.enemySubscriptionList.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
    this.characterSubscriptionList.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
    this.tempSubscription?.unsubscribe();
    this.enemyFightSubscription?.unsubscribe();
    this.playersFightSubscription?.unsubscribe();
    this.checkActionsSubscription?.unsubscribe();
    this.moveClickEventSubscription?.unsubscribe();
    this.roundSubscription?.unsubscribe();
    this.selectOptionSubscription?.unsubscribe();
  }
}
