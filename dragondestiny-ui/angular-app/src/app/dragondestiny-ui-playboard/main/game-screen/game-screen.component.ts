import { Component, OnDestroy, OnInit } from '@angular/core';
import { SharedService } from '../../../services/shared.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-game-screen',
  templateUrl: './game-screen.component.html',
  styleUrls: ['./game-screen.component.css']
})
export class GameScreenComponent implements OnInit, OnDestroy{
  showNotification: boolean = false;
  notificationType: number = 0;
  notificationData: any = null;
  toDeleteSubscription: Subscription[] = [];

  constructor(private shared: SharedService) {
  }

  ngOnInit() {
    this.processEvents();
  }

  processEvents(){
    this.processCardDraw();
    this.processPlayerFight();
    this.processEnemyFight();
    this.processContinue();
    this.processNotificationClose();
  }

  processCardDraw(){
    this.toDeleteSubscription.push(
      this.shared.getDrawCardClickEvent().subscribe( (numberOfCards: number) => {
        this.showNotification = true;
        this.notificationType = 1;
        this.notificationData = numberOfCards;
      })
    );
  }

  processPlayerFight(){
    this.toDeleteSubscription.push(
      this.shared.getFightPlayerClickEvent().subscribe( (playerToFightWithLogin: string ) => {
        this.showNotification = true;
        this.notificationType = 2;
        this.notificationData = playerToFightWithLogin;
      })
    );
  }

  processEnemyFight(){
    this.toDeleteSubscription.push(
      this.shared.getFightEnemyCardClickEvent().subscribe( (cardToFightWithID: number) => {
        this.showNotification = true;
        this.notificationType = 3;
        this.notificationData = cardToFightWithID;
      })
    );
  }

  processContinue(){

  }

  processNotificationClose(){
    this.toDeleteSubscription.push(
      this.shared.getNotificationCloseEvent().subscribe( () => {
        this.resetNotification();
      })
    );
  }

  resetNotification(){
    this.notificationType = 0;
    this.showNotification = false;
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
