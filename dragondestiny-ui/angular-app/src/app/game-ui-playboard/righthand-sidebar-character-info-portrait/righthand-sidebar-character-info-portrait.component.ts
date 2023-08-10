import {Component, Input, SimpleChanges} from '@angular/core';
import {Character} from "../../interfaces/game-engine/game-character";
import {PlayedGameCharacter} from "../../interfaces/game-played-game/played-game-character";
import {GameEngineService} from "../../services/game-engine.service";

@Component({
  selector: 'app-righthand-sidebar-character-info-portrait',
  templateUrl: './righthand-sidebar-character-info-portrait.component.html',
  styleUrls: ['./righthand-sidebar-character-info-portrait.component.css']
})
export class RighthandSidebarCharacterInfoPortraitComponent {
  @Input() character!: PlayedGameCharacter;
  gameEngineCharacter: Character = {
    id: 0,
    name: "",
    profession: "",
    story: "",
    initialStrength: 0,
    initialHealth: 0,
    field: null
  };

  constructor(private gameEngineService: GameEngineService) {
  }

  ngOnChanges(changes: SimpleChanges){
    this.gameEngineService.getCharacter(this.character.id).subscribe((data:Character) => {
      this.gameEngineCharacter = data;
    })
  }
}
