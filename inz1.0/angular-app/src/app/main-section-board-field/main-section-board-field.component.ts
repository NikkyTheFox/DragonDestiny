import { Component, Input } from '@angular/core';


@Component({
  selector: 'app-main-section-board-field',
  templateUrl: './main-section-board-field.component.html',
  styleUrls: ['./main-section-board-field.component.css']
})
export class MainSectionBoardFieldComponent {
  @Input() boardInfo: any;// Add the definite assignment assertion
  fieldName!: string;

  ngOnChanges() {
      this.retrieveBoardInfo();
  }
  retrieveBoardInfo() {
    if (this.boardInfo && this.boardInfo.fieldsInBoard && this.boardInfo.fieldsInBoard.length > 0) {
      this.fieldName = this.boardInfo.fieldsInBoard[0].fieldType;
      //console.log(this.fieldName);
    }
  }
}
