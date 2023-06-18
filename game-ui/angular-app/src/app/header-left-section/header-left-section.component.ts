import { Component } from '@angular/core';
import {GameDataService} from "../services/game-data.service";

@Component({
  selector: 'app-header-left-section',
  templateUrl: './header-left-section.component.html',
  styleUrls: ['./header-left-section.component.css']
})
export class HeaderLeftSectionComponent {

  playerLogin: string;

  constructor(private dataService: GameDataService) {
    this.playerLogin="";
  }

  ngDoCheck(){
    this.playerLogin = this.dataService.loginData.login;
  }

}
