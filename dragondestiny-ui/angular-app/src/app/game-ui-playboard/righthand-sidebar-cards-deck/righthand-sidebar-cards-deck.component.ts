import {Component, Input, SimpleChanges} from '@angular/core';
import {PlayedGameService} from "../../services/played-game-service";
import {PlayedGameCard} from "../../interfaces/game-played-game/played-game-card";

@Component({
  selector: 'app-righthand-sidebar-cards-deck',
  templateUrl: './righthand-sidebar-cards-deck.component.html',
  styleUrls: ['./righthand-sidebar-cards-deck.component.css']
})
export class RighthandSidebarCardsDeckComponent {
  @Input() gameId!: string;
  @Input() playerLogin!: string;
  deck: PlayedGameCard[];
  numberOfCardsInDeck: number;

  constructor(private playedGameService: PlayedGameService) {
    this.deck = [];
    this.numberOfCardsInDeck = 0;
  }

  ngOnChanges(changes: SimpleChanges){
    this.playedGameService.getCardsDeck(this.gameId).subscribe( (data: any) => {
      this.deck = data.cardList;
      this.numberOfCardsInDeck = this.deck.length;
    });
  }

  drawCard() { // FOR TESTS OF MOVING CARD TO HAND: ID=7 is first item card in deck
    this.playedGameService.moveCardFromDeckToPlayerHand(this.gameId, 8, this.playerLogin).subscribe();
  }
}
