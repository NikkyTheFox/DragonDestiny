import { Component, OnInit } from '@angular/core';
import { GameDataService } from '../services/game-data.service';
import { SharedService } from "../services/shared.service";

@Component({
  selector: 'app-prepare-game',
  templateUrl: './prepare-game.component.html',
  styleUrls: ['./prepare-game.component.css']
})
export class PrepareGameComponent implements OnInit{

  constructor(private dataService: GameDataService, private shared: SharedService){

  }

  ngOnInit(){
    this.shared.setRequestByID(this.dataService.getGame(), this.dataService.getPlayerLogin());
    this.shared.initSocket(this.dataService.getGame());
  }
}
