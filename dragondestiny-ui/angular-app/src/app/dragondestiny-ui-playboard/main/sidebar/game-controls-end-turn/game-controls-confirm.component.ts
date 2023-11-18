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
  notificationClosedSubscription!: Subscription;
  requestStructure!: GameDataStructure;

  disableButtonFlag: boolean = true;
  constructor(private shared: SharedService, private playedGameService: PlayedGameService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.disableButtonFlag = true;
    this.notificationClosedSubscription = this.shared.getNotificationCloseEvent().subscribe( () => {
      this.disableButtonFlag = false;
    });
  }

  endTurn(){
    this.endTurnSubscription = this.playedGameService.setNextRound(this.requestStructure.game!.id).subscribe( () => {
      this.shared.sendEndTurnEvent();
      this.disableButtonFlag = true;
    });
  }

  ngOnDestroy(): void {
    this.notificationClosedSubscription?.unsubscribe();
    this.endTurnSubscription?.unsubscribe();
  }
}
