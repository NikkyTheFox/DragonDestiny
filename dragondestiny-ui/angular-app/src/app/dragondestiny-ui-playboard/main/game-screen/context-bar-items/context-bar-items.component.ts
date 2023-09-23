import { Component, Input, OnChanges } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { ItemCard } from '../../../../interfaces/played-game/card/item-card/item-card';
import { Card } from '../../../../interfaces/game-engine/card/card/card';
import { RequestStructureGameidPlayerlogin } from '../../../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-context-bar-items',
  templateUrl: './context-bar-items.component.html',
  styleUrls: ['./context-bar-items.component.css']
})
export class ContextBarItemsComponent implements OnChanges {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;
  itemsList: ItemCard[] = [];
  cardNameList: string[] = [];
  cardDescList: string[] = [];

  constructor(private playedGameService: PlayedGameService, private gameEngineServie: GameEngineService) {
  }

  ngOnChanges(){
    this.resetArrays();
    this.playedGameService.getCardsFromPlayerHand(this.requestStructure.gameId, this.requestStructure.playerLogin).subscribe( (data: any) => {
      this.itemsList = data.itemCardList;
      this.fetchCardsFromEngine();
    },
      (error: any) => {
        console.log('player has no cards')
      });
  }

  fetchCardsFromEngine(){
    this.itemsList.forEach( (data: ItemCard) => {
      this.gameEngineServie.getCard(data.id).subscribe( (data: Card) => {
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
