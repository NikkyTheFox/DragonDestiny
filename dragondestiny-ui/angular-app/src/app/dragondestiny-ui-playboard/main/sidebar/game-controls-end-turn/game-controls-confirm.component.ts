import { Component, OnInit } from '@angular/core';
import { GamePlayerRequest } from '../../../../interfaces/game-player-request';
import { SharedService } from "../../../../services/shared.service";

@Component({
  selector: 'app-game-controls-confirm',
  templateUrl: './game-controls-confirm.component.html',
  styleUrls: ['./game-controls-confirm.component.css']
})
export class GameControlsConfirmComponent implements OnInit{
  requestStructure!: GamePlayerRequest;

  constructor(private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
  }

  endTurn(){

  }
}
