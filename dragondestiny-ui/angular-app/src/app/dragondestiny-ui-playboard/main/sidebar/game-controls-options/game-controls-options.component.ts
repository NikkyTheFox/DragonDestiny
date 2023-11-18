import {Component, OnDestroy, OnInit} from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { SharedService } from '../../../../services/shared.service';
import { Player } from '../../../../interfaces/played-game/player/player';
import { Character } from '../../../../interfaces/game-engine/character/character';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { EnemyCard } from "../../../../interfaces/played-game/card/enemy-card/enemy-card";
import { Card } from "../../../../interfaces/game-engine/card/card/card";
import {EnemyCardList} from "../../../../interfaces/played-game/card/enemy-card/enemy-card-list";
import { Subscription } from 'rxjs';
import { PlayerList } from 'src/app/interfaces/played-game/player/player-list';

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
  tempSubscription!: Subscription;
  endTurnSubscription!: Subscription;
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
  movedFlag: boolean = false;
  actionButtonClickFlag: boolean = false;

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService,private shared: SharedService){
  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.movedFlag = false;
    this.actionButtonClickFlag = false;
    this.resetOptions();
    this.handleOptions(); // ONLY FOR TEST PURPOSES (SO YOU DONT HAVE TO HIT SPECIAL FIELD)
    this.endTurnSubscription = this.shared.getEndTurnEvent().subscribe( () => {
      this.movedFlag = false;
      this.actionButtonClickFlag = false;
      this.resetOptions();
    });
    this.moveClickEventSubscription = this.shared.getMoveCharacterClickEvent().subscribe( () => {
      this.resetOptions();
      this.handleOptions();
    });
  }

  handleOptions(){
    this.getFieldOptions();
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
        // to check
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
    this.shared.sendDrawCardClickEvent(this.numberOfCardsToBeDrawn);
    this.actionButtonClickFlag = true;
  }

  // loseTurnClick(){
  //   this.shared.sendBlockTurnEvent();
  //   // this.playedGameService.automaticallyBlockTurnsOfPlayer(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( () => {
  //   //   console.log('Player: ' + this.requestStructure.player!.login + ' blocked for ' + this.numberOfTurnsToBeLost + ' turns.');
  //   // });
  // }

  attackPlayedClick(enemyPlayedLogin: string){
    this.shared.sendFightPlayerClickEvent(enemyPlayedLogin);
    this.actionButtonClickFlag = true;
  }

  attackEnemyClick(enemyCardId: number){
    this.shared.sendFightEnemyCardClickEvent(enemyCardId);
    this.actionButtonClickFlag = true;
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
  }
}
