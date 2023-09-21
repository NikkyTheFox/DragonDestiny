import { Component, Input } from '@angular/core';
import { RequestStructureGameidPlayerlogin } from '../../../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-game-controls',
  templateUrl: './game-controls.component.html',
  styleUrls: ['./game-controls.component.css']
})
export class GameControlsComponent {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;
}
