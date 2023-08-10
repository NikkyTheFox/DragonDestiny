import { Component } from '@angular/core';
import {GameDataService} from "../../services/game-data.service";

@Component({
  selector: 'app-prepare-game',
  templateUrl: './prepare-game.component.html',
  styleUrls: ['./prepare-game.component.css']
})
export class PrepareGameComponent {
  gameId: string;
  playerLogin: string;

  constructor(private dataService: GameDataService) {
    this.gameId = "";
    this.playerLogin = "";
  }

  ngOnInit(){
    this.gameId = this.dataService.chosenGame;
    this.playerLogin = this.dataService.loginData.login;
  }
}
