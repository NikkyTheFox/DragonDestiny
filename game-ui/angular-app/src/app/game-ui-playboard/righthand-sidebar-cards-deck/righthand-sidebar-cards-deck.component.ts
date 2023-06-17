import {Component, Input} from '@angular/core';
import {PlayedGameAbstractCard} from "../../interfaces/game-played-game/played-game-abstract-card";
import {GameEngineService} from "../../services/game-engine.service";

@Component({
  selector: 'app-righthand-sidebar-cards-deck',
  templateUrl: './righthand-sidebar-cards-deck.component.html',
  styleUrls: ['./righthand-sidebar-cards-deck.component.css']
})
export class RighthandSidebarCardsDeckComponent {
  @Input() gameId!: number;
  deck: PlayedGameAbstractCard[];
  numberOfCardsInDeck: number;

  constructor(private gameService: GameEngineService) {
    this.deck = [];
    this.numberOfCardsInDeck = 0;
  }

  ngOnInit(){
    this.gameService.getGameCards(this.gameId).subscribe( (data: any) => {
      this.deck = data.cardList;
      this.numberOfCardsInDeck = this.deck.length;
    })
  }
}
