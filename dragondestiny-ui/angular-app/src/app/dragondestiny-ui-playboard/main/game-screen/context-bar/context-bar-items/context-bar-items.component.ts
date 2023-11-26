import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../../services/played-game/played-game-service';
import { GameEngineService } from '../../../../../services/game-engine/game-engine.service';
import { ItemCard } from '../../../../../interfaces/played-game/card/item-card/item-card';
import { ItemCard as EngineCard } from '../../../../../interfaces/game-engine/card/item-card/item-card';
import { GameDataStructure } from '../../../../../interfaces/game-data-structure';
import { SharedService } from '../../../../../services/shared.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-context-bar-items',
  templateUrl: './context-bar-items.component.html',
  styleUrls: ['./context-bar-items.component.css']
})
export class ContextBarItemsComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  itemsList: ItemCard[] = [];
  engineItemsList: EngineCard[] = [];
  cardOrder: number[] = [];
  cardFetchedFlag: boolean = false;
  discardFlag: boolean = false;

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.handleCards();
    this.toDeleteSubscription.push(
      this.shared.getRefreshHandCardsEvent().subscribe( () => {
        this.handleCards();
      })
    );
    this.toDeleteSubscription.push(
      this.shared.getItemToDiscardEvent().subscribe( () => {
        this.discardFlag = true;
      })
    )
  }

  handleCards(){
    this.resetArrays();
    this.toDeleteSubscription.push(
      this.playedGameService.getCardsFromPlayerHand(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: any) => {
        this.itemsList = data.itemCardList;
      },
      (error: any) => {
        console.log('player has no cards')
      },
      () => {
        this.fetchCardsFromEngine();
      })
    );
  }

  fetchCardsFromEngine(){
    this.itemsList.forEach( (data: ItemCard) => {
      this.toDeleteSubscription.push(
        this.gameEngineService.getCard(data.id).subscribe( (data: any) => {
          this.engineItemsList.push(data);
          if(this.engineItemsList.length == this.itemsList.length){
            this.cardFetchedFlag = true;
            this.sortItemArrays();
          }
        })
      );
    });
  }

  sortItemArrays(){
    for(let i = 0; i< this.itemsList.length; i++){
      for(let j = 0; j < this.engineItemsList.length; j++){
        if(this.itemsList[i].id == this.engineItemsList[j].id){
          this.cardOrder.push(j);
        }
      }
    }
  }

  discardItem(card: ItemCard){
    this.toDeleteSubscription.push(
      this.playedGameService.moveCardFromPlayerHandToUsedCardDeck(this.requestStructure.game!.id, this.requestStructure.player!.login, card.id).subscribe( () => {
        this.discardFlag = false;
        this.shared.sendRefreshHandCardsEvent();        
      })
    );
  }

  resetArrays(){
    this.discardFlag = false;
    this.cardFetchedFlag = false;
    this.itemsList = [];
    this.engineItemsList = [];
    this.cardOrder = [];
  }
  
  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
