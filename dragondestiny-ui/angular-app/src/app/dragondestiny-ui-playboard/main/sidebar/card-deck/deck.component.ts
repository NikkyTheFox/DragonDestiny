import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { Card } from '../../../../interfaces/played-game/card/card/card';
import { GamePlayerRequest } from '../../../../interfaces/game-player-request';
import { SharedService } from "../../../../services/shared.service";

@Component({
  selector: 'app-deck',
  templateUrl: './deck.component.html',
  styleUrls: ['./deck.component.css']
})
export class DeckComponent implements OnInit, OnChanges{
  requestStructure!: GamePlayerRequest;
  deck: Card[] = [];
  numberOfCardsInDeck: number = 0;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
  }

  ngOnChanges(changes: SimpleChanges){
    this.playedGameService.getCardsDeck(this.requestStructure.game!.id).subscribe( (data: any) => {
      this.deck = data.cardList;
      this.numberOfCardsInDeck = this.deck.length;
    });
  }

  drawCard(){ // FOR TESTS OF MOVING CARD TO HAND: ID=11 is first item card in deck
    this.playedGameService.moveItemCardFromDeckToPlayerHand(this.requestStructure.game!.id, 11, this.requestStructure.player!.login).subscribe( ()=> {
      this.shared.sendEquipItemCardClickEvent();
    });
  }
}
