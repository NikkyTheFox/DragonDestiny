import { Component, OnDestroy, OnInit } from '@angular/core';
import { GameDataService } from '../services/game-data.service';
import { SharedService } from '../services/shared.service';
import { GameDataStructure } from '../interfaces/game-data-structure';

@Component({
  selector: 'app-prepare-game',
  templateUrl: './prepare-game.component.html',
  styleUrls: ['./prepare-game.component.css']
})
export class PrepareGameComponent implements OnInit, OnDestroy{

  requestStructure!: GameDataStructure;

  constructor(private dataService: GameDataService, private shared: SharedService){

  }

  ngOnInit(){
    this.shared.setRequestByID(this.dataService.getGame(), this.dataService.getPlayerLogin());
    this.requestStructure = this.shared.getRequest();
    this.shared.initSocket(this.dataService.getGame());
  }

  ngOnDestroy(): void {
      this.shared.closeSocket();
  }
}
