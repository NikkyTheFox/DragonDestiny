import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-righthand-sidebar-game-control',
  templateUrl: './righthand-sidebar-game-control.component.html',
  styleUrls: ['./righthand-sidebar-game-control.component.css']
})
export class RighthandSidebarGameControlComponent {
  @Input() gameId!: string;
  @Input() playerLogin!: string;
}
