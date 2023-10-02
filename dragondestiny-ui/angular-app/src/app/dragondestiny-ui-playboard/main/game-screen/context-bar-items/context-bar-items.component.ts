import { Component, OnChanges, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { ItemCard } from '../../../../interfaces/played-game/card/item-card/item-card';
import { Card } from '../../../../interfaces/game-engine/card/card/card';
import { GamePlayerRequest } from '../../../../interfaces/game-player-request';
import { SharedService } from "../../../../services/shared.service";

@Component({
  selector: 'app-context-bar-items',
  templateUrl: './context-bar-items.component.html',
  styleUrls: ['./context-bar-items.component.css']
})
export class ContextBarItemsComponent implements OnInit, OnChanges{
  requestStructure!: GamePlayerRequest;
  itemsList: ItemCard[] = [];
  cardNameList: string[] = [];
  cardDescList: string[] = [];

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
  }

  ngOnChanges(){
    this.resetArrays();
    this.playedGameService.getCardsFromPlayerHand(this.requestStructure.game.id, this.requestStructure.player.login).subscribe( (data: any) => {
      this.itemsList = data.itemCardList;
      this.fetchCardsFromEngine();
    },
      (error: any) => {
        console.log('player has no cards')
      });
  }

  fetchCardsFromEngine(){
    this.itemsList.forEach( (data: ItemCard) => {
      this.gameEngineService.getCard(data.id).subscribe( (data: Card) => {
        this.cardNameList.push(data.name);
        this.cardDescList.push(data.description);
      });
    });
  }

  resetArrays(){
    this.itemsList = [];
    this.cardNameList = [];
    this.cardDescList = [];
  }
}
