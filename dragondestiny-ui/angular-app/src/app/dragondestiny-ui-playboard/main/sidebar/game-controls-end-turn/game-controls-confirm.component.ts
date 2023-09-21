import { Component, Input } from '@angular/core';
import { RequestStructureGameidPlayerlogin } from '../../../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-game-controls-confirm',
  templateUrl: './game-controls-confirm.component.html',
  styleUrls: ['./game-controls-confirm.component.css']
})
export class GameControlsConfirmComponent {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;

  endTurn() {

  }
}
