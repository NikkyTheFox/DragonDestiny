import {Component, Input} from '@angular/core';
import {GameEngineService} from "../../services/game-engine.service";
import {Board} from "../../interfaces/game-engine/board";

@Component({
  selector: 'app-main-section-board',
  templateUrl: './main-section-board.component.html',
  styleUrls: ['./main-section-board.component.css']
})
export class MainSectionBoardComponent {
  @Input() gameId!: number;
  // @ts-ignore
  board: Board = {
    id: 0,
    ysize: 0,
    xsize: 0
  };
  rowArray: number[];
  //fieldsList: Field[];
  constructor(private gameService: GameEngineService) {
    this.rowArray = [];
    //this.fieldsList = [];
 }

  ngOnInit() {
    this.gameService.getGameBoard(this.gameId).subscribe((data: Board) => {
      this.board = data;
      this.prepareRowArray();
    });
  }

  prepareRowArray(){
    this.rowArray = this.getRange(this.board.ysize);
  }
  getRange(size: number): number[] {
    return Array(size).fill(0).map((_, index) => index);
  }
}
