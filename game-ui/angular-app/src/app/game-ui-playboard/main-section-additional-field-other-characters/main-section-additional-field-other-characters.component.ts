import {Component, Input, SimpleChanges} from '@angular/core';
import {Character} from "../../interfaces/game-engine/game-character";
import {GameEngineService} from "../../services/game-engine.service";
import {PlayedGameCharacter} from "../../interfaces/game-played-game/played-game-character";
import {GamePlayedGameService} from "../../services/game-played-game-service";
@Component({
  selector: 'app-main-section-additional-field-other-characters',
  templateUrl: './main-section-additional-field-other-characters.component.html',
  styleUrls: ['./main-section-additional-field-other-characters.component.css']
})
export class MainSectionAdditionalFieldOtherCharactersComponent {
  @Input() gameId!: string;
  @Input() playerId!: string;
  playersCharacter: PlayedGameCharacter = {
    id: 0,
    initialHealth: 0,
    initialStrength: 0,
    additionalStrength: 0,
    additionalHealth: 0,
    positionField: null
  };
  otherCharacters: PlayedGameCharacter[];
  allCharacters: PlayedGameCharacter[];
  characterNames: string[];
  constructor(protected gameEngineService: GameEngineService, private playedGameService: GamePlayedGameService) {
    this.otherCharacters = [];
    this.allCharacters = [];
    this.characterNames = [];
  }

  ngOnChanges(changes: SimpleChanges){
    this.playedGameService.getPlayerCharacter(this.gameId, this.playerId).subscribe( (data: PlayedGameCharacter) => {
      this.playersCharacter = data;
      this.playedGameService.getGameCharacters(this.gameId).subscribe( (data: any) => {
        this.allCharacters = data.characterList;
        this.retrieveOtherCharacters();
        this.retrieveCharacterNames();
      });
    });
  }


  retrieveOtherCharacters(){
    this.otherCharacters = this.allCharacters.filter(character => character.id !== this.playersCharacter.id);
  }

  retrieveCharacterNames(){
    this.otherCharacters.forEach((character: PlayedGameCharacter) => {
      this.gameEngineService.getCharacter(character.id).subscribe((data: Character) => {
        this.characterNames.push(data.name);
      });
    });
  }
}
