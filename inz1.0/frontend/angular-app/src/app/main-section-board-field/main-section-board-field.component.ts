import { Component, Input } from '@angular/core';
import {Board} from "../board";


@Component({
  selector: 'app-main-section-board-field',
  templateUrl: './main-section-board-field.component.html',
  styleUrls: ['./main-section-board-field.component.css']
})
export class MainSectionBoardFieldComponent {
  // @ts-ignore
  @Input() board: Board;// Add the definite assignment assertion

  // @ts-ignore
  @Input() fieldIndex: number;
  // @ts-ignore
  @Input() rowIndex: number;
  fieldName!: string;

  ngOnInit(){
    console.log("Field coords " + this.rowIndex + " " + this.fieldIndex);
  }
  ngOnChanges() {
      this.retrieveBoardInfo();
  }
  retrieveBoardInfo() {
    if (this.board && this.board.fieldsInBoard && this.board.fieldsInBoard.length > 0) {

      for(let i of this.board.fieldsInBoard){
        if(i.yposition == this.rowIndex && i.xposition == this.fieldIndex){
          // @ts-ignore
          this.fieldName = this.board.fieldsInBoard[0].fieldType;
        }
      }
    }
  }
}
