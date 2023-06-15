import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import {Observable} from "rxjs";
import {Game} from "./game";
import {Board} from "./board";
import {Field} from "./field";
import {AbstractCard} from "./abstract-card";
import {EnemyCard} from "./enemy-card";
import {ItemCard} from "./item-card";
import {observableToBeFn} from "rxjs/internal/testing/TestScheduler";
import {GameCharacter} from "./game-character";

@Injectable({
  providedIn: 'root'
})
export class GameServiceService {

  constructor(private http: HttpClient) { }

  getGames(){
    return this.http.get(`${environment.apiUrl}/games`);
  }

  getGame(id: number): Observable<Game>{
    return this.http.get<Game>(`${environment.apiUrl}/games/${id}`)
  }

  getGamesBoard(id: number): Observable<Board>{
    return this.http.get<Board>(`${environment.apiUrl}/games/${id}/board`)
  }

  getBoardFields(id: number):Observable<Field[]>{
    return this.http.get<Field[]>(`${environment.apiUrl}/boards/${id}/fields`)
  }

  getGameCharacters(id: number):Observable<GameCharacter[]>{
    return this.http.get<GameCharacter[]>(`${environment.apiUrl}/games/${id}/characters`)
  }

  getGameCharacter(gameId: number, characterId: number):Observable<GameCharacter>{
    return this.http.get<GameCharacter>(`${environment.apiUrl}/games/${gameId}/characters/${characterId}`)
  }

  getCards(id:number):Observable<AbstractCard[]>{
    return this.http.get<AbstractCard[]>(`${environment.apiUrl}/games/${id}/cards`)
  }

  getEnemyCards(id:number):Observable<EnemyCard[]>{
    return this.http.get<EnemyCard[]>(`${environment.apiUrl}/games/${id}/cards/enemy`)
  }

  getItemCards(id:number):Observable<ItemCard[]>{
    return this.http.get<ItemCard[]>(`${environment.apiUrl}/games/${id}/cards/item`)
  }


}
