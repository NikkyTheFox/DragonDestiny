import {Component, Input} from '@angular/core';
import {GameServiceService} from "../game-service.service";

@Component({
  selector: 'app-main-section-board',
  templateUrl: './main-section-board.component.html',
  styleUrls: ['./main-section-board.component.css']
})
export class MainSectionBoardComponent {
  @Input() gameId!: number;
  boardInfo: any;
  fieldName: any;
  numOfRows: any;
  numOfCols: any;
  fields: [];

  constructor(private gameService: GameServiceService) {
    this.fields = [];
  }

  ngOnInit() {
    this.gameService.getGamesBoard(this.gameId).subscribe(board => {
      this.boardInfo = board;
      this.retrieveBoardInfo();
      //console.log(this.boardInfo);
    });
  }

  retrieveBoardInfo() {
    if (this.boardInfo && this.boardInfo.fieldsInBoard && this.boardInfo.fieldsInBoard.length > 0) {
      this.fieldName = this.boardInfo.fieldsInBoard[0].fieldType;
      this.numOfRows = this.boardInfo.ysize;
      this.numOfCols = this.boardInfo.xsize;
      this.fields = this.boardInfo.fieldsInBoard;
      //console.log(this.fields);

    }

  }

}
