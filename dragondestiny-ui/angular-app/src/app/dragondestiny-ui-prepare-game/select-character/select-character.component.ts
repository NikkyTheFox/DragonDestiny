import { Component, OnChanges, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { PlayedGameCharacter } from '../../interfaces/played-game/character/character';
import { Character } from '../../interfaces/game-engine/character/character';
import { GameEngineService } from '../../services/game-engine/game-engine.service';
import { SharedService } from "../../services/shared.service";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-select-character',
  templateUrl: './select-character.component.html',
  styleUrls: ['./select-character.component.css']
})
export class SelectCharacterComponent implements OnInit, OnDestroy{
  playersCharacterSubscription!: Subscription;
  characterNotInUserSubscription!: Subscription;
  tempSubscription!: Subscription;
  characterSubscriptionList: Subscription[] = [];
  getCharacterSubscription!: Subscription;
  SelectCharacterSubscription!: Subscription;

  gameId!: string;
  playerLogin!: string;
  isSelectedFlag: boolean = false;
  selectedCharacterId!: number;
  availableCharacters: PlayedGameCharacter[] = [];
  charactersToDisplay: Character[] = [];

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService, private shared: SharedService){

  }

  ngOnInit(){
    this.gameId = this.shared.getGame()!.id;
    this.playerLogin = this.shared.getPlayer()!.login;
    this.resetAllTables();
    this.handleCharacterTiles();
  }

  // ngOnChanges(){
  //   this.resetAllTables();
  //   this.handleCharacterTiles();
  // }

  resetAllTables(){
    this.availableCharacters = [];
    this.charactersToDisplay = [];
  }

  handleCharacterTiles(){
    this.checkOwnCharacter();
  }
  
  checkOwnCharacter(){
    this.playersCharacterSubscription = this.playedGameService.getPlayersCharacter(this.gameId, this.playerLogin).subscribe( (data: PlayedGameCharacter) => {
      this.selectedCharacterId = data.id;
      this.handleChosenCharacter()
    },
    (error: any) => {
      this.findCharactersToDisplay();
    });
  }

  findCharactersToDisplay(){
    this.characterNotInUserSubscription = this.playedGameService.getCharacterNotInUse(this.gameId).subscribe( (data: any) => {
      this.availableCharacters = data.characterList;
      this.availableCharacters.forEach( (pgCharacter: PlayedGameCharacter) => {
        this.tempSubscription = this.gameEngineService.getCharacter(pgCharacter.id).subscribe( (geCharacter: Character) => {
          this.charactersToDisplay.push(geCharacter);
        });
        this.characterSubscriptionList.push(this.tempSubscription);
      })
    })
  }


  
  handleChosenCharacter(){
    this.getCharacterSubscription = this.gameEngineService.getCharacter(this.selectedCharacterId).subscribe( (data: Character) => {
      this.charactersToDisplay.push(data);
      this.isSelectedFlag = true;
    });
  }

  // isEmpty(character: any): boolean {
  //   // Check if character is null, undefined, or an empty object
  //   return character === null || character === undefined || Object.keys(character).length === 0;
  // }

  select(character: Character){
    this.SelectCharacterSubscription = this.playedGameService.selectCharacter(this.gameId, this.playerLogin, character.id).subscribe( (data: any) => {
      this.selectedCharacterId = character.id;
      this.resetAllTables();
      this.handleCharacterTiles();
    });
  }

  ngOnDestroy(): void {
    this.SelectCharacterSubscription?.unsubscribe();
    this.getCharacterSubscription?.unsubscribe();
    this.characterSubscriptionList.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
    this.tempSubscription?.unsubscribe();
    this.characterNotInUserSubscription?.unsubscribe();
    this.playersCharacterSubscription?.unsubscribe();
  }
}
