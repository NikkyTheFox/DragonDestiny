import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Board } from '../../../../interfaces/game-engine/board/board';
import { Field as EngineField} from '../../../../interfaces/game-engine/field/field';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { GameDataService } from '../../../../services/game-data.service';
import { FieldType } from '../../../../interfaces/game-engine/field/field-type';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { Player } from '../../../../interfaces/played-game/player/player';
import { Subscription } from 'rxjs';
import { SharedService } from '../../../../services/shared.service';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { FieldList as EngineFieldList} from '../../../../interfaces/game-engine/field/field-list';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { RoundState } from 'src/app/interfaces/played-game/round/round-state';
import { Field } from 'src/app/interfaces/played-game/field/field';

@Component({
  selector: 'app-board-field',
  templateUrl: './board-field.component.html',
  styleUrls: ['./board-field.component.css']
})
export class BoardFieldComponent implements OnInit, OnDestroy{
  boardFieldsSubscription!: Subscription;
  playersSubscription!: Subscription;
  changePositionSubscription!: Subscription;
  roundSubscription!: Subscription;
  fetchPossibleFieldSubscription!: Subscription;

  @Input() board!: Board;
  @Input() fieldIndex!: number;
  @Input() rowIndex!: number;
  requestStructure!:GameDataStructure;
  fieldList: EngineField[] = [];
  fieldName!: FieldType;
  fieldId!: number;
  charactersOnField: PlayedGameCharacter[] = [];
  playersInGame: Player[] = [];
  moveFlag: boolean = false;
  clickDiceRollEventSubscription!: Subscription;
  clickMoveCharacterEventSubscription!: Subscription;
  webSocketMessagePipe!: Subscription;
  messageData!: NotificationMessage;

  constructor(private gameService: GameEngineService, private playedGameService: PlayedGameService, private dataService: GameDataService,
              private shared: SharedService){
  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.clickDiceRollEventSubscription = this.shared.getDiceRollClickEvent().subscribe( (data: any) => {
      this.handlePossibleField();
    });
    this.resetField();
    this.handleFieldContent();
    this.fetchRound();
    this.webSocketMessagePipe = this.shared.getSocketMessage().subscribe( (data: any) => {
      this.messageData = this.shared.parseNotificationMessage(data);
      if(this.messageData.notificationOption === NotificationEnum.POSITION_UPDATED){
        this.resetField();
        this.handleFieldContent()
      }
    })
  }

  fetchRound(){
    // sadly must be done in each field in order to 'check' whether a player can move to the field
    this.roundSubscription = this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
      if(data.roundState == RoundState.WAITING_FOR_FIELDS_TO_MOVE){
        this.fetchPossibleFieldSubscription = this.playedGameService.checkPossibleNewPositions(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: any) => {
          this.dataService.possibleFields = data.fieldList;
          this.handlePossibleField();
        })
      }
      if(data.roundState == RoundState.WAITING_FOR_MOVE){
        this.dataService.possibleFields = data.fieldListToMove;
        this.handlePossibleField();
      }      
    })
  }

  handleFieldContent(){
    this.boardFieldsSubscription = this.gameService.getBoardFields(this.board.id).subscribe((data: EngineFieldList) => {
      this.fieldList = data.fieldList;
      this.retrieveFieldType();
      this.retrieveCharactersOnField();
    });
  }


  retrieveFieldType(){
    for(let i of this.fieldList){
      if(i.yposition == this.rowIndex && i.xposition == this.fieldIndex){
        this.fieldName = i.type;
        this.fieldId = i.id;
      }
    }
  }

  retrieveCharactersOnField(){
    this.playersSubscription = this.playedGameService.getPlayers(this.requestStructure.game!.id).subscribe( (data: any) => {
      this.playersInGame = data.playerList;
      this.playersInGame.forEach( (player: Player) => {
        this.charactersOnField.push(player.character);
      });
      this.charactersOnField = this.charactersOnField.filter( (c : PlayedGameCharacter) => {
        return c.field?.id === this.fieldId;
      });
      this.charactersOnField = this.removeDuplicates(this.charactersOnField);
    });
  }

  handlePossibleField(){
    this.dataService.possibleFields.forEach( (data: Field) => {
      if(this.fieldId === data.id){
       this.moveFlag = true;
      }
    });
  }

  resetField(){
    this.fieldList = [];
    this.charactersOnField = [];
    this.playersInGame = [];
    this.moveFlag = false;
  }

  moveCharacter(fieldId: number){
    this.changePositionSubscription = this.playedGameService.changeFieldPositionOfCharacter(this.requestStructure.game!.id, this.requestStructure.player!.login, fieldId).subscribe( () => {
      this.shared.sendMoveCharacterClickEvent();
    });
  }

  removeDuplicates(array: PlayedGameCharacter[]) {
    let toReturn: PlayedGameCharacter[] = [];
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
      this.changePositionSubscription?.unsubscribe();
      this.playersSubscription?.unsubscribe();
      this.boardFieldsSubscription?.unsubscribe();
      this.webSocketMessagePipe?.unsubscribe();
      this.clickDiceRollEventSubscription?.unsubscribe();
  }
}
