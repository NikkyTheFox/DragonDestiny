import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { SharedService } from '../../../../services/shared.service';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { Subscription } from 'rxjs';
import { GameEngineService } from 'src/app/services/game-engine/game-engine.service';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { FieldOptionEnum } from 'src/app/interfaces/played-game/field/field-option-enum';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit, OnChanges, OnDestroy{
  @Input() notificationType!: number;
  @Input() notificationData!: any;
  @Input() gameContinueFlag!: boolean;
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  dieData: {fightEnemyCondition: boolean, rollValue: number} = {fightEnemyCondition: false, rollValue: 0}  
  showDrawCard: boolean = false;
  showFightEnemyCard: boolean = false;
  showFightPlayerConditionBoolean: boolean = false;
  showAttackingPlayerPOV: boolean = false;
  showDefeningPlayerPOV: boolean = false;
  showUpdate: boolean = false;
  rollCondition: boolean = false;
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
        case 1: // DRAW CARD
          this.showDrawCard = true; // show card draw option
          // notificationData = numberOfCardsToTake;
          break;
        case 2: // FIGHT ENEMY CARD
          this.showFightEnemyCard = true; // show field enemy data
          // notificationData = cardToFightWithID;
          break;
        case 3: // ATTACKER POV
          this.showAttackingPlayerPOV = true;
          console.log('here');
          // notificationData = defenderPlayerLogin;
          break;
        case 4: // DEFENDER POV
          this.showDefeningPlayerPOV = true;
          // notificationData = attackerPlayerLogin;
          break;
        case 5: //PROCESS CONTINUEGAME
          this.processContinueGame();
          // notificationData = Round;
          break;
        case 6:
          this.showUpdate = true;
          // notification data is a string to show
      };
    };
  }

  receiveCardActionFinished(){
    this.actionsDone++;
    this.showDrawCard = false;
    this.rollCondition = false;
  }

  receiveRollFightEvent(data: boolean){
    this.rollCondition = data;
  }

  recieveFinishConditionChange(data: boolean){
    this.finishCondition = data;
    this.rollCondition = false;
}

  processContinueGame(){
    let round = this.notificationData as Round;
    if(round.fieldOptionChosen.fieldOptionEnum == FieldOptionEnum.TAKE_ONE_CARD){

    }
    if(round.fieldOptionChosen.fieldOptionEnum == FieldOptionEnum.TAKE_TWO_CARDS){

    }
    if(round.fieldOptionChosen.fieldOptionEnum == FieldOptionEnum.FIGHT_WITH_ENEMY_ON_FIELD){

    }
    if(round.fieldOptionChosen.fieldOptionEnum == FieldOptionEnum.BOSS_FIELD){

    }
    if(round.fieldOptionChosen.fieldOptionEnum == FieldOptionEnum.BRIDGE_FIELD){
      
    }
    if(round.fieldOptionChosen.fieldOptionEnum == FieldOptionEnum.FIGHT_WITH_PLAYER){

    }
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
    this.showDrawCard = false;
    this.rollCondition = false;
    this.showFightEnemyCard = false;
    this.dieData = {fightEnemyCondition: false, rollValue: 0};
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
