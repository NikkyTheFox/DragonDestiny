import {Component} from '@angular/core';
import {GameDataService} from "../../services/game-data.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent {
  gameId: string;
  playerId: string;

  constructor(private dataService: GameDataService) {
    this.gameId = "";
    this.playerId = "";
  }

  ngOnInit(){
    if(!this.dataService.loginFlag) return;
    this.gameId = this.dataService.chosenGame;
    this.playerId = this.dataService.loginData.login;
  }
}
