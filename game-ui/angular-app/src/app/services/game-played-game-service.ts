import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import {Observable} from "rxjs";
import {PlayedGame} from "../interfaces/game-played-game/played-game";
import {PlayedGamePlayer} from "../interfaces/game-played-game/played-game-player";
import {PlayedGameItemCard} from "../interfaces/game-played-game/played-game-item-card";
import {PlayedGameCharacter} from "../interfaces/game-played-game/played-game-character";
import {PlayedGameCard} from "../interfaces/game-played-game/played-game-card";


@Injectable({
  providedIn: 'root'
})
export class GamePlayedGameService {

  constructor(private http: HttpClient) { }

  // GAME----------------------------------------------------------

  getGames(){
    return this.http.get(`${environment.apiUrl}/playedgames`);
  }

  getGame(playedGameId: string): Observable<PlayedGame>{
    return this.http.get<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}`);
  }

  initializeGame(baseGameId: number){
    return this.http.post(`${environment.apiUrl}/playedgames/${baseGameId}`, null);
  }

  createGame(){
    return this.http.post(`${environment.apiUrl}/playedgames}`, null);
  }

  // CARDS----------------------------------------------------------

  getCardsDeck(playedGameId: string):Observable<PlayedGameCard[]>{
    return this.http.get<PlayedGameCard[]>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/deck`);
  }

  getCardsUsed(playedGameId: string):Observable<PlayedGameCard[]>{
    return this.http.get<PlayedGameCard[]>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/used`);
  }

  getCardFromDeck(playedGameId: string, cardId: number):Observable<PlayedGameCard>{
    return this.http.get<PlayedGameCard>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/deck/${cardId}`);
  }

  getCardFromUsed(playedGameId: string, cardId: number):Observable<PlayedGameCard>{
    return this.http.get<PlayedGameCard>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/used/${cardId}`)
  }

  moveCardFromDeckToUsed(playedGameId: string, cardId: number){
    return this.http.put(`${environment.apiUrl}/playedgames/${playedGameId}/cardToUsed/${cardId}`, null);
  }

  moveCardFromDeckToPlayerHand(playedGameId: string, cardId: number, playerId: string){
    return this.http.put(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/cardToPlayer/${cardId}`, null);
  }

  // CHARACTERS -------------------------------------------------------

  getGameCharacters(playedGameId: string):Observable<PlayedGameCharacter[]>{
    return this.http.get<PlayedGameCharacter[]>(`${environment.apiUrl}/playedgames/${playedGameId}/characters`)
  }

  getGameCharacter(playedGameId: string, characterId: number):Observable<PlayedGameCharacter>{
    return this.http.get<PlayedGameCharacter>(`${environment.apiUrl}/playedgames/${playedGameId}/characters/${characterId}`)
  }

  // PLAYERS -------------------------------------------------------------

  getPlayers(playedGameId: string):Observable<PlayedGamePlayer[]>{
    return this.http.get<PlayedGamePlayer[]>(`${environment.apiUrl}/playedgames/${playedGameId}/players`);
  }

  getPlayer(playedGameId: string, playerId: string):Observable<PlayedGamePlayer>{
    return this.http.get<PlayedGamePlayer>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}`);
  }

  getPlayersCards(playedGameId: string, playerId: string):Observable<PlayedGameItemCard[]>{
    return this.http.get<PlayedGameItemCard[]>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/cards`);
  }

  getPlayersCard(playedGameId: string, playerId: string, cardId: number):Observable<PlayedGameItemCard>{
    return this.http.get<PlayedGameItemCard>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/cards/${cardId}`);
  }

  addPlayerToGameByLogin(playedGameId: string, playerId: string){
    return this.http.put(`${environment.apiUrl}/playergames/${playedGameId}/addPlayer/${playerId}`, null);
  }

  selectCharacter(playedGameId: string, playerId: string, characterId: number){
    return this.http.put(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/character/${characterId}`, null);
  }

  changeFieldPositionOfCharacter(playedGameId: string, playerId: string, characterId: number, fieldId: number){
    return this.http.put(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerId}/character/${characterId}/field/${fieldId}`, null);
  }

  // GAME ENGINE INFO -------------------------------------------------------------
/*
  getBoardFromGameEngine(gameId: string): Observable<Board>{
    const game: Observable<PlayedGame> = this.http.get<PlayedGame>(`${environment.apiUrl}/playedgames/${gameId}`);
    return game.pipe(
      switchMap((playedGame: PlayedGame) => {
        const boardId = playedGame.board.id;
        return this.http.get<Board>(`${environment.apiUrl}/games/${boardId}/board`);
      })
    )
  }

  getPlayedBoardFields(gameId: string):Observable<Field[]>{
    //return this.http.get<Field[]>(`${environment.apiUrl}/boards/${id}/fields`)
    const game: Observable<PlayedGame> = this.http.get<PlayedGame>(`${environment.apiUrl}/playedgames/${gameId}`);
    return game.pipe(
      switchMap((playedGame: PlayedGame) => {
        return of(playedGame.board.fieldsOnBoard); // 'of' used to create an expected Observable to return
      })
    )
  }

  getPlayedEnemyCards(gameId: string):Observable<EnemyCard[]>{
    return this.http.get<EnemyCard[]>(`${environment.apiUrl}/games/${gameId}/cards/enemy`)
  }

  getPlayedItemCards(gameId: string):Observable<ItemCard[]>{
    return this.http.get<ItemCard[]>(`${environment.apiUrl}/games/${gameId}/cards/item`)
  }

*/
}
