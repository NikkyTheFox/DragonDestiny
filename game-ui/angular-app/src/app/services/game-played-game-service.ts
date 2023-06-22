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

  moveCardFromHandToUsed(playedGameId: string, playerLogin: string, cardId: number): Observable<PlayedGame> {
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cardToUsed/${cardId}`, null);
  }

  moveCardFromDeckToPlayerHand(playedGameId: string, cardId: number, playerLogin: string) {
    return this.http.put(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cardToPlayer/${cardId}`, null);
  }

  moveTrophyToPlayer(playedGameId: string, playerLogin: string, cardId: number): Observable<PlayedGame> {
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cardToTrophies/${cardId}`, null);
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

  getPlayer(playedGameId: string, playerLogin: string): Observable<PlayedGamePlayer> {
    return this.http.get<PlayedGamePlayer>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}`);
  }

  getPlayerCharacter(playedGameId: string, playerLogin: string): Observable<PlayedGameCharacter> {
    return this.http.get<PlayedGameCharacter>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/character`);
  }

  getPlayersCards(playedGameId: string, playerLogin: string): Observable<PlayedGameItemCard[]> {
    return this.http.get<PlayedGameItemCard[]>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards`);
  }

  getPlayersCard(playedGameId: string, playerLogin: string, cardId: number): Observable<PlayedGameItemCard> {
    return this.http.get<PlayedGameItemCard>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards/${cardId}`);
  }

  getPlayerTrophies(playedGameId: string, playerLogin: string): Observable<PlayedGameEnemyCard[]> {
    return this.http.get<PlayedGameEnemyCard[]>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/trophies`);
  }

  addPlayerToGameByLogin(playedGameId: string, playerLogin: string): Observable<PlayedGame> {
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/addPlayer/${playerLogin}`, null);
  }

  selectCharacter(playedGameId: string, playerLogin: string, characterId: number): Observable<PlayedGame> {
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/character/${characterId}`, null);
  }

  changeFieldPositionOfCharacter(playedGameId: string, playerLogin: string, fieldId: number): Observable<GameFieldOption[]> {
    return this.http.put<GameFieldOption[]>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/field/${fieldId}`, null);
  }

  // GAME MECHANICS

  drawRandomCard(playedGameId: string, playerLogin: string):Observable<PlayedGameCard>{
    return this.http.put<PlayedGameCard>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/drawCard`, null);
  }

  handleItemCard(playedGameId: string, playerLogin: string, cardId: number): Observable<Boolean>{
    return this.http.put<Boolean>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/handleItemCard/${cardId}`, null);
  }

      // FIGHT

  handleFightWithEnemyCard(playedGameId: string, playerLogin: string, playerRoll: number, enemyCardId: number, enemyRoll: number):Observable<Boolean>{
    return this.http.put<Boolean>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/roll/${playerRoll}/enemy/${enemyCardId}/roll/${enemyRoll}`, null);
  }

  handleFightWithEnemyField(playedGameId: string, playerLogin: string, playerRoll: number, enemyRoll: number):Observable<Boolean>{
    return this.http.put<Boolean>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/roll/${playerRoll}/enemy/roll/${enemyRoll}`, null);
  }

  handleFightWithOtherPlayer(playedGameId: string, playerLogin: string, playerRoll: number):Observable<Boolean>{
    return this.http.put<Boolean>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/roll/${playerRoll}`, null);
  }

}
