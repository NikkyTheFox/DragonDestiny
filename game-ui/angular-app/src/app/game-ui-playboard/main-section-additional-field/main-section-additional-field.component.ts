import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-main-section-additional-field',
  templateUrl: './main-section-additional-field.component.html',
  styleUrls: ['./main-section-additional-field.component.css']
})
export class MainSectionAdditionalFieldComponent {
  @Input() gameId!: string;
  @Input() playerId!: string;
}
