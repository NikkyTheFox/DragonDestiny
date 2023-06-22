import { Component, Input } from '@angular/core';
import {GamePlayedGameService} from "../../services/game-played-game-service";
import {PlayedGameCharacter} from "../../interfaces/game-played-game/played-game-character";
import {PlayedGame} from "../../interfaces/game-played-game/played-game";
import {Character} from "../../interfaces/game-engine/game-character";
import {PlayedGamePlayer} from "../../interfaces/game-played-game/played-game-player";
import {GameEngineService} from "../../services/game-engine.service";
import {isEmpty} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-select-character',
  templateUrl: './select-character.component.html',
  styleUrls: ['./select-character.component.css']
})
export class SelectCharacterComponent {
  @Input() gameId!: string;
  @Input() playerLogin!: string;
  isSelectedFlag: boolean;
  allCharacters: PlayedGameCharacter[];
  availableCharacters: PlayedGameCharacter[];
  charactersToDisplay: Character[];

  constructor(private playedGameService: GamePlayedGameService, private gameEngineService: GameEngineService) {
    this.allCharacters = [];
    this.availableCharacters = [];
    this.charactersToDisplay = [];
    this.isSelectedFlag = false;
  }

  resetAllTables(){
    this.allCharacters = [];
    this.availableCharacters = [];
    this.charactersToDisplay = [];
  }

  ngOnChanges(){
    this.resetAllTables();
    this.handleCharacterTiles();
  }

  findCharactersToDisplay(){
    this.playedGameService.getGameCharacters(this.gameId).subscribe( (data: any) => {
      this.allCharacters = data.characterList;
      this.playedGameService.getGame(this.gameId).subscribe( (data: PlayedGame) => {
        this.availableCharacters = this.allCharacters.filter((character: PlayedGameCharacter) => {
          return !data.players.some((player: PlayedGamePlayer) => {
            if(this.isEmpty(player.character)) return false;
            return player.character.id === character.id;
          });
        });
        this.availableCharacters.forEach( (character: PlayedGameCharacter) => {
          this.gameEngineService.getCharacter(character.id).subscribe( (data: Character) => {
            this.charactersToDisplay.push(data);
          });
        });
      });
    });
  }

  handleCharacterTiles(){
    this.playedGameService.getPlayerCharacter(this.gameId, this.playerLogin).subscribe((data: PlayedGameCharacter) => {
      this.gameEngineService.getCharacter(data.id).subscribe( (data: Character) => {
        this.charactersToDisplay.push(data);
        this.isSelectedFlag = true;
      });
    },
      (error: any) => {
      this.findCharactersToDisplay();
      });
  }

  isEmpty(character: any): boolean {
    // Check if character is null, undefined, or an empty object
    return character === null || character === undefined || Object.keys(character).length === 0;
  }

  select(character: Character) {
    this.playedGameService.selectCharacter(this.gameId, this.playerLogin,  character.id).subscribe( (data: any) => {
      this.resetAllTables();
      this.handleCharacterTiles();
    });
  }
}
