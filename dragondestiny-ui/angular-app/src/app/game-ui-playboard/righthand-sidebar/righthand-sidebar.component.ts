import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-righthand-sidebar',
  templateUrl: './righthand-sidebar.component.html',
  styleUrls: ['./righthand-sidebar.component.css']
})
export class RighthandSidebarComponent {
  @Input() gameId!: string;
  @Input() playerLogin!: string;

}
