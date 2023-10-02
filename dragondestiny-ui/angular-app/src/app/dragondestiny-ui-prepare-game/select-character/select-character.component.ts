import { Component, OnChanges, OnInit } from '@angular/core';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { PlayedGameCharacter } from '../../interfaces/played-game/character/character';
import { Character } from '../../interfaces/game-engine/character/character';
import { GameEngineService } from '../../services/game-engine/game-engine.service';
import { SharedService } from "../../services/shared.service";

@Component({
  selector: 'app-select-character',
  templateUrl: './select-character.component.html',
  styleUrls: ['./select-character.component.css']
})
export class SelectCharacterComponent implements OnInit, OnChanges{
  gameId!: string;
  playerLogin!: string;
  isSelectedFlag: boolean = false;
  selectedCharacterId!: number;
  availableCharacters: PlayedGameCharacter[] = [];
  charactersToDisplay: Character[] = [];

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService, private shared: SharedService){
  }

  ngOnInit(){
    // console.log(this.shared.getRequest())
    this.gameId = this.shared.getGame()!.id;
    this.playerLogin = this.shared.getPlayer()!.login;
  }

  ngOnChanges(){
    this.resetAllTables();
    this.handleCharacterTiles();
  }

  resetAllTables(){
    this.availableCharacters = [];
    this.charactersToDisplay = [];
  }

  findCharactersToDisplay(){
    this.playedGameService.getCharacterNotInUse(this.gameId).subscribe( (data: any) => {
      this.availableCharacters = data.characterList;
      this.availableCharacters.forEach( (pgCharacter: PlayedGameCharacter) => {
        this.gameEngineService.getCharacter(pgCharacter.id).subscribe( (geCharacter: Character) => {
          this.charactersToDisplay.push(geCharacter);
        });
      })
    })
  }

  handleCharacterTiles(){
    if(this.selectedCharacterId !== undefined){
      this.gameEngineService.getCharacter(this.selectedCharacterId).subscribe( (data: Character) => {
        this.charactersToDisplay.push(data);
        this.isSelectedFlag = true;
      });
    }
    else{
      this.findCharactersToDisplay();
    }
  }

  // isEmpty(character: any): boolean {
  //   // Check if character is null, undefined, or an empty object
  //   return character === null || character === undefined || Object.keys(character).length === 0;
  // }

  select(character: Character){
    this.playedGameService.selectCharacter(this.gameId, this.playerLogin, character.id).subscribe( (data: any) => {
      this.selectedCharacterId = character.id;
      this.resetAllTables();
      this.handleCharacterTiles();
    });
  }
}
