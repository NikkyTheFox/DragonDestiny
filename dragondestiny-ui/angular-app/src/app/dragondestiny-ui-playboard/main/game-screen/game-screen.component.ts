import {Component, OnInit} from '@angular/core';
import {SharedService} from "../../../services/shared.service";
import {Observable, share} from "rxjs";

@Component({
  selector: 'app-game-screen',
  templateUrl: './game-screen.component.html',
  styleUrls: ['./game-screen.component.css']
})
export class GameScreenComponent implements OnInit{
  showNotification: boolean = false;
  notificationType: number = 0;
  notificationData: any = null;
  playerFight!: Observable<any>;
  cardDraw!: Observable<any>;
  enemyFight!: Observable<any>;
  notificationClose!: Observable<any>;

  constructor(private shared: SharedService) {
  }

  ngOnInit() {
    this.cardDraw = this.shared.getDrawCardClickEvent();
    this.playerFight = this.shared.getFightPlayerClickEvent();
    this.enemyFight = this.shared.getFightEnemyOnFieldClickEvent();
    this.notificationClose = this.shared.getNotificationCloseEvent();
    this.processEvents();
  }

  processEvents(){
    this.processCardDraw();
    this.processPlayerFight();
    this.processEnemyFight();
    this.processNotificationClose();
  }

  processCardDraw(){
    this.cardDraw.subscribe( () => {
      this.showNotification = true;
      this.notificationType = 1;
    });
  }

  processPlayerFight(){
    this.playerFight.subscribe( (playerToFightWithLogin: string) => {
      this.showNotification = true;
      this.notificationType = 2;
      this.notificationData = playerToFightWithLogin;
    });
  }

  processEnemyFight(){
    this.enemyFight.subscribe( (cardToFightWithID: number) => {
      this.showNotification = true;
      this.notificationType = 3;
      this.notificationData = cardToFightWithID;
    });
  }

  processNotificationClose(){
    this.notificationClose.subscribe( () => {
      this.resetNotification();
    });
  }

  resetNotification(){
    this.notificationType = 0;
    this.showNotification = false;
  }
}
