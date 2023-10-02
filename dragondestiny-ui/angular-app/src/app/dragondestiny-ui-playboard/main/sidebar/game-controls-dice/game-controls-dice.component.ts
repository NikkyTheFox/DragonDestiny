import { Component, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { GameDataService } from '../../../../services/game-data.service';
import { SharedService } from '../../../../services/shared.service';
import { GamePlayerRequest } from '../../../../interfaces/game-player-request';

@Component({
  selector: 'app-game-controls-dice',
  templateUrl: './game-controls-dice.component.html',
  styleUrls: ['./game-controls-dice.component.css']
})
export class GameControlsDiceComponent implements OnInit{
  requestStructure!: GamePlayerRequest;
  rollValue: number = 0;

  constructor(private playedGameService: PlayedGameService, private dataService: GameDataService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
  }

  rollDice(){
    if(this.rollValue === 0){ // so a player can roll only once
      this.playedGameService.rollDice(this.requestStructure.game.id, this.requestStructure.player.login).subscribe((data: number) => {
        this.rollValue = data;
        this.checkPositions();
      });
    }
  }

  checkPositions(){
    this.playedGameService.checkPossibleNewPositions(this.requestStructure.game.id, this.requestStructure.player.login, this.rollValue).subscribe((data: any) => {
      this.dataService.possibleFields = data.fieldList;
      this.shared.sendDiceRollClickEvent();
    });
  }
}
