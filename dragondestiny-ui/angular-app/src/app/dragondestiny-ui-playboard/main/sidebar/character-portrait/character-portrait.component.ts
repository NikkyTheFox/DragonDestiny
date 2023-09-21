import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Character } from '../../../../interfaces/game-engine/character/character';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';

@Component({
  selector: 'app-character-portrait',
  templateUrl: './character-portrait.component.html',
  styleUrls: ['./character-portrait.component.css']
})
export class CharacterPortraitComponent implements OnChanges{
  @Input() character!: PlayedGameCharacter;
  gameEngineCharacter!: Character;

  constructor(private gameEngineService: GameEngineService) {
  }

  ngOnChanges(changes: SimpleChanges){
    this.gameEngineService.getCharacter(this.character.id).subscribe((data: Character) => {
      this.gameEngineCharacter = data;
    })
  }
}
