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
    ySize: 0,
    xSize: 0
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
      console.log(data);
      console.log(this.board);
      /*console.log(this.board.id);
      console.log(this.board.ySize);
      console.log(this.board.xSize);*/
      this.prepareRowArray();
    });
  }

  prepareRowArray(){
    this.rowArray = this.getRange(this.board.ySize);
    //console.log("rowArray of board: " + this.board.id);
    //console.log(this.rowArray);
  }
  getRange(size: number): number[] {
    return Array(size).fill(0).map((_, index) => index);
  }
}
