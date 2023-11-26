import { NotificationEnum } from './../interfaces/played-game/notification/notification-enum';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { GameDataStructure } from '../interfaces/game-data-structure';
import { PlayedGameService } from './played-game/played-game-service';
import { PlayedGame } from '../interfaces/played-game/played-game/played-game';
import { Player } from '../interfaces/played-game/player/player';
import { WebsocketService } from './websocket.service';
import { NotificationMessage } from '../interfaces/played-game/notification/notification-message';
import { Round } from '../interfaces/played-game/round/round';
import { UpdateEnum } from '../interfaces/played-game/notification/update-enum';
import { ItemCard } from '../interfaces/played-game/card/item-card/item-card';

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
  private playerLogin = new Subject();

  // Game mechanics
  private diceRoll = new Subject();
  private moveCharacter = new Subject();
  // private drawEnemyCard = new Subject();
  private refreshHandCards = new Subject();
  // private fightEnemyOnField = new Subject();
  private endTurn = new Subject();
  private blockTurn = new Subject();
  // private exchangeTrophies = new Subject();
  private refreshCharacterStats = new Subject();
  private itemToDiscard = new Subject();

  // Notifications
  private notificationClose = new Subject();
  private drawCard = new Subject<number>();
  // private fightPlayer = new Subject<string>();
  private notifyAttackerPlayer = new Subject<string>();
  private notifyDefenderPlayer = new Subject<string>();
  private fightEnemyCard = new Subject<number>();
  private continueGame = new Subject<Round>();
  private updateGame = new Subject<{updateType: UpdateEnum, 
                                    player1Login: string | null, 
                                    player2Login: string | null, 
                                    cardId: number | null,
                                    numTurnsBlock: number | null}>();
  // private continue = new Subject();

  // Web Socket
  private socket!: WebSocket;
  private socketMessage = new Subject<NotificationMessage>();


  constructor(private playedGameService: PlayedGameService, private wsService: WebsocketService){

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

  sendPlayerLoginEvent(){
    this.playerLogin.next(null);
  }

  getPlayerLoginEvent(){
    return this.playerLogin.asObservable();
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

  // sendDrawEnemyCardClickEvent(){
  //   this.drawEnemyCard.next(null);
  // }

  // getDrawEnemyCardClickEvent(){
  //   return this.drawEnemyCard.asObservable();
  // }

  sendRefreshHandCardsEvent(){
    this.refreshHandCards.next(null);
    this.sendRefreshCharacterStatsEvent();
  }

  getRefreshHandCardsEvent(){
    return this.refreshHandCards.asObservable();
  }

  // sendFightEnemyOnFieldEvent(){
  //   this.fightEnemyOnField.next(null);
  // }

  // getFightEnemyOnFieldEvent(){
  //   return this.fightEnemyOnField.asObservable();
  // }

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

  // sendExchangeTrophiesEvent(){
  //   this.exchangeTrophies.next(null);
  // }

  // getExchangeTrophiesEvent(){
  //   return this.exchangeTrophies.asObservable();
  // }

  sendNotificationCloseEvent(){
    this.notificationClose.next(null);
  }

  getNotificationCloseEvent(){
    return this.notificationClose.asObservable();
  }

  sendRefreshCharacterStatsEvent(){
    this.refreshCharacterStats.next(null);
  }

  getRefreshCharacterStatsEvent(){
    return this.refreshCharacterStats.asObservable();
  }

  sendItemToDiscardEvent(){
    this.itemToDiscard.next(null);
  }

  getItemToDiscardEvent(){
    return this.itemToDiscard.asObservable();
  }

//   Notifications

  sendDrawCardClickEvent(numberOfCards: number){
    this.drawCard.next(numberOfCards);
  }

  getDrawCardClickEvent(){
    return this.drawCard.asObservable();
  }

  // sendFightPlayerClickEvent(playerToFightWithLogin: string){
  //   this.fightPlayer.next(playerToFightWithLogin);
  // }

  // getFightPlayerClickEvent(){
  //   return this.fightPlayer.asObservable();
  // }

  sendNotifyAttackerPlayerEvent(defenderLogin: string){
    this.notifyAttackerPlayer.next(defenderLogin);
  }

  getNotifyAttackerPlayerEvent(){
    return this.notifyAttackerPlayer.asObservable();
  }

  sendNotifyDefenderPlayerEvent(attackerLogin: string){
    this.notifyDefenderPlayer.next(attackerLogin);
  }

  getNotifyDefenderPlayerEvent(){
    return this.notifyDefenderPlayer.asObservable();
  }

  sendFightEnemyCardEvent(cardToFightWithID: number){
    this.fightEnemyCard.next(cardToFightWithID);
  }

  getFightEnemyCardEvent(){
    return this.fightEnemyCard.asObservable();
  }

  sendContinuteGameEvent(round: Round){
    this.continueGame.next(round);
  }

  getContinuteGameEvent(){
    return this.continueGame.asObservable();
  }

  sendUpdateGameEvent(updateType: UpdateEnum, player1Login: string | null, player2Login: string | null, cardId: number | null, numTurnsBlock: number | null){
    this.updateGame.next({updateType: updateType, player1Login: player1Login, player2Login: player2Login, cardId: cardId, numTurnsBlock: numTurnsBlock});
  }

  getUpdateGameEvent(){
    return this.updateGame.asObservable();
  }



  // sendContinueClickEvent(){
  //   this.continue.next(null);
  // }

  // getContinueClickEvent(){
  //   return this.continue.asObservable();
  // }

  //    Socket Handling
  initSocket(playedGameId: string){
    this.wsService.connect(playedGameId);
    this.socket = this.wsService.getSocket();
    this.socket.onmessage = (event) => {
      this.broadcastSocketMessage(event.data);
    }
  }

  getSocket(){
    return this.socket;
  }

  closeSocket(){
    this.wsService.closeConnection();
  }

  broadcastSocketMessage(message: NotificationMessage){
    this.socketMessage.next(message);
  }

  getSocketMessage(){
    return this.socketMessage.asObservable();
  }

  parseNotificationMessage(data: string): NotificationMessage {
    const regex = /NotificationMessage\(notificationOption=(.*), name=(.*), number=(.*), bool=(.*)\)/;
    const matches = data.match(regex);
  
    if (matches) {
      const [, notificationOption, name, number, bool] = matches;
  

      return {
        notificationOption: NotificationEnum[notificationOption as keyof typeof NotificationEnum],
        name,
        number: parseInt(number, 10),
        bool: bool === 'true',
      };
    }
  
    return {
      notificationOption: 0,
      name: '',
      number: 0,
      bool: false,
    };
  }
  
}
