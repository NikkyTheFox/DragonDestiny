import { Component, Input } from '@angular/core';
import { RequestStructureGameidPlayerlogin } from '../../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-game-screen',
  templateUrl: './game-screen.component.html',
  styleUrls: ['./game-screen.component.css']
})
export class GameScreenComponent {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;
}
