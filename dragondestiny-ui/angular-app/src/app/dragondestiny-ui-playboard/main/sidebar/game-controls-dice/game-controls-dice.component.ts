import { Component, Input } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { GameDataService } from '../../../../services/game-data.service';
import { SharedService } from '../../../../services/shared.service';
import { RequestStructureGameidPlayerlogin } from '../../../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-game-controls-dice',
  templateUrl: './game-controls-dice.component.html',
  styleUrls: ['./game-controls-dice.component.css']
})
export class GameControlsDiceComponent {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;
  rollValue: number = 0;

  constructor(private playedGameService: PlayedGameService, private dataService: GameDataService, private shared: SharedService) {
  }
  rollDice() {
    if(this.rollValue === 0){ // so a player can roll only once
      this.playedGameService.rollDice(this.requestStructure.gameId, this.requestStructure.playerLogin).subscribe((data: number) => {
        this.rollValue = data;
        this.playedGameService.checkPossibleNewPositions(this.requestStructure.gameId, this.requestStructure.playerLogin, this.rollValue).subscribe((data: any) => {
          this.dataService.possibleFields = data.fieldList;
          this.shared.sendDiceRollClickEvent();
        });
      });
    }
  }
}
