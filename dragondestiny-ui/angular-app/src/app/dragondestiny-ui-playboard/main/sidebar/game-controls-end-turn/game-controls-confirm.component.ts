import { Component, OnInit } from '@angular/core';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { SharedService } from "../../../../services/shared.service";
import {PlayedGameService} from "../../../../services/played-game/played-game-service";

@Component({
  selector: 'app-game-controls-confirm',
  templateUrl: './game-controls-confirm.component.html',
  styleUrls: ['./game-controls-confirm.component.css']
})
export class GameControlsConfirmComponent implements OnInit{
  requestStructure!: GameDataStructure;

  constructor(private shared: SharedService, private playedGameService: PlayedGameService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
  }

  endTurn(){
    this.playedGameService.setNextRound(this.requestStructure.game!.id).subscribe( () => {
      this.shared.sendEndTurnEvent();
    });
  }
}
