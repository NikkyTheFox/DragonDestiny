import { Component, Input } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { SharedService } from '../../../../services/shared.service';
import { Field } from '../../../../interfaces/game-engine/field/field';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { Player } from '../../../../interfaces/played-game/player/player';
import { Character } from '../../../../interfaces/game-engine/character/character';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { RequestStructureGameidPlayerlogin } from '../../../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-game-controls-options',
  templateUrl: './game-controls-options.component.html',
  styleUrls: ['./game-controls-options.component.css']
})
export class GameControlsOptionsComponent {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;

  enemyOnFieldFlag: boolean;
  playerOnFieldFlag: boolean;
  fieldTypeTakeOneCardFlag: boolean;
  fieldTypeTakeTwoCardsFlag: boolean;
  fieldTypeLoseOneRoundFlag: boolean;
  fieldTypeLoseTwoRoundFlag: boolean;
  fieldTypeBridgeFieldFlag: boolean;
  fieldTypeBossFieldFlag: boolean;

  numberOfCardsToBeDrawn: number;
  numberOfTurnsToBeLost: number
  currentField!: any;
  fieldType: string;
  enemyName: number;

  playersCharacter!: PlayedGameCharacter;
  playersOnField: Player[];
  filteredOutPlayers: Player[];
  charactersOnField: Character[];
  enemyOnField: string[];


  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService,private shared: SharedService) {
    this.enemyOnFieldFlag = false;
    this.playerOnFieldFlag = false;
    this.fieldTypeTakeOneCardFlag = false;
    this.fieldTypeTakeTwoCardsFlag = false;
    this.fieldTypeLoseOneRoundFlag = false;
    this.fieldTypeLoseTwoRoundFlag = false;
    this.fieldTypeBridgeFieldFlag = false;
    this.fieldTypeBossFieldFlag = false;

    this.numberOfCardsToBeDrawn = 0;
    this.numberOfTurnsToBeLost = 0;
    this.currentField = null;
    this.fieldType = '';
    this.enemyName = 0;

    this.playersOnField = [];
    this.filteredOutPlayers = [];
    this.charactersOnField = [];
    this.enemyOnField = [];
  }

  ngOnInit(){
    this.resetOptions();
    this.handleOptions();
  }

  handleOptions(){
    this.getPlayerCharacter();
  }

  getField(){
    this.playedGameService.getPlayersCharacter(this.requestStructure.gameId, this.requestStructure.playerLogin).subscribe((data: PlayedGameCharacter) => {
      this.currentField = data.field;
      this.checkEnemyOnField(this.currentField);
      this.checkPlayerOnField(this.currentField);
    });
  }

  getPlayerCharacter(){
    this.playedGameService.getPlayersCharacter(this.requestStructure.gameId, this.requestStructure.playerLogin).subscribe((data: PlayedGameCharacter) => {
      this.playersCharacter = data;
      this.getField();
    });
  }

  checkEnemyOnField(field: Field){
    if(field.enemy !== null){
      this.enemyOnFieldFlag = true;
      //this.enemyOnField.push(field.enemy.id);
      this.enemyName = field.enemy.id;
    }
  }

  checkPlayerOnField(field: Field){
    this.playedGameService.getPlayersOnField(this.requestStructure.gameId, field.id).subscribe((data: any) => {
      this.playersOnField = data.playerList;
      this.playersOnField.forEach((player: Player) => {
        if(player.character.id !== this.playersCharacter.id){
          this.filteredOutPlayers.push(player);
        }
      });
      if(this.filteredOutPlayers.length > 0){
        this.playerOnFieldFlag = true;
      }
      this.filteredOutPlayers.forEach((player: Player) => {
        this.gameEngineService.getCharacter(player.character.id).subscribe((data: Character) => {
          this.charactersOnField.push(data);
        });
      });
      this.checkFieldOption();
    });
  }

  checkFieldOption(){
    this.fieldType = this.currentField.type;
    switch (this.fieldType){
      case 'TAKE_ONE_CARD':
        this.fieldTypeTakeOneCardFlag = true;
        this.numberOfCardsToBeDrawn = 1;
        break;
      case 'TAKE_TWO_CARDS':
        this.fieldTypeTakeTwoCardsFlag = true;
        this.numberOfCardsToBeDrawn = 2;
        break;
      case 'LOSE_ONE_ROUND':
        this.fieldTypeLoseOneRoundFlag = true;
        this.numberOfTurnsToBeLost = 1;
        break;
      case 'LOSE_TWO_ROUNDS':
        this.fieldTypeLoseTwoRoundFlag = true;
        this.numberOfTurnsToBeLost = 2;
        break;
      case 'BRIDGE_FIELD':
        this.fieldTypeBridgeFieldFlag = true;
        break;
      case 'BOSS_FIELD':
        this.fieldTypeBossFieldFlag = true;
        break;
    }
  }

  goToBossRoom() {

  }

  confirmTurnLoss() {

  }

  drawCard() {

  }

  attackEnemyOnField() {

  }

  resetOptions(){
    this.enemyOnFieldFlag = false;
    this.playerOnFieldFlag = false;
    this.fieldTypeTakeOneCardFlag = false;
    this.fieldTypeTakeTwoCardsFlag = false;
    this.fieldTypeLoseOneRoundFlag = false;
    this.fieldTypeLoseTwoRoundFlag = false;
    this.fieldTypeBridgeFieldFlag = false;
    this.fieldTypeBossFieldFlag = false;

    this.numberOfCardsToBeDrawn = 0;
    this.numberOfTurnsToBeLost = 0;
    this.currentField = null;
    this.fieldType = '';

    this.playersOnField = [];
    this.filteredOutPlayers = [];
    this.charactersOnField = [];
    this.enemyOnField = [];
  }
}
