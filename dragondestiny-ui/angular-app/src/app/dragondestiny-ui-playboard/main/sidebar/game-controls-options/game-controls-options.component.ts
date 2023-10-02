import {Component, Input, OnInit} from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { SharedService } from '../../../../services/shared.service';
import { Field } from '../../../../interfaces/game-engine/field/field';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { Player } from '../../../../interfaces/played-game/player/player';
import { Character } from '../../../../interfaces/game-engine/character/character';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { GamePlayerRequest } from '../../../../interfaces/game-player-request';

@Component({
  selector: 'app-game-controls-options',
  templateUrl: './game-controls-options.component.html',
  styleUrls: ['./game-controls-options.component.css']
})
export class GameControlsOptionsComponent implements OnInit{
  requestStructure!: GamePlayerRequest;

  enemyOnFieldFlag: boolean = false;
  playerOnFieldFlag: boolean = false;
  fieldTypeTakeOneCardFlag: boolean = false;
  fieldTypeTakeTwoCardsFlag: boolean = false;
  fieldTypeLoseOneRoundFlag: boolean = false;
  fieldTypeLoseTwoRoundFlag: boolean = false;
  fieldTypeBridgeFieldFlag: boolean = false;
  fieldTypeBossFieldFlag: boolean = false;

  numberOfCardsToBeDrawn: number = 0;
  numberOfTurnsToBeLost: number = 0;
  currentField: any = null;
  fieldType: string = '';
  enemyName: number = 0;

  playersCharacter!: PlayedGameCharacter;
  playersOnField: Player[] = [];
  filteredOutPlayers: Player[] = [];
  charactersOnField: Character[] = [];
  enemyOnField: string[] = [];


  constructor(private playedGameService: PlayedGameService, private gameEngineService: GameEngineService,private shared: SharedService){
  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.resetOptions();
    this.handleOptions();
  }

  handleOptions(){
    this.getPlayerCharacter();
  }

  getField(){
    this.playedGameService.getPlayersCharacter(this.requestStructure.game.id, this.requestStructure.player.login).subscribe((data: PlayedGameCharacter) => {
      this.currentField = data.field;
      this.checkEnemyOnField(this.currentField);
      this.checkPlayerOnField(this.currentField);
    });
  }

  getPlayerCharacter(){
    this.playedGameService.getPlayersCharacter(this.requestStructure.game.id, this.requestStructure.player.login).subscribe((data: PlayedGameCharacter) => {
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
    this.playedGameService.getPlayersOnField(this.requestStructure.game.id, field.id).subscribe((data: any) => {
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

  goToBossRoom(){

  }

  confirmTurnLoss(){

  }

  drawCard(){

  }

  attackEnemyOnField(){

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
