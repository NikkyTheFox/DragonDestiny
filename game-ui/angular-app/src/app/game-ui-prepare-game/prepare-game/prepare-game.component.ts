import { Component } from '@angular/core';
import {GameDataService} from "../../services/game-data.service";

@Component({
  selector: 'app-prepare-game',
  templateUrl: './prepare-game.component.html',
  styleUrls: ['./prepare-game.component.css']
})
export class PrepareGameComponent {
  gameId: string;

  constructor(private dataService: GameDataService) {
    this.gameId = "";
  }

  ngOnInit(){
    this.gameId = this.dataService.chosenGame;
  }
}
