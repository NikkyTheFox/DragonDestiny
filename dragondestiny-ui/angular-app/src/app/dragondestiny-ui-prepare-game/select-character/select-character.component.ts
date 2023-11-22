import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { PlayedGameCharacter } from '../../interfaces/played-game/character/character';
import { Character } from '../../interfaces/game-engine/character/character';
import { GameEngineService } from '../../services/game-engine/game-engine.service';
import { SharedService } from '../../services/shared.service';
import { Subscription } from 'rxjs';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';

@Component({
  selector: 'app-select-character',
  templateUrl: './select-character.component.html',
  styleUrls: ['./select-character.component.css']
})
export class SelectCharacterComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  gameId!: string;
  playerLogin!: string;
  isSelectedFlag: boolean = false;
  selectedCharacterId!: number;
  availableCharacters: PlayedGameCharacter[] = [];
  charactersToDisplay: Character[] = [];
  messageData!: NotificationMessage;

  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService, private shared: SharedService){

  }

  ngOnInit(){
    this.gameId = this.shared.getGame()!.id;
    this.playerLogin = this.shared.getPlayer()!.login;
    this.resetAllTables();
    this.handleCharacterTiles();
    this.toDeleteSubscription.push(
      this.shared.getSocketMessage().subscribe( (data: any) => {
        this.messageData = this.shared.parseNotificationMessage(data);
        if(this.messageData.notificationOption === NotificationEnum.CHARACTER_CHOSEN){
          this.resetAllTables();
          this.handleCharacterTiles();
        }
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
      this.playedGameService.getPlayersCharacter(this.gameId, this.playerLogin).subscribe( (data: PlayedGameCharacter) => {
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
      this.playedGameService.getCharacterNotInUse(this.gameId).subscribe( (data: any) => {
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

  select(character: Character){
    this.toDeleteSubscription.push(
      this.playedGameService.selectCharacter(this.gameId, this.playerLogin, character.id).subscribe( (data: any) => {
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
