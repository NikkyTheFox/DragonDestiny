import {Component, OnInit } from '@angular/core';
import { GameDataService } from '../../services/game-data.service';
import { RequestStructureGameidPlayerlogin } from '../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  gamePlayerStructure: RequestStructureGameidPlayerlogin = {
    gameId: '',
    playerLogin: '',
  };

  constructor(private dataService: GameDataService) {
  }

  ngOnInit(){
    if(!this.dataService.loginFlag) return;
    this.gamePlayerStructure.gameId = this.dataService.chosenGame;
    this.gamePlayerStructure.playerLogin = this.dataService.loginData.login;
  }
}
