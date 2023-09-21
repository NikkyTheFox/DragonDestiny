import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { Card } from '../../../../interfaces/played-game/card/card/card';
import { RequestStructureGameidPlayerlogin } from '../../../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-deck',
  templateUrl: './deck.component.html',
  styleUrls: ['./deck.component.css']
})
export class DeckComponent implements OnChanges {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;
  deck: Card[] = [];
  numberOfCardsInDeck: number = 0;

  constructor(private playedGameService: PlayedGameService) {
  }

  ngOnChanges(changes: SimpleChanges){
    this.playedGameService.getCardsDeck(this.requestStructure.gameId).subscribe( (data: any) => {
      this.deck = data.cardList;
      this.numberOfCardsInDeck = this.deck.length;
    });
  }

  drawCard() { // FOR TESTS OF MOVING CARD TO HAND: ID=11 is first item card in deck
    this.playedGameService.moveItemCardFromDeckToPlayerHand(this.requestStructure.gameId, 11, this.requestStructure.playerLogin).subscribe();
  }
}
