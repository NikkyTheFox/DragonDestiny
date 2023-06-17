import { Component, Input } from '@angular/core';
import {Board} from "../../interfaces/game-engine/board";


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

  fieldInRowArray: number[];

  constructor() {
    this.fieldInRowArray = [];
  }

  ngOnInit(){
    this.prepareFieldArray();
  }
  prepareFieldArray(){
    this.fieldInRowArray = this.getRange(this.board.xSize);
  }
  getRange(size: number): number[] {
    return Array(size).fill(0).map((_, index) => index);
  }
}
