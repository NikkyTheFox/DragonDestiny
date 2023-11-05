import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {SharedService} from "../../../services/shared.service";
import {Observable, Subscription, share} from "rxjs";

@Component({
  selector: 'app-game-screen',
  templateUrl: './game-screen.component.html',
  styleUrls: ['./game-screen.component.css']
})
export class GameScreenComponent implements OnInit, OnDestroy{
  showNotification: boolean = false;
  notificationType: number = 0;
  notificationData: any = null;
  playerFightSubscription!: Subscription;
  cardDrawSubscription!: Subscription;
  enemyFightSubscription!: Subscription;
  notificationCloseSubscription!: Subscription ;

  constructor(private shared: SharedService) {
  }

  ngOnInit() {
    this.processEvents();
  }


  processEvents(){
    this.processCardDraw();
    this.processPlayerFight();
    this.processEnemyFight();
    this.processNotificationClose();
  }

  processCardDraw(){
    this.cardDrawSubscription = this.shared.getDrawCardClickEvent().subscribe( () => {
      this.showNotification = true;
      this.notificationType = 1;
    });
  }

  processPlayerFight(){
    this.playerFightSubscription = this.shared.getFightPlayerClickEvent().subscribe( (playerToFightWithLogin: any ) => {
      this.showNotification = true;
      this.notificationType = 2;
      this.notificationData = playerToFightWithLogin;
    });
  }

  processEnemyFight(){
    this.enemyFightSubscription = this.shared.getFightEnemyOnFieldClickEvent().subscribe( (cardToFightWithID: any) => {
      this.showNotification = true;
      this.notificationType = 3;
      this.notificationData = cardToFightWithID;
    });
  }

  processNotificationClose(){
    this.notificationCloseSubscription = this.shared.getNotificationCloseEvent().subscribe( () => {
      this.resetNotification();
    });
  }

  resetNotification(){
    this.notificationType = 0;
    this.showNotification = false;
  }

  ngOnDestroy(): void {
      this.notificationCloseSubscription?.unsubscribe();
      this.enemyFightSubscription?.unsubscribe();
      this.playerFightSubscription?.unsubscribe();
      this.cardDrawSubscription?.unsubscribe();
  }
}
