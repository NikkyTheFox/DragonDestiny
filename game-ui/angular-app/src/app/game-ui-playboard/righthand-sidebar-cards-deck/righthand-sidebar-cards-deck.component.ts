import {Component, Input} from '@angular/core';
import {AbstractCard} from "../../abstract-card";
import {GameServiceService} from "../../game-service.service";

@Component({
  selector: 'app-righthand-sidebar-cards-deck',
  templateUrl: './righthand-sidebar-cards-deck.component.html',
  styleUrls: ['./righthand-sidebar-cards-deck.component.css']
})
export class RighthandSidebarCardsDeckComponent {
  @Input() gameId!: number;
  deck: AbstractCard[];
  numberOfCardsInDeck: number;

  constructor(private gameService: GameServiceService) {
    this.deck = [];
    this.numberOfCardsInDeck = 0;
  }

  ngOnInit(){
    this.gameService.getCards(this.gameId).subscribe( (data: any) => {
      this.deck = data.cardList;
      this.numberOfCardsInDeck = this.deck.length;
    })
  }
}
