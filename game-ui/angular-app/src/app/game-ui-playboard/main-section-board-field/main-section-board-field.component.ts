import { Component, Input } from '@angular/core';
import {Board} from "../../board";
import {Field} from "../../field";
import {GameServiceService} from "../../game-service.service";


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
  fieldsList: Field[];
  fieldName!: string;

  constructor(private gameService: GameServiceService) {
    this.fieldsList = [];
  }
  ngOnInit() {
    this.gameService.getBoardFields(this.board.id).subscribe((data: any) => {
      this.fieldsList = data.fieldList;
      this.retrieveBoardInfo();
    });
  }


  retrieveBoardInfo() {
    for(let i of this.fieldsList){
      if(i.yposition == this.rowIndex && i.xposition == this.fieldIndex){
        this.fieldName = i.type;
      }
    }
  }

}
