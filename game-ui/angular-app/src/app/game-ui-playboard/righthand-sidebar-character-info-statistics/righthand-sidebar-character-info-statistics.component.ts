import {Component, Input} from '@angular/core';
import {Character} from "../../interfaces/game-engine/game-character";

@Component({
  selector: 'app-righthand-sidebar-character-info-statistics',
  templateUrl: './righthand-sidebar-character-info-statistics.component.html',
  styleUrls: ['./righthand-sidebar-character-info-statistics.component.css']
})
export class RighthandSidebarCharacterInfoStatisticsComponent {
  @Input() character!: Character;
}
