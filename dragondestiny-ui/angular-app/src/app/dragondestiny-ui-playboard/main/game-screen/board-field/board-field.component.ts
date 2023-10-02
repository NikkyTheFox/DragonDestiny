import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Board } from '../../../../interfaces/game-engine/board/board';
import { Field } from '../../../../interfaces/game-engine/field/field';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { GameDataService } from '../../../../services/game-data.service';
import { FieldType } from '../../../../interfaces/game-engine/field/field-type';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { Player } from '../../../../interfaces/played-game/player/player';
import { Subscription } from 'rxjs';
import { SharedService } from '../../../../services/shared.service';
import { GamePlayerRequest } from '../../../../interfaces/game-player-request';
import { FieldList } from '../../../../interfaces/game-engine/field/field-list';
import { PlayedGame } from '../../../../interfaces/played-game/played-game/played-game';

@Component({
  selector: 'app-board-field',
  templateUrl: './board-field.component.html',
  styleUrls: ['./board-field.component.css']
})
export class BoardFieldComponent implements OnInit, OnChanges{
  @Input() board!: Board;
  @Input() fieldIndex!: number;
  @Input() rowIndex!: number;
  requestStructure!:GamePlayerRequest;
  fieldList: Field[] = [];
  fieldName!: FieldType;
  fieldId!: number;
  charactersOnField: PlayedGameCharacter[] = [];
  playersInGame: Player[] = [];
  moveFlag: boolean = false;
  clickDiceRollEventSubscription!: Subscription;
  clickMoveCharacterEventSubscription!: Subscription;

  constructor(private gameService: GameEngineService, private playedGameService: PlayedGameService, private dataService: GameDataService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.clickDiceRollEventSubscription = this.shared.getDiceRollClickEvent().subscribe( (data: any) => {
      this.handlePossibleField();
    });
    this.clickMoveCharacterEventSubscription = this.shared.getMoveCharacterClickEvent().subscribe((data: any) => {
      this.resetField();
      this.handleFieldContent();
    });
  }

  ngOnChanges(changes: SimpleChanges){
    this.resetField();
    this.handleFieldContent()
  }

  handleFieldContent(){
    this.gameService.getBoardFields(this.board.id).subscribe((data: FieldList) => {
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
    this.playedGameService.getPlayers(this.requestStructure.game!.id).subscribe( (data: any) => {
      this.playersInGame = data.playerList;
      this.playersInGame.forEach( (player: Player) => {
        this.charactersOnField.push(player.character);
      });
      this.charactersOnField = this.charactersOnField.filter( (c : PlayedGameCharacter) => {
        return c.field?.id === this.fieldId;
      });
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
    this.playedGameService.changeFieldPositionOfCharacter(this.requestStructure.game!.id, this.requestStructure.player!.login, fieldId).subscribe((data: PlayedGame) => {
      this.shared.sendMoveCharacterClickEvent();
    });
  }
}
