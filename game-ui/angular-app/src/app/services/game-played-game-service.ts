import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import {Observable} from "rxjs";
import {PlayedGame} from "../interfaces/game-played-game/played-game";
import {PlayedGamePlayer} from "../interfaces/game-played-game/played-game-player";
import {PlayedGameItemCard} from "../interfaces/game-played-game/played-game-item-card";
import {PlayedGameCharacter} from "../interfaces/game-played-game/played-game-character";
import {PlayedGameCard} from "../interfaces/game-played-game/played-game-card";
import {PlayedGameEnemyCard} from "../interfaces/game-played-game/played-game-enemy-card";
import {PlayedGameBoard} from "../interfaces/game-played-game/played-game-board";
import {Field} from "../interfaces/game-engine/field";
import {GameFieldOption} from "../interfaces/game-played-game/game-field-option";


@Injectable({
  providedIn: 'root'
})
export class GamePlayedGameService {

  constructor(private http: HttpClient) {
  }

  // GAME----------------------------------------------------------

  getGame(playedGameId: string): Observable<PlayedGame> {
    return this.http.get<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}`);
  }

  getGames(): Observable<PlayedGame[]> {
    return this.http.get<PlayedGame[]>(`${environment.apiUrl}/playedgames`);
  }

  initializeGame(baseGameId: number): Observable<PlayedGame> {
    return this.http.post<PlayedGame>(`${environment.apiUrl}/playedgames/${baseGameId}`, null);
  }

  deleteGame(playedGameId: string):Observable<string> {
    return this.http.delete<string>(`${environment.apiUrl}/playedgames/${playedGameId}`);
  }

  // BOARD + FIELD ----------------------------------------------------------

  getBoard(playedGameId: string): Observable<PlayedGameBoard> {
    return this.http.get<PlayedGameBoard>(`${environment.apiUrl}/playedgames/${playedGameId}/board`);
  }

  getFields(playedGameId: string): Observable<Field[]> {
    return this.http.get<Field[]>(`${environment.apiUrl}/playedgames/${playedGameId}/board/fields`);
  }

  getField(playedGameId: string, fieldId: number): Observable<Field> {
    return this.http.get<Field>(`${environment.apiUrl}/playedgames/${playedGameId}/board/fields/${fieldId}`);
  }

  // CARDS----------------------------------------------------------

  getCardsDeck(playedGameId: string): Observable<PlayedGameCard[]> {
    return this.http.get<PlayedGameCard[]>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/deck`);
  }

  getCardsUsed(playedGameId: string): Observable<PlayedGameCard[]> {
    return this.http.get<PlayedGameCard[]>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/used`);
  }

  getCardFromDeck(playedGameId: string, cardId: number): Observable<PlayedGameCard> {
    return this.http.get<PlayedGameCard>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/deck/${cardId}`);
  }

  getCardFromUsed(playedGameId: string, cardId: number): Observable<PlayedGameCard> {
    return this.http.get<PlayedGameCard>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/used/${cardId}`)
  }

  moveCardFromDeckToUsed(playedGameId: string, cardId: number):Observable<PlayedGame> {
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/cardToUsed/${cardId}`, null);
  }

  moveCardFromHandToUsed(playedGameId: string, playerId: string, cardId: number): Observable<PlayedGame> {
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/cardToUsed/${cardId}`, null);
  }

  moveCardFromDeckToPlayerHand(playedGameId: string, cardId: number, playerId: string) {
    return this.http.put(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/cardToPlayer/${cardId}`, null);
  }

  moveTrophyToPlayer(playedGameId: string, playerId: string, cardId: number): Observable<PlayedGame> {
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/cardToTrophies/${cardId}`, null);
  }

  // CHARACTERS -------------------------------------------------------

  getGameCharacters(playedGameId: string): Observable<PlayedGameCharacter[]> {
    return this.http.get<PlayedGameCharacter[]>(`${environment.apiUrl}/playedgames/${playedGameId}/characters`)
  }

  getGameCharacter(playedGameId: string, characterId: number): Observable<PlayedGameCharacter> {
    return this.http.get<PlayedGameCharacter>(`${environment.apiUrl}/playedgames/${playedGameId}/characters/${characterId}`)
  }

  // PLAYERS -------------------------------------------------------------

  getPlayers(playedGameId: string): Observable<PlayedGamePlayer[]> {
    return this.http.get<PlayedGamePlayer[]>(`${environment.apiUrl}/playedgames/${playedGameId}/players`);
  }

  getPlayer(playedGameId: string, playerId: string): Observable<PlayedGamePlayer> {
    return this.http.get<PlayedGamePlayer>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}`);
  }

  getPlayerCharacter(playedGameId: string, playerId: string): Observable<PlayedGameCharacter> {
    return this.http.get<PlayedGameCharacter>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/character`);
  }

  getPlayersCards(playedGameId: string, playerId: string): Observable<PlayedGameItemCard[]> {
    return this.http.get<PlayedGameItemCard[]>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/cards`);
  }

  getPlayersCard(playedGameId: string, playerId: string, cardId: number): Observable<PlayedGameItemCard> {
    return this.http.get<PlayedGameItemCard>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/cards/${cardId}`);
  }

  getPlayerTrophies(playedGameId: string, playerId: string): Observable<PlayedGameEnemyCard[]> {
    return this.http.get<PlayedGameEnemyCard[]>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/trophies`);
  }

  addPlayerToGameByLogin(playedGameId: string, playerId: string): Observable<PlayedGame> {
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/addPlayer/${playerId}`, null);
  }

  selectCharacter(playedGameId: string, playerId: string, characterId: number): Observable<PlayedGame> {
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/character/${characterId}`, null);
  }

  changeFieldPositionOfCharacter(playedGameId: string, playerId: string, fieldId: number): Observable<GameFieldOption[]> {
    return this.http.put<GameFieldOption[]>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/field/${fieldId}`, null);
  }

  // GAME MECHANICS

  drawRandomCard(playedGameId: string, playerId: string):Observable<PlayedGameCard>{
    return this.http.put<PlayedGameCard>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/drawCard`, null);
  }

  handleItemCard(playedGameId: string, playerId: string, cardId: number): Observable<Boolean>{
    return this.http.put<Boolean>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/handleItemCard/${cardId}`, null);
  }

      // FIGHT

  handleFightWithEnemyCard(playedGameId: string, playerId: string, playerRoll: number, enemyCardId: number, enemyRoll: number):Observable<Boolean>{
    return this.http.put<Boolean>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/roll/${playerRoll}/enemy/${enemyCardId}/roll/${enemyRoll}`, null);
  }

  handleFightWithEnemyField(playedGameId: string, playerId: string, playerRoll: number, enemyRoll: number):Observable<Boolean>{
    return this.http.put<Boolean>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/roll/${playerRoll}/enemy/roll/${enemyRoll}`, null);
  }

  handleFightWithOtherPlayer(playedGameId: string, playerId: string, playerRoll: number):Observable<Boolean>{
    return this.http.put<Boolean>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/roll/${playerRoll}`, null);
  }

}
