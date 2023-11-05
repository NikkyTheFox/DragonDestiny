import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { ItemCard } from '../../../../interfaces/played-game/card/item-card/item-card';
import { Card } from '../../../../interfaces/game-engine/card/card/card';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { SharedService } from "../../../../services/shared.service";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-context-bar-items',
  templateUrl: './context-bar-items.component.html',
  styleUrls: ['./context-bar-items.component.css']
})
export class ContextBarItemsComponent implements OnInit, OnDestroy{
  equipItemSubscription!: Subscription;
  handCardsSubscription!: Subscription;
  tempSubscription!: Subscription;
  cardSubscriptionList: Subscription[] = [];

  requestStructure!: GameDataStructure;
  itemsList: ItemCard[] = [];
  cardNameList: string[] = [];
  cardDescList: string[] = [];

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.equipItemSubscription = this.shared.getEquipItemCardClickEvent().subscribe( () => {
      this.handleCards();
    });
  }

  handleCards(){
    this.resetArrays();
    this.handCardsSubscription = this.playedGameService.getCardsFromPlayerHand(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: any) => {
        this.itemsList = data.itemCardList;
        this.fetchCardsFromEngine();
      },
      (error: any) => {
        console.log('player has no cards')
      });
  }

  fetchCardsFromEngine(){
    this.itemsList.forEach( (data: ItemCard) => {
      this.tempSubscription = this.gameEngineService.getCard(data.id).subscribe( (data: Card) => {
        this.cardNameList.push(data.name);
        this.cardDescList.push(data.description);
      });
      this.cardSubscriptionList.push(this.tempSubscription);
    });
  }

  resetArrays(){
    this.itemsList = [];
    this.cardNameList = [];
    this.cardDescList = [];
  }
  
  ngOnDestroy(): void {
      this.cardSubscriptionList.forEach( (s: Subscription) => {
        s?.unsubscribe();
      })
      this.tempSubscription?.unsubscribe();
      this.handCardsSubscription?.unsubscribe();
      this.equipItemSubscription?.unsubscribe();
  }
}
