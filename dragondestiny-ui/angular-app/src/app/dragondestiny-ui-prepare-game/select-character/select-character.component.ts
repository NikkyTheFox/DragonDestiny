import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { PlayedGameCharacter } from '../../interfaces/played-game/character/character';
import { Character } from '../../interfaces/game-engine/character/character';
import { GameEngineService } from '../../services/game-engine/game-engine.service';
import { SharedService } from '../../services/shared.service';
import { Subscription } from 'rxjs';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';
import { GameDataStructure } from 'src/app/interfaces/game-data-structure';
import { PlayerList } from 'src/app/interfaces/played-game/player/player-list';
import { CharacterList } from 'src/app/interfaces/played-game/character/character-list';

@Component({
  selector: 'app-select-character',
  templateUrl: './select-character.component.html',
  styleUrls: ['./select-character.component.css']
})
export class SelectCharacterComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  isSelectedFlag: boolean = false;
  selectedCharacterId!: number;
  availableCharacters: PlayedGameCharacter[] = [];
  charactersToDisplay: Character[] = [];
  numOfSelected: number = 0;
  numOfPlayers: number = 0;
  messageData!: NotificationMessage;

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    console.log('chuj a nie herbata')
    this.resetAllTables();
    this.handleCharacterTiles();
    this.fetchCharacterPlayerStatistic();
    this.toDeleteSubscription.push(
      this.shared.getSocketMessage().subscribe( (data: any) => {
        this.messageData = this.shared.parseNotificationMessage(data);
        if(this.messageData.notificationOption === NotificationEnum.CHARACTER_CHOSEN){
          this.resetAllTables();
          this.handleCharacterTiles();
          this.fetchCharacterPlayerStatistic();
        }
      })
    );
    this.toDeleteSubscription.push(
      this.shared.getPlayerInvitedEvent().subscribe( () => {
        console.log('player got invited lul')
        this.fetchCharacterPlayerStatistic();
      })
    );
  }

  resetAllTables(){
    this.availableCharacters = [];
    this.charactersToDisplay = [];
  }

  handleCharacterTiles(){
    this.checkOwnCharacter();
  }
  
  checkOwnCharacter(){
    this.toDeleteSubscription.push(
      this.playedGameService.getPlayersCharacter(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: PlayedGameCharacter) => {
        this.selectedCharacterId = data.id;
        this.handleChosenCharacter()
      },
      (error: any) => {
        this.findCharactersToDisplay();
      })
    );
  }

  findCharactersToDisplay(){
    this.toDeleteSubscription.push(
      this.playedGameService.getCharacterNotInUse(this.requestStructure.game!.id).subscribe( (data: any) => {
        this.availableCharacters = data.characterList;
        this.availableCharacters.forEach( (pgCharacter: PlayedGameCharacter) => {
          this.toDeleteSubscription.push(
            this.gameEngineService.getCharacter(pgCharacter.id).subscribe( (geCharacter: Character) => {
              this.charactersToDisplay.push(geCharacter);
            })
          );
        })
      })
    );
  }
  
  handleChosenCharacter(){
    this.toDeleteSubscription.push(
      this.gameEngineService.getCharacter(this.selectedCharacterId).subscribe( (data: Character) => {
        this.charactersToDisplay.push(data);
        this.charactersToDisplay = this.removeDuplicates(this.charactersToDisplay)
        this.isSelectedFlag = true;
      })
    );
  }

  fetchCharacterPlayerStatistic(){
    console.log('heherbata')
    this.toDeleteSubscription.push(
      this.playedGameService.getPlayers(this.requestStructure.game!.id).subscribe( (data: PlayerList) => {
        this.numOfPlayers = data.playerList.length;
        console.log('num of players')
        console.log(this.numOfPlayers)
      })
    );
    this.toDeleteSubscription.push(
      this.playedGameService.getCharactersInUse(this.requestStructure.game!.id).subscribe( (data: CharacterList) => {
        this.numOfSelected = data.characterList.length;
        console.log('num selected')
        console.log(this.numOfSelected)
      })
    );
  }

  select(character: Character){
    this.toDeleteSubscription.push(
      this.playedGameService.selectCharacter(this.requestStructure.game!.id, this.requestStructure.player!.login, character.id).subscribe( (data: any) => {
        this.selectedCharacterId = character.id;
        this.resetAllTables();
        this.handleCharacterTiles();
      })
    );
  }

  removeDuplicates(array: Character[]) {
    let toReturn: Character[] = [];
    for(let i = 0; i < array.length; i++){
      let toInsertCheck = true;
      for(let j = 0; j < toReturn.length; j++){
        if(array[i].id == toReturn[j].id){
          toInsertCheck = false;
        }
      }
      if(toInsertCheck){
        toReturn.push(array[i]);
      }
    }
    return toReturn;
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
