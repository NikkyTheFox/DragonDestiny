import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { SharedService } from '../../../../services/shared.service';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { Round } from '../../../../interfaces/played-game/round/round';
import { Subscription } from 'rxjs';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';

@Component({
  selector: 'app-game-controls',
  templateUrl: './game-controls.component.html',
  styleUrls: ['./game-controls.component.css']
})
export class GameControlsComponent implements OnInit, OnDestroy{
  roundSubscription!: Subscription;
  requestStructure!: GameDataStructure;
  isActive: boolean = false;
  activePlayerLogin!: string;
  activePlayerCharacter!: number;
  webSocketMessagePipe!: Subscription;
  messageData!: NotificationMessage;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService){

  }
  ngOnInit() {
    this.requestStructure = this.shared.getRequest();
    this.checkRound();
    this.webSocketMessagePipe = this.shared.getSocketMessage().subscribe( (data: any) => {
      this.messageData = this.shared.parseNotificationMessage(data);
      if(this.messageData.notificationOption === NotificationEnum.NEXT_ROUND){
        this.checkRound();
      }
    });
  }

  checkRound(){
    this.roundSubscription = this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
      this.activePlayerLogin = data.activePlayer.login;
      this.activePlayerCharacter = data.activePlayer.character.id;
      this.isActive = this.activePlayerLogin === this.requestStructure.player!.login;
    });
  }

  ngOnDestroy(): void {
      this.roundSubscription?.unsubscribe();
      this.webSocketMessagePipe?.unsubscribe();
  }
}
