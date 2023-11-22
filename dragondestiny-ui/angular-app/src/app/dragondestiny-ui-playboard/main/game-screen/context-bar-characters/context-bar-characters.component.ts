import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { Player } from '../../../../interfaces/played-game/player/player';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { Character } from '../../../../interfaces/game-engine/character/character';
import { SharedService } from '../../../../services/shared.service';
import { Subscription } from 'rxjs';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';

@Component({
  selector: 'app-context-bar-characters',
  templateUrl: './context-bar-characters.component.html',
  styleUrls: ['./context-bar-characters.component.css']
})
export class ContextBarCharactersComponent implements OnInit, OnChanges, OnDestroy{
  playersCharacterSubscription!: Subscription;
  playersSubscription!: Subscription;
  tempSubscription!: Subscription;
  characterSubscritpionList: Subscription[] = [];
  webSocketMessagePipe!: Subscription;

  requestStructure!: GameDataStructure;
  playersCharacter!: PlayedGameCharacter;
  otherCharacters: PlayedGameCharacter[] = [];
  allCharacters: PlayedGameCharacter[] = [];
  characterNames: string[] = [];
  messageData!: NotificationMessage;

  constructor(protected gameEngineService: GameEngineService, private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.handleCharacters();
    this.webSocketMessagePipe = this.shared.getSocketMessage().subscribe( (data: any) => {
      this.messageData = this.shared.parseNotificationMessage(data);
      if(this.messageData.notificationOption === NotificationEnum.NEXT_ROUND){
        this.reset();
        this.handleCharacters();
      }
    })
  }

  ngOnChanges(changes: SimpleChanges){
    this.handleCharacters();
  }

  reset(){
    this.otherCharacters = [];
    this.allCharacters = [];
    this.characterNames = [];
  }

  handleCharacters(){
    this.playersCharacterSubscription = this.playedGameService.getPlayersCharacter(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: PlayedGameCharacter) => {
      this.playersCharacter = data;
      this.filterOutPlayers();
    });
  }

  filterOutPlayers(){
    this.playersSubscription = this.playedGameService.getPlayers(this.requestStructure.game!.id).subscribe( (data: any) => {
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
      this.tempSubscription = this.gameEngineService.getCharacter(character.id).subscribe((data: Character) => {
        this.characterNames.push(data.name);
      });
      this.characterSubscritpionList.push(this.tempSubscription);
    });
  }

  ngOnDestroy(): void {
      this.characterSubscritpionList.forEach( (s: Subscription) => {
        s?.unsubscribe();
      })
      this.tempSubscription?.unsubscribe();
      this.playersSubscription?.unsubscribe();
      this.playersCharacterSubscription?.unsubscribe();
      this.webSocketMessagePipe?.unsubscribe();
  }
}
