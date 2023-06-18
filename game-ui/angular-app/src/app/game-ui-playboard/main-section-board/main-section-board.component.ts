import {Component, Input, SimpleChanges} from '@angular/core';
import {GameEngineService} from "../../services/game-engine.service";
import {Board} from "../../interfaces/game-engine/board";
import {GamePlayedGameService} from "../../services/game-played-game-service";
import {PlayedGame} from "../../interfaces/game-played-game/played-game";

@Component({
  selector: 'app-main-section-board',
  templateUrl: './main-section-board.component.html',
  styleUrls: ['./main-section-board.component.css']
})
export class MainSectionBoardComponent {
  @Input() gameId!: string;
  // @ts-ignore
  board: Board = {
    id: 0,
    ysize: 0,
    xsize: 0
  };
  rowArray: number[];
  //fieldsList: Field[];
  constructor(private gameEngineService: GameEngineService, private playedGameService: GamePlayedGameService) {
    this.rowArray = [];
    //this.fieldsList = [];
 }

  ngOnChanges(changes: SimpleChanges) {
    this.playedGameService.getGame(this.gameId).subscribe( (data: PlayedGame) => {
      this.gameEngineService.getBoard(data.board.id).subscribe( (data: Board) => {
        this.board = data;
        this.prepareRowArray();
      })
    })
  }

  prepareRowArray(){
    this.rowArray = this.getRange(this.board.ysize);
  }
  getRange(size: number): number[] {
    return Array(size).fill(0).map((_, index) => index);
  }
}
