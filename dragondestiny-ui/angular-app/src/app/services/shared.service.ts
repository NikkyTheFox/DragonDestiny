import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { GameDataStructure } from "../interfaces/game-data-structure";
import { PlayedGameService } from "./played-game/played-game-service";
import { PlayedGame } from "../interfaces/played-game/played-game/played-game";
import { Player } from "../interfaces/played-game/player/player";

@Injectable({
  providedIn: 'root'
})
export class SharedService{

  private requestStructure: GameDataStructure = {

  };

  // Loading data
  private gameDataLoaded = new Subject();
  private playerDataLoaded = new Subject();
  private bossFieldDataLoaded = new Subject();
  private bridgeFieldDataLoaded = new Subject();
  private dataLoaded = new Subject();

  // Game mechanics
  private diceRoll = new Subject();
  private moveCharacter = new Subject();
  private drawEnemyCard = new Subject();
  private equipItemCard = new Subject();
  private fightEnemyOnField = new Subject();
  private endTurn = new Subject();
  private blockTurn = new Subject();
  private exchangeTrophies = new Subject();
  private notificationClose = new Subject();

  // Notifications
  private drawCard = new Subject();
  private fightPlayer = new Subject<string>();
  private fightEnemyCard = new Subject<number>();


  constructor(private playedGameService: PlayedGameService){

  }

  // HANDLING DATA LOADING

  setRequestByID(playedGameId: string, playerLogin: string){
    this.setGameByID(playedGameId);
    this.gameDataLoaded.subscribe( () => {
      this.setPlayerByID(playedGameId, playerLogin);
      this.playerDataLoaded.subscribe( () => {
        this.setGameFields(playedGameId);
      });
    });
  }

  setGameByID(gameId: string){
    this.playedGameService.getGame(gameId).subscribe((playedGame: PlayedGame) => {
      this.requestStructure.game = playedGame;
      this.sendGameDataLoadedEvent();
    });
  }

  setPlayerByID(gameId: string, playerLogin: string){
    this.playedGameService.getPlayer(gameId, playerLogin).subscribe( (player: Player) => {
      this.requestStructure.player = player;
      this.sendPlayerDataLoadedEvent();
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

  setGameFields(playedGameId: string){
    this.setBossField(playedGameId);
    this.bossFieldDataLoaded.subscribe( () => {
      this.setBridgeField(playedGameId);
      this.bridgeFieldDataLoaded.subscribe( () => {
        this.sendDataLoadedEvent();
      });
    });
  }

  setBossField(playedGameId: string){
    this.playedGameService.getGameBossField(playedGameId).subscribe((bossFieldId: number) => {
      this.requestStructure.bossFieldId = bossFieldId;
      this.sendBossFieldDataLoadedEvent();
    });
  }

  setBridgeField(playedGameId: string){
    this.playedGameService.getGameBridgeField(playedGameId).subscribe( (bridgeFieldId: number) => {
      this.requestStructure.bridgeFieldId = bridgeFieldId;
      this.sendBridgeFieldDataLoadedEvent();
    })
  }

  getBossField(){
    return this.requestStructure.bossFieldId;
  }

  getBridgeField(){
    return this.requestStructure.bridgeFieldId;
  }

  // HANDLING EVENTS

  sendGameDataLoadedEvent(){
    this.gameDataLoaded.next(null);
  }

  getGameDataLoadedEvent(){
    return this.gameDataLoaded.asObservable();
  }

  sendPlayerDataLoadedEvent(){
    this.playerDataLoaded.next(null);
  }

  getPlayerDataLoadedEvent(){
    return this.playerDataLoaded.asObservable();
  }

  sendBossFieldDataLoadedEvent(){
    this.bossFieldDataLoaded.next(null);
  }

  getBossFieldDataLoadedEvent(){
    return this.bossFieldDataLoaded.asObservable();
  }

  sendBridgeFieldDataLoadedEvent(){
    this.bridgeFieldDataLoaded.next(null);
  }

  getBridgeFieldDataLoadedEvent(){
    return this.bridgeFieldDataLoaded.asObservable();
  }

  sendDataLoadedEvent(){
    this.dataLoaded.next(null);
  }

  getDataLoadedEvent(){
    return this.dataLoaded.asObservable();
  }

  // Game Events

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

  sendFightEnemyOnFieldClickEvent(){
    this.fightEnemyOnField.next(null);
  }

  getFightEnemyOnFieldClickEvent(){
    return this.fightEnemyOnField.asObservable();
  }

  sendEndTurnEvent(){
    this.endTurn.next(null);
  }

  getEndTurnEvent(){
    return this.endTurn.asObservable();
  }

  sendBlockTurnEvent(){
    this.blockTurn.next(null);
  }

  getBlockTurnEvent(){
    return this.blockTurn.asObservable();
  }

  sendExchangeTrophiesEvent(){
    this.exchangeTrophies.next(null);
  }

  getExchangeTrophiesEvent(){
    return this.exchangeTrophies.asObservable();
  }

  sendNotificationCloseEvent(){
    this.notificationClose.next(null);
  }

  getNotificationCloseEvent(){
    return this.notificationClose.asObservable();
  }

//   Notifications

  sendDrawCardClickEvent(){
    this.drawCard.next(null);
  }

  getDrawCardClickEvent(){
    return this.drawCard.asObservable();
  }
  sendFightPlayerClickEvent(playerToFightWithLogin: string){
    this.fightPlayer.next(playerToFightWithLogin);
  }

  getFightPlayerClickEvent(){
    return this.fightEnemyCard.asObservable();
  }

  sendFightEnemyCardClickEvent(cardToFightWithID: number){
    this.fightEnemyCard.next(cardToFightWithID);
  }

  getFightEnemyCardClickEvent(){
    return this.fightEnemyCard.asObservable();
  }
}
