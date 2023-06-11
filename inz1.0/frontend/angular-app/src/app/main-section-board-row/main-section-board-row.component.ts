import { Component, Input } from '@angular/core';
import {Board} from "../board";
import {GameServiceService} from "../game-service.service";


@Component({
  selector: 'app-main-section-board-row',
  templateUrl: './main-section-board-row.component.html',
  styleUrls: ['./main-section-board-row.component.css']
})
export class MainSectionBoardRowComponent {
  // @ts-ignore
  @Input() board: Board;
  // @ts-ignore
  @Input() rowIndex: number;

  fieldArray: number[];

  constructor(private gameService: GameServiceService) {
    this.fieldArray = [];
  }

  ngOnInit(){
    this.prepareFieldArray();
  }
  prepareFieldArray(){
    this.fieldArray = this.getRange(this.board.xsize);
    console.log("fieldArray of row " + this.rowIndex);
    console.log(this.fieldArray);
  }
  getRange(size: number): number[] {
    return Array(size).fill(0).map((_, index) => index);
  }
}
