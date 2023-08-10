import {Component, Input, SimpleChanges} from '@angular/core';
import {Board} from "../../interfaces/game-engine/board";
import {Field} from "../../interfaces/game-engine/field";
import {GameEngineService} from "../../services/game-engine.service";
import {GameDataService} from "../../services/game-data.service";
import {FieldType} from "../../interfaces/game-engine/field-type";
import {PlayedGameCharacter} from "../../interfaces/game-played-game/played-game-character";
import {PlayedGameService} from "../../services/played-game-service";
import {PlayedGamePlayer} from "../../interfaces/game-played-game/played-game-player";
import {Subscription} from "rxjs";
import {SharedService} from "../../services/shared.service";
import {GameFieldOption} from "../../interfaces/game-field-option";
import {Router} from "@angular/router";


@Component({
  selector: 'app-main-section-board-field',
  templateUrl: './main-section-board-field.component.html',
  styleUrls: ['./main-section-board-field.component.css']
})
export class MainSectionBoardFieldComponent {
  @Input() board!: Board;
  @Input() fieldIndex!: number;
  @Input() rowIndex!: number;
  @Input() gameId!: string;
  @Input() playerLogin!: string;
  fieldsList: Field[];
  fieldName!: FieldType;
  fieldId!: number;
  charactersOnField: PlayedGameCharacter[];
  playersInGame: PlayedGamePlayer[];
  moveFlag: boolean;
  clickDiceRollEventSubscription:Subscription;
  clickMoveCharacterEventSubscription:Subscription;

  constructor(private gameService: GameEngineService, private playedGameService: PlayedGameService, private dataService: GameDataService, private shared: SharedService, private router: Router) {
    this.fieldsList = [];
    this.charactersOnField = [];
    this.playersInGame = [];
    this.moveFlag = false;
    this.clickDiceRollEventSubscription = this.shared.getDiceRollClickEvent().subscribe( (data: any) => {
      this.handlePossibleField();
    });
    this.clickMoveCharacterEventSubscription = this.shared.getMoveCharacterClickEvent().subscribe((data: any) => {
      this.resetField();
      this.handleFieldContent();
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    this.resetField();
    this.handleFieldContent()
  }

  handleFieldContent(){
    this.gameService.getBoardFields(this.board.id).subscribe((data: any) => {
      this.fieldsList = data.fieldList;
      this.retrieveFieldType();
      this.retrieveCharactersOnField();
    });
  }


  retrieveFieldType() {
    for(let i of this.fieldsList){
      if(i.yposition == this.rowIndex && i.xposition == this.fieldIndex){
        this.fieldName = i.type;
        this.fieldId = i.id;
      }
    }
  }

  retrieveCharactersOnField(){
    this.playedGameService.getPlayers(this.gameId).subscribe( (data: any) => {
      this.playersInGame = data.playerList;
      this.playersInGame.forEach( (player: PlayedGamePlayer) => {
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
    this.fieldsList = [];
    this.charactersOnField = [];
    this.playersInGame = [];
    this.moveFlag = false;
  }

  moveCharacter(fieldId: number) {
    this.playedGameService.changeFieldPositionOfCharacter(this.gameId, this.playerLogin, fieldId).subscribe((data:GameFieldOption[]) => {
      this.shared.sendMoveCharacterClickEvent();
    });
  }
}
