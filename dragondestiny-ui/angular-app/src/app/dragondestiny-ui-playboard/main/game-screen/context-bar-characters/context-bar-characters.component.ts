import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { Player } from '../../../../interfaces/played-game/player/player';
import { GamePlayerRequest } from '../../../../interfaces/game-player-request';
import { Character } from '../../../../interfaces/game-engine/character/character';
import { SharedService } from "../../../../services/shared.service";

@Component({
  selector: 'app-context-bar-characters',
  templateUrl: './context-bar-characters.component.html',
  styleUrls: ['./context-bar-characters.component.css']
})
export class ContextBarCharactersComponent implements OnInit, OnChanges{
  requestStructure!: GamePlayerRequest;
  playersCharacter!: PlayedGameCharacter;
  otherCharacters: PlayedGameCharacter[] = [];
  allCharacters: PlayedGameCharacter[] = [];
  characterNames: string[] = [];

  constructor(protected gameEngineService: GameEngineService, private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.handleCharacters();
  }

  ngOnChanges(changes: SimpleChanges){
    this.handleCharacters();
  }

  handleCharacters(){
    this.playedGameService.getPlayersCharacter(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: PlayedGameCharacter) => {
      this.playersCharacter = data;
      this.filterOutPlayers();
    });
  }

  filterOutPlayers(){
    this.playedGameService.getPlayers(this.requestStructure.game!.id).subscribe( (data: any) => {
      let playerList = data.playerList;
      playerList.forEach( (data: Player) => {
        this.allCharacters.push(data.character);
      });
      this.retrieveOtherCharacters();
      this.retrieveCharacterNames();
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
