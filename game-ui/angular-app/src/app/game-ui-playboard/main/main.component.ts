import {Component} from '@angular/core';
import {GameDataService} from "../../services/game-data.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent {
  gameId: string;
  playerLogin: string;

  constructor(private dataService: GameDataService) {
    this.gameId = "";
    this.playerLogin = "";
  }

  ngOnInit(){
    if(!this.dataService.loginFlag) return;
    this.gameId = this.dataService.chosenGame;
    this.playerLogin = this.dataService.loginData.login;
  }
}
