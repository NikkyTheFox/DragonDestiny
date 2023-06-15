import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import {Observable, of, switchMap} from "rxjs";
import {PlayedGame} from "./played-game";
import {Board} from "./board";
import {Field} from "./field";
import {AbstractCard} from "./abstract-card";
import {EnemyCard} from "./enemy-card";
import {ItemCard} from "./item-card";
import {observableToBeFn} from "rxjs/internal/testing/TestScheduler";
import {GameCharacter} from "./game-character";
import {PlayedGameBoard} from "./played-game-board";


@Injectable({
  providedIn: 'root'
})
export class GameServiceService {

  constructor(private http: HttpClient) { }

  getPlayedGames(){
    return this.http.get(`${environment.apiUrl}/playedgames`);
  }

  getPlayedGame(gameId: string): Observable<PlayedGame>{
    return this.http.get<PlayedGame>(`${environment.apiUrl}/playedgames/${gameId}`)
  }

  getPlayedGamesBoard(gameId: string): Observable<Board>{
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

  getPlayedGameCharacters(gameId: string):Observable<GameCharacter[]>{
    return this.http.get<GameCharacter[]>(`${environment.apiUrl}/playedgames/${gameId}/characters`)
  }

  getPlayedGameCharacter(gameId: string, characterId: number):Observable<GameCharacter>{
    return this.http.get<GameCharacter>(`${environment.apiUrl}/playedgames/${gameId}/characters/${characterId}`)
  }

  getPlayedCardsDeck(gameId: string):Observable<AbstractCard[]>{
    return this.http.get<AbstractCard[]>(`${environment.apiUrl}/playedgames/${gameId}/cards/deck`)
  }

  getPlayedEnemyCards(gameId: string):Observable<EnemyCard[]>{
    return this.http.get<EnemyCard[]>(`${environment.apiUrl}/games/${gameId}/cards/enemy`)
  }

  getPlayedItemCards(gameId: string):Observable<ItemCard[]>{
    return this.http.get<ItemCard[]>(`${environment.apiUrl}/games/${gameId}/cards/item`)
  }


}
