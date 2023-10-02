import { Component, DoCheck } from '@angular/core';
import { GameDataService} from "../../services/game-data.service";

@Component({
  selector: 'app-header-left',
  templateUrl: './header-left.component.html',
  styleUrls: ['./header-left.component.css']
})
export class HeaderLeftComponent implements DoCheck{
  playerLogin!: string;

  constructor(private dataService: GameDataService){

  }

  ngDoCheck(){
    this.playerLogin = this.dataService.getPlayerLogin();
  }
}
