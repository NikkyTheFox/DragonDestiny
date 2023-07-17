import {Component, Input, SimpleChanges} from '@angular/core';
import {Board} from "../../interfaces/game-engine/board";
import {Field} from "../../interfaces/game-engine/field";
import {GameEngineService} from "../../services/game-engine.service";
import {GameDataService} from "../../services/game-data.service";


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

  constructor(private gameService: GameEngineService) {
    this.fieldsList = [];
  }

  ngOnChanges(changes: SimpleChanges) {
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
