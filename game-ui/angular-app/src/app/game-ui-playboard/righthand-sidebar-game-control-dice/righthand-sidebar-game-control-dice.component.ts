import {Component, Input} from '@angular/core';
import {GamePlayedGameService} from "../../services/game-played-game-service";

@Component({
  selector: 'app-righthand-sidebar-game-control-dice',
  templateUrl: './righthand-sidebar-game-control-dice.component.html',
  styleUrls: ['./righthand-sidebar-game-control-dice.component.css']
})
export class RighthandSidebarGameControlDiceComponent {
  @Input() gameId!: string;
  @Input() playerLogin!: string;
  rolledNumber: number;

  constructor(private playedGameService: GamePlayedGameService) {
    this.rolledNumber = 0;
  }
  rollDice() {
    this.rolledNumber = this.getRandomInt(6) + 1; //get random between 1 and 6
  }

  getRandomInt(max: number) {
    return Math.floor(Math.random() * max);
  }
}
