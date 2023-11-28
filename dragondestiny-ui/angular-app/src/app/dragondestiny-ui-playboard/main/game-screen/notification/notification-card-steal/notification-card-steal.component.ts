import { ItemCardList } from 'src/app/interfaces/played-game/card/item-card/item-card-list';
import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { GameDataStructure } from 'src/app/interfaces/game-data-structure';
import { ItemCard } from 'src/app/interfaces/played-game/card/item-card/item-card';
import { ItemCard as EngineCard } from 'src/app/interfaces/game-engine/card/item-card/item-card';
import { GameEngineService } from 'src/app/services/game-engine/game-engine.service';
import { PlayedGameService } from 'src/app/services/played-game/played-game-service';
import { SharedService } from 'src/app/services/shared.service';
import { Round } from 'src/app/interfaces/played-game/round/round';

@Component({
  selector: 'app-notification-card-steal',
  templateUrl: './notification-card-steal.component.html',
  styleUrls: ['./notification-card-steal.component.css']
})
export class NotificationCardStealComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  round!: Round;
  playedGameCards: ItemCard[] = [];
  engineCards: EngineCard[] = [];
  cardOrder: number[] = [];
  cardFetchedFlag: boolean = false;
  loserLogin!: string;

  constructor(private engineService: GameEngineService, private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(): void {
    this.requestStructure = this.shared.getRequest();
    this.fetchRound();
  }

  fetchRound(){
    this.toDeleteSubscription.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        this.round = data;
        if(data.activePlayer.login == this.requestStructure.player!.login){
          // Means that activePlayer won
          this.loserLogin = data.enemyPlayerFought.login;
        }
        else{
          // Means that enemyPlayerFought won
          this.loserLogin = data.activePlayer.login;
        }
        this.fetchItemCards();
      })
    );
  }

  fetchItemCards(){
    this.toDeleteSubscription.push(
      this.playedGameService.getHealthCardsFromPlayerHand(this.requestStructure.game!.id, this.loserLogin).subscribe( (data: ItemCardList) => {
        this.playedGameCards = data.itemCardList;
        this.fetchEngineCards();
      })
    );
  }

  fetchEngineCards(){
    this.playedGameCards.forEach( (c: ItemCard) => {
      this.toDeleteSubscription.push(
        this.engineService.getCard(c.id).subscribe( (data: any) => {
          this.engineCards.push(data as EngineCard);
          if(this.engineCards.length == this.playedGameCards.length){
            this.cardFetchedFlag = true;
            this.sortItemArrays();
          }
        })
      );
    });
  }

  steal(card: ItemCard){
    this.toDeleteSubscription.push(
      this.playedGameService.moveCardFromPlayerToPlayer(this.requestStructure.game!.id, this.loserLogin, card.id, this.requestStructure.player!.login).subscribe( () => {
        this.reset();
      })
    );
  }

  sortItemArrays(){
    for(let i = 0; i< this.playedGameCards.length; i++){
      for(let j = 0; j < this.engineCards.length; j++){
        if(this.playedGameCards[i].id == this.engineCards[j].id){
          this.cardOrder.push(j);
        }
      }
    }
  }

  reset(){
    this.playedGameCards = [];
    this.engineCards = [];
    this.cardOrder = [];
    this.cardFetchedFlag = false;
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    })
  }
}
