import {Component, Input} from '@angular/core';
import {PlayedGameService} from "../../services/played-game-service";
import {GameDataService} from "../../services/game-data.service";
import {Router} from "@angular/router";
import {SharedService} from "../../services/shared.service";

@Component({
  selector: 'app-righthand-sidebar-game-control-dice',
  templateUrl: './righthand-sidebar-game-control-dice.component.html',
  styleUrls: ['./righthand-sidebar-game-control-dice.component.css']
})
export class RighthandSidebarGameControlDiceComponent {
  @Input() gameId!: string;
  @Input() playerLogin!: string;
  rollValue: number;

  constructor(private playedGameService: PlayedGameService, private dataService: GameDataService, private shared: SharedService) {
    this.rollValue = 0;
  }
  rollDice() {
    if(this.rollValue === 0){ // so a player can roll only once
      this.playedGameService.rollDice(this.gameId, this.playerLogin).subscribe((data: number) => {
        this.rollValue = data;
        this.playedGameService.checkPossiblePositions(this.gameId, this.playerLogin, this.rollValue).subscribe((data: any) => {
          this.dataService.possibleFields = data.fieldList;
          this.shared.sendDiceRollClickEvent();
        });
      });
    }
  }

  getRandomInt(max: number) {
    return Math.floor(Math.random() * max);
  }
}
