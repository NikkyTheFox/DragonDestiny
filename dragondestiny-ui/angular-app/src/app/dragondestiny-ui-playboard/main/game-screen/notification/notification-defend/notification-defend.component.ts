import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { GameDataStructure } from 'src/app/interfaces/game-data-structure';
import { Character } from 'src/app/interfaces/game-engine/character/character';
import { PlayedGameCharacter } from 'src/app/interfaces/played-game/character/character';
import { FightResult } from 'src/app/interfaces/played-game/fight-result/fight-result';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { Player } from 'src/app/interfaces/played-game/player/player';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { RoundState } from 'src/app/interfaces/played-game/round/round-state';
import { GameEngineService } from 'src/app/services/game-engine/game-engine.service';
import { PlayedGameService } from 'src/app/services/played-game/played-game-service';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-notification-defend',
  templateUrl: './notification-defend.component.html',
  styleUrls: ['./notification-defend.component.css']
})
export class NotificationDefendComponent implements OnInit, OnDestroy{
  @Input() notificationData!: any;
  @Input() gameContinueFlag!: boolean;
  @Input() dieData!: {fightEnemyCondition: boolean, rollValue: number}
  @Input() finishCondition!: boolean;
  @Input() showDefendingPlayerPOV!: boolean;
  @Output() finishConditionChange = new EventEmitter<boolean>();
  @Output() characterFightCondition = new EventEmitter<boolean>();

  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  playerToDisplay!: Player;
  characterToDisplay!: Character;
  characterAttributes: number[] = [];
  winner!: boolean;
  fightResultCondition: boolean = false;
  characterDisplayCondition: boolean = false;
  fightPlayerCondition: boolean = false;
  round!: Round;
  playerRoll: number = 0;
  enemyRoll: number = 0;

  messageData!: NotificationMessage;

  constructor(private engineServie: GameEngineService, private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(): void {
    this.requestStructure = this.shared.getRequest();
    this.toDeleteSubscription.push(
      this.shared.getSocketMessage().subscribe( (data: any) => {
        this.messageData = this.shared.parseNotificationMessage(data);
        if(this.messageData.notificationOption === NotificationEnum.DICE_ROLLED){
          this.fetchRound();
        };
        if(this.messageData.notificationOption === NotificationEnum.PLAYER_FIGHT && this.messageData.name == this.requestStructure.player!.login){
          this.winner = this.messageData.bool;
          this.fightResultCondition = true;
        }
      })
    );
    if(!this.gameContinueFlag){
      this.fetchEnemy();
    }
  }

  fetchEnemy(){
    this.fetchRound();
  }

  fetchRound(){
    this.toDeleteSubscription.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        this.round = data;
        this.processRound();
      })
    );
  }

  processRound(){
    if(this.playerToDisplay !== undefined){
      this.playerToDisplay = this.round.activePlayer;
      this.characterAttributes = [];
      this.characterAttributes.push(this.playerToDisplay.character.health);
      this.characterAttributes.push(this.playerToDisplay.character.strength);
      this.fetchCharacter(this.playerToDisplay.character);
    }
    else{
      this.checkDiceRoll();
    }
  }

  fetchCharacter(character: PlayedGameCharacter){
    this.toDeleteSubscription.push(
      this.engineServie.getCharacter(character.id).subscribe( (data: Character) => {
        this.characterToDisplay = data;
        this.checkDiceRoll();
      })
    );
  }

  checkDiceRoll(){
    if(this.round.roundState == RoundState.WAITING_FOR_ENEMY_PLAYER_ROLL){
      this.enemyRoll = this.round.playerFightRoll;
      this.characterFightCondition.emit(true);
    }
  }

  stealCard(){

  }

  reset(){
    this.fightResultCondition = false;
    this.showDefendingPlayerPOV = false;
    this.dieData = {fightEnemyCondition: false, rollValue: 0};
    this.characterAttributes = [];
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s.unsubscribe();
    });
  }
}
