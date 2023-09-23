import { Component, Input } from '@angular/core';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';

@Component({
  selector: 'app-character-statistics',
  templateUrl: './character-statistics.component.html',
  styleUrls: ['./character-statistics.component.css']
})
export class CharacterStatisticsComponent {
  @Input() character!: PlayedGameCharacter;
}
