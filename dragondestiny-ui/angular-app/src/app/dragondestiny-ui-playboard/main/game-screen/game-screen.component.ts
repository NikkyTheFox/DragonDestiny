import { GameEngineService } from 'src/app/services/game-engine/game-engine.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { SharedService } from '../../../services/shared.service';
import { Subscription } from 'rxjs';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { UpdateEnum } from 'src/app/interfaces/played-game/notification/update-enum';

@Component({
  selector: 'app-game-screen',
  templateUrl: './game-screen.component.html',
  styleUrls: ['./game-screen.component.css']
})
export class GameScreenComponent implements OnInit, OnDestroy{
  showNotification: boolean = false;
  notificationType: number = 0;
  notificationData: any = null;
  notificationData2: any = null;
  gameContinueFlag: boolean = false;

  toDeleteSubscription: Subscription[] = [];

  constructor(private shared: SharedService, private engineService: GameEngineService) {
  }

  ngOnInit() {
    this.processEvents();
  }

  processEvents(){
    this.processCardDraw();
    this.processEnemyFight();
    this.processPlayerAttack();
    this.processPlayerDefend();
    this.processContinueGame();
    this.processGameUpdate();
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

  processEnemyFight(){
    this.toDeleteSubscription.push(
      this.shared.getFightEnemyCardEvent().subscribe( (cardToFightWithID: number) => {
        this.showNotification = true;
        this.notificationType = 2;
        this.notificationData = cardToFightWithID;
      })
    );
  }

  processPlayerAttack(){
    this.toDeleteSubscription.push(
      this.shared.getNotifyAttackerPlayerEvent().subscribe( (defenderPlayerLogin: string ) => {
        this.showNotification = true;
        this.notificationType = 3;
        this.notificationData = defenderPlayerLogin;
      })
    );
  }

  processPlayerDefend(){
    this.toDeleteSubscription.push(
      this.shared.getNotifyDefenderPlayerEvent().subscribe( (attackerPlayerLogin: string ) => {
        this.showNotification = true;
        this.notificationType = 4;
        this.notificationData = attackerPlayerLogin;
      })
    );
  }

  processContinueGame(){
    this.toDeleteSubscription.push(
      this.shared.getContinuteGameEvent().subscribe( (round: Round) => {
        this.showNotification = true;
        this.notificationType = 5;
        this.notificationData = round;
      })
    );
  }

  processGameUpdate(){
    this.toDeleteSubscription.push(
      this.shared.getUpdateGameEvent().subscribe( (data: {updateType: UpdateEnum, player1Login: string | null, player2Login: string | null, cardId: number | null, numTurnsBlock: number | null}) => {
        if(data.player1Login == this.shared.getRequest().player!.login || data.player2Login == this.shared.getRequest().player!.login){
          return;
        }
        // this.showNotification = false;
        // this.notificationData = '';
        this.notificationData2 = '';
        if(data.updateType == UpdateEnum.PLAYER_ATTACKED &&
          data.player1Login != null && data.player2Login != null){
            this.showNotification = true;
            this.notificationType = 6;
            this.notificationData = data.player1Login +' attacked ' + data.player2Login;
        }
        else if(data.updateType == UpdateEnum.PLAYER_BLOCKED &&
          data.player1Login != null && data.numTurnsBlock != null){
            this.notificationData = data.player1Login + ' has been blocked for ' + data.numTurnsBlock + 'turns';
        }
        else if(data.updateType == UpdateEnum.PLAYER_DIED &&
          data.player1Login != null){
            this.notificationData = data.player1Login + ' died';
        }
        else if(data.updateType == UpdateEnum.PLAYER_FIGHT &&
          data.player1Login != null && data.player2Login != null){
          // ASSUMPTION: Player1 = winner
          this.notificationData = data.player1Login + ' won a in a fight with ' + data.player2Login;
        }
        else if(data.updateType == UpdateEnum.PLAYER_GOT_ITEM &&
          data.player1Login != null && data.cardId != null){
            this.toDeleteSubscription.push(
              this.engineService.getCard(data.cardId).subscribe( (card: any) => {
                this.notificationData = data.player1Login + ' got a card';
                this.showNotification = true;
                this.notificationType = 6;
                this.notificationData2 = card;
              })
            );
            
        }
        else if(data.updateType == UpdateEnum.PLAYER_WON_GAME &&
          data.player1Login != null){
            this.notificationData = data.player1Login + ' has won the game!';
            this.showNotification = true;
            this.notificationType = 6;
        }
        else if(data.updateType == UpdateEnum.CARD_STOLEN &&
          data.player1Login != null && data.player2Login != null && data.cardId != null){
            // ASSUMPTION: Player1 = winner
            this.toDeleteSubscription.push(
              this.engineService.getCard(data.cardId).subscribe( (card: any) => {
                this.notificationData = data.player1Login + ' has stolen a card from ' + data.player2Login;
                this.showNotification = true;
                this.notificationType = 6;
                this.notificationData2 = card;
              })
            );
        }
      })
    );
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
