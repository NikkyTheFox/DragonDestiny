import { Component, OnDestroy, OnInit } from '@angular/core';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { Board } from '../../../../interfaces/game-engine/board/board';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { PlayedGame } from '../../../../interfaces/played-game/played-game/played-game';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { SharedService } from '../../../../services/shared.service';
import { Subscription } from 'rxjs';
import { RoundState } from 'src/app/interfaces/played-game/round/round-state';
import { GameDataService } from 'src/app/services/game-data.service';
import { Round } from 'src/app/interfaces/played-game/round/round';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  board !: Board;
  rowArray: number[] = [];
  conditionFlag: boolean = false;

  constructor(private gameEngineService: GameEngineService, private playedGameService: PlayedGameService, private shared: SharedService, private dataService: GameDataService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.fetchRound();
    this.handleRows();
  }

  fetchRound(){
    this.toDeleteSubscription.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        if(data.roundState == RoundState.WAITING_FOR_FIELDS_TO_MOVE){
          this.toDeleteSubscription.push(
            this.playedGameService.checkPossibleNewPositions(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: any) => {
              this.dataService.possibleFields = data.fieldList;
              this.conditionFlag = true;
            })
          );
        }
        if(data.roundState == RoundState.WAITING_FOR_MOVE){
          this.dataService.possibleFields = data.fieldListToMove;
          this.conditionFlag = true;
        }      
      })
    );
  }

  handleRows(){
    this.toDeleteSubscription.push(
      this.playedGameService.getGame(this.requestStructure.game!.id).subscribe( (data: PlayedGame) => {
        this.getBoard(data);
      })
    );
  }

  getBoard(playedGame: PlayedGame){
    this.toDeleteSubscription.push(
      this.gameEngineService.getBoard(playedGame.board.id).subscribe( (data: Board) => {
        this.board = data;
        this.prepareRowArray();
      })
    );
  }

  prepareRowArray(){
    this.rowArray = this.getRange(this.board.ysize);
  }
  getRange(size: number){
    return Array(size).fill(0).map((_, index) => index);
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
