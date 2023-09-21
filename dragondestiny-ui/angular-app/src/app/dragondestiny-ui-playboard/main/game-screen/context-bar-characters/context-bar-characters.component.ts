import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { Player } from '../../../../interfaces/played-game/player/player';
import { RequestStructureGameidPlayerlogin } from '../../../../interfaces/request-structure-gameid-playerlogin';
import { Character } from '../../../../interfaces/game-engine/character/character';

@Component({
  selector: 'app-context-bar-characters',
  templateUrl: './context-bar-characters.component.html',
  styleUrls: ['./context-bar-characters.component.css']
})
export class ContextBarCharactersComponent implements OnChanges {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;
  playersCharacter!: PlayedGameCharacter;
  otherCharacters: PlayedGameCharacter[] = [];
  allCharacters: PlayedGameCharacter[] = [];
  characterNames: string[] = [];

  constructor(protected gameEngineService: GameEngineService, private playedGameService: PlayedGameService) {
  }

  ngOnChanges(changes: SimpleChanges){
    this.playedGameService.getPlayersCharacter(this.requestStructure.gameId, this.requestStructure.playerLogin).subscribe( (data: PlayedGameCharacter) => {
      this.playersCharacter = data;
      this.playedGameService.getPlayers(this.requestStructure.gameId).subscribe( (data: any) => {
        let playerList = data.playerList;
        playerList.forEach( (data: Player) => {
          this.allCharacters.push(data.character);
        });
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
