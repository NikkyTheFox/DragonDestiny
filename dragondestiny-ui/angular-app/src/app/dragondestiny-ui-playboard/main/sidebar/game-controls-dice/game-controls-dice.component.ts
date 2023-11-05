import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { GameDataService } from '../../../../services/game-data.service';
import { SharedService } from '../../../../services/shared.service';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-game-controls-dice',
  templateUrl: './game-controls-dice.component.html',
  styleUrls: ['./game-controls-dice.component.css']
})
export class GameControlsDiceComponent implements OnInit, OnDestroy{
  rollDieSubscription!: Subscription;
  checkPositionSubscription!: Subscription;

  requestStructure!: GameDataStructure;
  rollValue: number = 0;

  constructor(private playedGameService: PlayedGameService, private dataService: GameDataService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
  }

  rollDice(){
    this.rollDieSubscription = this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe((data: number) => {
      this.rollValue = data;
      this.checkPositions();
    });
  }

  checkPositions(){
    this.checkPositionSubscription = this.playedGameService.checkPossibleNewPositions(this.requestStructure.game!.id, this.requestStructure.player!.login, this.rollValue).subscribe((data: any) => {
      this.dataService.possibleFields = data.fieldList;
      this.shared.sendDiceRollClickEvent();
    });
  }

  ngOnDestroy(): void {
    this.checkPositionSubscription?.unsubscribe();
    this.rollDieSubscription?.unsubscribe();
  }
}
