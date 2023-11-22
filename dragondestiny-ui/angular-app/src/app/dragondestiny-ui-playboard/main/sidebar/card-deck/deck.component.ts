import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { Card } from '../../../../interfaces/played-game/card/card/card';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { SharedService } from '../../../../services/shared.service';
import { Subscription } from 'rxjs';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';

@Component({
  selector: 'app-deck',
  templateUrl: './deck.component.html',
  styleUrls: ['./deck.component.css']
})
export class DeckComponent implements OnInit, OnDestroy{
  deckSubscription!: Subscription;
  moveCardSubscription!: Subscription;
  subscriptionsToDelete: Subscription[] = [];
  webSocketMessagePipe!: Subscription;


  requestStructure!: GameDataStructure;
  deck: Card[] = [];
  numberOfCardsInDeck: number = 0;
  messageData!: NotificationMessage;


  constructor(private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.fetchDeck();
    this.subscriptionsToDelete.push(
      this.shared.getUpdateStatisticsEvent().subscribe( () => {
        this.fetchDeck();
      })
    )
    this.webSocketMessagePipe = this.shared.getSocketMessage().subscribe( (data: any) => {
      this.messageData = this.shared.parseNotificationMessage(data);
      console.log('Notification data from DECK:');
      console.log(this.messageData);
      if(this.messageData.notificationOption === NotificationEnum.NEXT_ROUND){
        this.fetchDeck();
      }
    })
  }

  fetchDeck(){
    this.deckSubscription = this.playedGameService.getCardsDeck(this.requestStructure.game!.id).subscribe( (data: any) => {
      this.deck = data.cardList;
      this.numberOfCardsInDeck = this.deck.length;
    });
  }

  ngOnDestroy(): void {
    this.moveCardSubscription?.unsubscribe();
    this.deckSubscription?.unsubscribe();
    this.subscriptionsToDelete.forEach( (s: Subscription) => {
      s.unsubscribe();
    });
    this.webSocketMessagePipe?.unsubscribe();
  }
}
