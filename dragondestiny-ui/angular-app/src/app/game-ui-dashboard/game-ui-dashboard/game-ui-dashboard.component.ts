import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {GameDataService} from "../../services/game-data.service";

@Component({
  selector: 'app-game-ui-dashboard',
  templateUrl: './game-ui-dashboard.component.html',
  styleUrls: ['./game-ui-dashboard.component.css']
})
export class GameUiDashboardComponent {

  playerLogin: string;

  constructor(private userService: UserService, private dataService: GameDataService) {
    this.playerLogin="";
  }

  ngOnInit(){
    if(!this.dataService.loginFlag) return;
    this.playerLogin = this.dataService.loginData.login;
  }
}
