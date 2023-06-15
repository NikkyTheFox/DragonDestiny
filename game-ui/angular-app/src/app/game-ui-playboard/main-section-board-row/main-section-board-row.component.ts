import { Component, Input } from '@angular/core';
import {Board} from "../../board";
import {GameServiceService} from "../../game-service.service";


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
  //@Input() fieldsList: Field[];

  fieldInRowArray: number[];

  constructor(private gameService: GameServiceService) {
    this.fieldInRowArray = [];
  }

  ngOnInit(){
    this.prepareFieldArray();
  }
  prepareFieldArray(){
    this.fieldInRowArray = this.getRange(this.board.xsize);
    //console.log("fieldArray of row " + this.rowIndex);
    //console.log(this.fieldInRowArray);
  }
  getRange(size: number): number[] {
    return Array(size).fill(0).map((_, index) => index);
  }
}
