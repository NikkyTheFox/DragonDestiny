import {Component, Input} from '@angular/core';
import {GameServiceService} from "../../game-service.service";
import {Board} from "../../board";
import {Field} from "../../field";

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
  constructor(private gameService: GameServiceService) {
    this.rowArray = [];
    //this.fieldsList = [];
 }

  ngOnInit() {
    this.gameService.getGamesBoard(this.gameId).subscribe((data: Board) => {
      this.board = data;
      //console.log(this.board.id);
      //console.log(this.board.ysize);
      //console.log(this.board.xsize);
      this.prepareRowArray();
    });
  }

  prepareRowArray(){
    this.rowArray = this.getRange(this.board.ysize);
    //console.log("rowArray of board: " + this.board.id);
    //console.log(this.rowArray);
  }
  getRange(size: number): number[] {
    return Array(size).fill(0).map((_, index) => index);
  }
}
