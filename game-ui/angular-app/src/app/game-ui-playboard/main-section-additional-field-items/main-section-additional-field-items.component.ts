import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-main-section-additional-field-items',
  templateUrl: './main-section-additional-field-items.component.html',
  styleUrls: ['./main-section-additional-field-items.component.css']
})
export class MainSectionAdditionalFieldItemsComponent {
  @Input() gameId!: string;
  @Input() playerId!: string;
}
