import { Component, OnDestroy, OnInit } from '@angular/core';
import { GameDataStructure } from '../../../../../interfaces/game-data-structure';
import { SharedService } from '../../../../../services/shared.service';
import { PlayedGameService } from '../../../../../services/played-game/played-game-service';
import { Subscription } from 'rxjs';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { RoundState } from 'src/app/interfaces/played-game/round/round-state';

@Component({
  selector: 'app-game-controls-confirm',
  templateUrl: './game-controls-confirm.component.html',
  styleUrls: ['./game-controls-confirm.component.css']
})
export class GameControlsConfirmComponent implements OnInit, OnDestroy{
  subscriptionToDelete: Subscription[] = [];
  requestStructure!: GameDataStructure;

  disableButtonFlag: boolean = true;
  constructor(private shared: SharedService, private playedGameService: PlayedGameService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.disableButtonFlag = true;
    this.fetchRound();
    this.subscriptionToDelete.push(
      this.shared.getNotificationCloseEvent().subscribe( () => {
        this.disableButtonFlag = false;
      })
    );
    this.subscriptionToDelete.push(
      this.shared.getBlockTurnEvent().subscribe( () => {
        this.disableButtonFlag = false;
      })
    );
  }

  fetchRound(){
    this.subscriptionToDelete.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        if(data.roundState == RoundState.WAITING_FOR_NEXT_ROUND){
          this.disableButtonFlag = false;
        }
      })
    )
  }

  endTurn(){
    this.subscriptionToDelete.push(
      this.playedGameService.setNextRound(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( () => {
        this.shared.sendEndTurnEvent();
        this.disableButtonFlag = true;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptionToDelete.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
