import {Component, Input} from '@angular/core';
import {Character} from "../../interfaces/game-engine/game-character";
import {GameEngineService} from "../../services/game-engine.service";
@Component({
  selector: 'app-main-section-additional-field-other-characters',
  templateUrl: './main-section-additional-field-other-characters.component.html',
  styleUrls: ['./main-section-additional-field-other-characters.component.css']
})
export class MainSectionAdditionalFieldOtherCharactersComponent {
  @Input() gameId!: number;
  @Input() playerId!: number;
  otherCharacters: Character[];
  allCharacters: Character[];
  constructor(private gameService: GameEngineService) {
    this.otherCharacters = [];
    this.allCharacters = [];
  }

  ngOnInit(){
    this.gameService.getGameCharacters(this.gameId).subscribe( (data: any) => {
      this.allCharacters = data.characterList;
      this.retrieveOtherCharacters();
    })
  }


  retrieveOtherCharacters(){
    this.otherCharacters = this.allCharacters.filter(character => character.id !== this.playerId);
  }
}
