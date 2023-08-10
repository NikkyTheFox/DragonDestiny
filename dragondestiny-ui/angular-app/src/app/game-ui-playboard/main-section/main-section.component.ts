import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-main-section',
  templateUrl: './main-section.component.html',
  styleUrls: ['./main-section.component.css']
})
export class MainSectionComponent {
  @Input() gameId!: string;
  @Input() playerLogin!: string;
}
