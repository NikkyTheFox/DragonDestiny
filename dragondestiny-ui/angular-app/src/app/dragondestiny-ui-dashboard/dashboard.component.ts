import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user/user.service';
import { GameDataService } from '../services/game-data.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  playerLogin!: string;

  constructor(private userService: UserService, private dataService: GameDataService) {
  }

  ngOnInit(){
    if(this.dataService.loginFlag){
      this.playerLogin = this.dataService.getPlayerLogin();
    }
  }
}
