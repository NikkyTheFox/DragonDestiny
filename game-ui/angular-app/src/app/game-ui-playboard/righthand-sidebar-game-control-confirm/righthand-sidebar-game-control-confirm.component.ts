import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-righthand-sidebar-game-control-confirm',
  templateUrl: './righthand-sidebar-game-control-confirm.component.html',
  styleUrls: ['./righthand-sidebar-game-control-confirm.component.css']
})
export class RighthandSidebarGameControlConfirmComponent {
  @Input() gameId!: string;
  @Input() playerLogin!: string;
}
