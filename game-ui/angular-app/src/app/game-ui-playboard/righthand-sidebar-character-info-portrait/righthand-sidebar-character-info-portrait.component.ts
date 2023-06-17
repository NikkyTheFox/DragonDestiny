import {Component, Input} from '@angular/core';
import {Character} from "../../interfaces/game-engine/game-character";

@Component({
  selector: 'app-righthand-sidebar-character-info-portrait',
  templateUrl: './righthand-sidebar-character-info-portrait.component.html',
  styleUrls: ['./righthand-sidebar-character-info-portrait.component.css']
})
export class RighthandSidebarCharacterInfoPortraitComponent {
  @Input() character!: Character;
}
