import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { Board } from '../../../../interfaces/game-engine/board/board';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { PlayedGame } from '../../../../interfaces/played-game/played-game/played-game';
import { GamePlayerRequest } from '../../../../interfaces/game-player-request';
import { SharedService } from "../../../../services/shared.service";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit, OnChanges{
  requestStructure!: GamePlayerRequest;
  board !: Board;
  rowArray: number[] = [];

  constructor(private gameEngineService: GameEngineService, private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
  }

  ngOnChanges(changes: SimpleChanges){
    this.playedGameService.getGame(this.requestStructure.game.id).subscribe( (data: PlayedGame) => {
      this.gameEngineService.getBoard(data.board.id).subscribe( (data: Board) => {
        this.board = data;
        this.prepareRowArray();
      })
    })
  }

  prepareRowArray(){
    this.rowArray = this.getRange(this.board.ysize);
  }
  getRange(size: number){
    return Array(size).fill(0).map((_, index) => index);
  }
}
