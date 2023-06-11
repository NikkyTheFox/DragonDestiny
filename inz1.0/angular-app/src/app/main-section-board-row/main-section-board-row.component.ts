import { Component, Input } from '@angular/core';


@Component({
  selector: 'app-main-section-board-row',
  templateUrl: './main-section-board-row.component.html',
  styleUrls: ['./main-section-board-row.component.css']
})
export class MainSectionBoardRowComponent {
  @Input() boardInfo: any;
}
