import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Board } from '../../../../../../interfaces/game-engine/board/board';
import { Field as EngineField} from '../../../../../../interfaces/game-engine/field/field';
import { GameEngineService } from '../../../../../../services/game-engine/game-engine.service';
import { GameDataService } from '../../../../../../services/game-data.service';
import { FieldType } from '../../../../../../interfaces/game-engine/field/field-type';
import { PlayedGameCharacter } from '../../../../../../interfaces/played-game/character/character';
import { PlayedGameService } from '../../../../../../services/played-game/played-game-service';
import { Player } from '../../../../../../interfaces/played-game/player/player';
import { Subscription } from 'rxjs';
import { SharedService } from '../../../../../../services/shared.service';
import { GameDataStructure } from '../../../../../../interfaces/game-data-structure';
import { FieldList as EngineFieldList} from '../../../../../../interfaces/game-engine/field/field-list';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';
import { Field } from 'src/app/interfaces/played-game/field/field';
import { Character } from 'src/app/interfaces/game-engine/character/character';

@Component({
  selector: 'app-board-field',
  templateUrl: './board-field.component.html',
  styleUrls: ['./board-field.component.css']
})
export class BoardFieldComponent implements OnInit, OnDestroy{
  @Input() board!: Board;
  @Input() fieldIndex!: number;
  @Input() rowIndex!: number;
  @Input() conditionFlag: boolean = false;
  toDeleteSubscription: Subscription[] = [];
  requestStructure!:GameDataStructure;
  fieldList: EngineField[] = [];
  fieldName!: FieldType;
  fieldId!: number;
  charactersOnField: PlayedGameCharacter[] = [];
  engineCharactersOnField: Character[] = [];
  playersInGame: Player[] = [];
  moveFlag: boolean = false;
  messageData!: NotificationMessage;

  constructor(private engineService: GameEngineService, private playedGameService: PlayedGameService, private dataService: GameDataService,
              private shared: SharedService){
  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.toDeleteSubscription.push(
      this.shared.getDiceRollClickEvent().subscribe( (data: any) => {
        this.handlePossibleField();
      })
    );
    this.resetField();
    this.handleFieldContent();
    this.toDeleteSubscription.push(
      this.shared.getSocketMessage().subscribe( (data: any) => {
        this.messageData = this.shared.parseNotificationMessage(data);
        if(this.messageData.notificationOption === NotificationEnum.POSITION_UPDATED){
          this.resetField();
          this.handleFieldContent()
        };
      })
    );
  }

  handleFieldContent(){
    this.toDeleteSubscription.push(
      this.engineService.getBoardFields(this.board.id).subscribe((data: EngineFieldList) => {
        this.fieldList = data.fieldList;
        this.retrieveFieldType();
        this.retrieveCharactersOnField();
        if(this.conditionFlag){
          // pass down fetchRound() check from Board (to minimize requests made in multiple field components)
          this.handlePossibleField();
        };
      })
    );
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
    this.toDeleteSubscription.push(
      this.playedGameService.getPlayers(this.requestStructure.game!.id).subscribe( (data: any) => {
        this.playersInGame = data.playerList;
        this.playersInGame.forEach( (player: Player) => {
          this.charactersOnField.push(player.character);
        });
        this.charactersOnField = this.charactersOnField.filter( (c : PlayedGameCharacter) => {
          return c.field?.id === this.fieldId;
        });
        this.charactersOnField = this.removeDuplicates(this.charactersOnField);
        this.retrieveEngineCharactersOnField();
      })
    );
  }

  retrieveEngineCharactersOnField(){
    this.charactersOnField.forEach( (pc: PlayedGameCharacter) => {
      this.toDeleteSubscription.push(
        this.engineService.getCharacter(pc.id).subscribe( (data: Character) => {
          this.engineCharactersOnField.push(data);
        })
      );
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
    this.engineCharactersOnField = [];
    this.playersInGame = [];
    this.moveFlag = false;
  }

  moveCharacter(fieldId: number){
    this.toDeleteSubscription.push(
      this.playedGameService.changeFieldPositionOfCharacter(this.requestStructure.game!.id, this.requestStructure.player!.login, fieldId).subscribe( () => {
        this.shared.sendMoveCharacterClickEvent();
      })
    );
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
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
