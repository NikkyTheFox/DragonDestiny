import { Component, OnDestroy, OnInit } from '@angular/core';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { SharedService } from "../../../../services/shared.service";
import {PlayedGameService} from "../../../../services/played-game/played-game-service";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-game-controls-confirm',
  templateUrl: './game-controls-confirm.component.html',
  styleUrls: ['./game-controls-confirm.component.css']
})
export class GameControlsConfirmComponent implements OnInit, OnDestroy{
  endTurnSubscription!: Subscription;

  requestStructure!: GameDataStructure;

  constructor(private shared: SharedService, private playedGameService: PlayedGameService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
  }

  endTurn(){
    this.endTurnSubscription = this.playedGameService.setNextRound(this.requestStructure.game!.id).subscribe( () => {
      this.shared.sendEndTurnEvent();
    });
  }

  ngOnDestroy(): void {
    this.endTurnSubscription?.unsubscribe();
  }
}
