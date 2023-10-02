import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { GamePlayerRequest } from "../interfaces/game-player-request";
import { PlayedGameService } from "./played-game/played-game-service";
import { PlayedGame } from "../interfaces/played-game/played-game/played-game";
import { Player } from "../interfaces/played-game/player/player";

@Injectable({
  providedIn: 'root'
})
export class SharedService{

  private requestStructure: GamePlayerRequest = {

  };

  private diceRoll = new Subject();
  private moveCharacter = new Subject();
  private drawCard = new Subject();
  private drawEnemyCard = new Subject();
  private equipItemCard = new Subject();
  private fightPlayer = new Subject();
  private fightEnemyCard = new Subject();
  private fightEnemyOnField = new Subject();

  constructor(private playedGameService: PlayedGameService){

  }

  setRequestByID(gameId: string, playerLogin: string){
    this.setGameByID(gameId);
    this.setPlayerByID(gameId, playerLogin);
    console.log("XDDDDDDD KUrwa")
    console.log(this.requestStructure.game);
  }

  setGameByID(gameId: string){
    this.playedGameService.getGame(gameId).subscribe((playedGame: PlayedGame) => {
      this.requestStructure.game = playedGame;
    });
  }

  setPlayerByID(gameId: string, playerLogin: string){
    this.playedGameService.getPlayer(gameId, playerLogin).subscribe( (player: Player) => {
      this.requestStructure.player = player;
    });
  }

  setRequest(game: PlayedGame, player: Player){
    this.setGame(game);
    this.setPlayer(player);
  }

  setGame(game: PlayedGame){
    this.requestStructure.game = game;
  }

  setPlayer(player: Player){
    this.requestStructure.player = player;
  }

  getRequest(){
    return this.requestStructure;
  }

  getGame(){
    return this.requestStructure.game;
  }

  getPlayer(){
    return this.requestStructure.player;
  }

  sendDiceRollClickEvent(){
    this.diceRoll.next(null);
  }

  getDiceRollClickEvent(){
    return this.diceRoll.asObservable();
  }

  sendMoveCharacterClickEvent(){
    this.moveCharacter.next(null);
  }

  getMoveCharacterClickEvent(){
    return this.moveCharacter.asObservable();
  }

  sendDrawCardClickEvent(){
    this.drawCard.next(null);
  }

  getDrawCardClickEvent(){
    return this.drawCard.asObservable();
  }

  sendDrawEnemyCardClickEvent(){
    this.drawEnemyCard.next(null);
  }

  getDrawEnemyCardClickEvent(){
    return this.drawEnemyCard.asObservable();
  }

  sendEquipItemCardClickEvent(){
    this.equipItemCard.next(null);
  }

  getEquipItemCardClickEvent(){
    return this.equipItemCard.asObservable();
  }

  sendFightPlayerClickEvent(){
    this.fightPlayer.next(null);
  }

  getFightPlayerClickEvent(){
    return this.fightEnemyCard.asObservable();
  }

  sendFightEnemyCardClickEvent(){
    this.fightEnemyCard.next(null);
  }

  getFightEnemyCardClickEvent(){
    return this.fightEnemyCard.asObservable();
  }

  sendFightEnemyOnFieldClickEvent(){
    this.fightEnemyOnField.next(null);
  }

  getFightEnemyOnFieldClickEvent(){
    return this.fightEnemyOnField.asObservable();
  }
}
