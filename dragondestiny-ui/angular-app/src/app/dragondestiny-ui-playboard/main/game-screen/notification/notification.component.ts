import { Card as EngineCard} from './../../../../interfaces/game-engine/card/card/card';
import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { SharedService } from '../../../../services/shared.service';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { FightResult } from '../../../../interfaces/played-game/fight-result/fight-result';
import { Subscription } from 'rxjs';
import { GameEngineService } from 'src/app/services/game-engine/game-engine.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit, OnChanges, OnDestroy{
  drawCardSubscription!: Subscription;
  playerFightSubscription!: Subscription;
  enemyFightSubscription!: Subscription;
  rollDieSubscription!: Subscription;
  tempSubscription!: Subscription;
  subscriptionToDelete: Subscription[] = [];

  requestStructure!: GameDataStructure;
  @Input() notificationType!: number;
  @Input() notificationData!: any;

  dieData: {fightEnemyCondition: boolean, rollValue: number} = {fightEnemyCondition: false, rollValue: 0}

  fightResult!: FightResult;
  cardToDisplay!: EngineCard;
  cardAttributes: number[] = [];
  
  playerRoll: number = 0;
  enemyRoll: number = 0;
  showDrawCardConditionBoolean: boolean = false;
  showFightEnemyCardConditionBoolean: boolean = false;
  cardDisplayCondition: boolean = false;
  handCondition: boolean = false;
  equipCondition: boolean = false;
  rollCondition: boolean = false;
  fightResultCondition: boolean = false;
  bridgeFlag: boolean = false; // player is on bridge field
  bossRoomFlag: boolean = false; // player is on boss field

  cardsDrawn: number = 0;
  finishCondition: boolean = false;
  actionsDone: number = 0;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService, private engineService: GameEngineService) {
  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.handleNotifications();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.requestStructure = this.shared.getRequest();
    this.cardAttributes = [];
    this.handleNotifications();
  }

  checkFinishCondition(){
    // Card draw check:
    if(this.cardsDrawn == this.notificationData){
      this.finishCondition = true;
    }
    // Field Enemy Fought Once
    // Player Fought Once
  }

  handleNotifications(){
    this.checkFinishCondition();
    if(!this.finishCondition){
      switch(this.notificationType){
        case 1:
          this.showDrawCardConditionBoolean = true; // show card draw option
          break;
        case 2:
          this.handleFightPlayer();
          break;
        case 3:
          this.showFightEnemyCardConditionBoolean = true; // show field enemy data
          break;
      }
    }
  }

  receiveCardActionFinished(){
    this.actionsDone++;
    this.showDrawCardConditionBoolean = false;
    this.rollCondition = false;
  }

  receiveRollFightEvent(data: boolean){
    this.rollCondition = data;
  }

  recieveFinishConditionChange(data: boolean){
    this.finishCondition = data;
    this.rollCondition = false;
}

  handleFightPlayer(){
    // ROLL A DIE
    let roll = 6;
    this.playerFightSubscription = this.playedGameService.handleFightWithPlayer(
      this.requestStructure.game!.id,
      this.requestStructure.player!.login,
      this.notificationData // login of a Player to fight with
    ).subscribe( (data: FightResult) => {
    // Handle fightResult data to be displayed in html file.
    });
  }

  recieveDieData(data: { flag: boolean, value: number }){
    this.dieData = {fightEnemyCondition: data.flag, rollValue: data.value};
  }

  continue(){
    this.reset();
    this.actionsDone = 0;
    this.handleNotifications();
  }

  close(){
    this.reset();
    this.shared.sendNotificationCloseEvent();
  }

  reset(){
    this.showDrawCardConditionBoolean = false;
    this.cardDisplayCondition = false;
    this.handCondition = false; 
    this.equipCondition = false;
    this.rollCondition = false;
    this.fightResultCondition = false;
    this.showFightEnemyCardConditionBoolean = false;
    this.dieData = {fightEnemyCondition: false, rollValue: 0};
  }

  ngOnDestroy(): void {
    this.subscriptionToDelete.forEach( (s: Subscription) => {
      s.unsubscribe();
    });
    this.rollDieSubscription?.unsubscribe();
    this.enemyFightSubscription?.unsubscribe();
    this.playerFightSubscription?.unsubscribe();
    this.drawCardSubscription?.unsubscribe();
  }
}
