import {Component, OnInit } from '@angular/core';
import { GameDataService } from '../services/game-data.service';

@Component({
  selector: 'app-prepare-game',
  templateUrl: './prepare-game.component.html',
  styleUrls: ['./prepare-game.component.css']
})
export class PrepareGameComponent implements OnInit {
  gameId!: string;
  playerLogin!: string;

  constructor(private dataService: GameDataService) {
  }

  ngOnInit(){
    this.gameId = this.dataService.chosenGame;
    this.playerLogin = this.dataService.loginData.login;
  }
}
