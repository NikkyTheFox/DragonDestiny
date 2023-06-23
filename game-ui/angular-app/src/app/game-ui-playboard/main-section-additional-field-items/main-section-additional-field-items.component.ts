import {Component, Input} from '@angular/core';
import {GamePlayedGameService} from "../../services/game-played-game-service";
import {GameEngineService} from "../../services/game-engine.service";
import {PlayedGameItemCard} from "../../interfaces/game-played-game/played-game-item-card";
import {Card} from "../../interfaces/game-engine/card";

@Component({
  selector: 'app-main-section-additional-field-items',
  templateUrl: './main-section-additional-field-items.component.html',
  styleUrls: ['./main-section-additional-field-items.component.css']
})
export class MainSectionAdditionalFieldItemsComponent {
  @Input() gameId!: string;
  @Input() playerLogin!: string;
  itemsList: PlayedGameItemCard[];
  cardNameList: string[];
  cardDescList: string[];

  constructor(private playedGameService: GamePlayedGameService, private gameEngineServie: GameEngineService) {
    this.itemsList = [];
    this.cardNameList = [];
    this.cardDescList = [];
  }

  ngOnChanges(){
    this.resetArrays();
    this.playedGameService.getPlayersCards(this.gameId, this.playerLogin).subscribe( (data: any) => {
      this.itemsList = data.itemCardList;
      this.fetchCardsFromEngine();
    });
  }

  fetchCardsFromEngine(){
    this.itemsList.forEach( (data: PlayedGameItemCard) => {
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
