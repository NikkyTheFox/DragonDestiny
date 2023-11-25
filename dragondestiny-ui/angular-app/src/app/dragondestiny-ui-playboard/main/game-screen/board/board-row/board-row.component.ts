import { Component, Input, OnInit } from '@angular/core';
import { Board } from '../../../../../interfaces/game-engine/board/board';

@Component({
  selector: 'app-board-row',
  templateUrl: './board-row.component.html',
  styleUrls: ['./board-row.component.css']
})
export class BoardRowComponent implements OnInit{
  @Input() board!: Board;
  @Input() rowIndex!: number;
  @Input() conditionFlag: boolean = false;
  fieldInRowArray: number[] = [];

  ngOnInit(): void {
    this.prepareFieldArray();
  }

  prepareFieldArray(){
    this.fieldInRowArray = this.getRange(this.board.xsize);
  }

  getRange(size: number){
    return Array(size).fill(0).map((_, index) => index);
  }
}
