import {Component, Input, SimpleChanges} from '@angular/core';
import {Board} from "../../interfaces/game-engine/board";


@Component({
  selector: 'app-main-section-board-row',
  templateUrl: './main-section-board-row.component.html',
  styleUrls: ['./main-section-board-row.component.css']
})
export class MainSectionBoardRowComponent {
  @Input() board!: Board;
  @Input() rowIndex!: number;
  @Input() gameId!: string;
  @Input() playerLogin!: string;
  fieldInRowArray: number[];

  constructor() {
    this.fieldInRowArray = [];
  }

  ngOnChanges(changes: SimpleChanges){
    this.prepareFieldArray();
  }
  prepareFieldArray(){
    this.fieldInRowArray = this.getRange(this.board.xsize);
  }
  getRange(size: number): number[] {
    return Array(size).fill(0).map((_, index) => index);
  }
}
