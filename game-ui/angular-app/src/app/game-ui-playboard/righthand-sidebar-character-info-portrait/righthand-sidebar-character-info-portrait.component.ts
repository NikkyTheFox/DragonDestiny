import {Component, Input} from '@angular/core';
import {GameCharacter} from "../../game-character";

@Component({
  selector: 'app-righthand-sidebar-character-info-portrait',
  templateUrl: './righthand-sidebar-character-info-portrait.component.html',
  styleUrls: ['./righthand-sidebar-character-info-portrait.component.css']
})
export class RighthandSidebarCharacterInfoPortraitComponent {
  @Input() character!: GameCharacter;
}
