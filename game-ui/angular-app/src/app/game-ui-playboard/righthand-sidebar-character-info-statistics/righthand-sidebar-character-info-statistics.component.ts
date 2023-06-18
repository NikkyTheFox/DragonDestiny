import {Component, Input} from '@angular/core';
import {PlayedGameCharacter} from "../../interfaces/game-played-game/played-game-character";

@Component({
  selector: 'app-righthand-sidebar-character-info-statistics',
  templateUrl: './righthand-sidebar-character-info-statistics.component.html',
  styleUrls: ['./righthand-sidebar-character-info-statistics.component.css']
})
export class RighthandSidebarCharacterInfoStatisticsComponent {
  @Input() character!: PlayedGameCharacter;
}
