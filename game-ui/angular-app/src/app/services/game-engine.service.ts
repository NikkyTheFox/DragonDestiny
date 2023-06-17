import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import {Observable} from "rxjs";
import {Game} from "../interfaces/game-engine/game";
import {Board} from "../interfaces/game-engine/board";
import {Field} from "../interfaces/game-engine/field";
import {EnemyCard} from "../interfaces/game-engine/enemy-card";
import {ItemCard} from "../interfaces/game-engine/item-card";
import {Character} from "../interfaces/game-engine/game-character";
import {Card} from "../interfaces/game-engine/card";

@Injectable({
  providedIn: 'root'
})
export class GameEngineService {

  constructor(private http: HttpClient) { }

  // BOARD CONTROLLER

  getBoards():Observable<Board[]>{
    return this.http.get<Board[]>(`${environment.apiUrl}/boards`);
  }

  getBoard(boardId: number):Observable<Board>{
    return this.http.get<Board>(`${environment.apiUrl}/boards/${boardId}`);
  }

  // CARD CONTROLLER

  getCards():Observable<Card[]>{
    return this.http.get<Card[]>(`${environment.apiUrl}/cards`);
  }

  getCard(cardId: number):Observable<Card>{
    return this.http.get<Card>(`${environment.apiUrl}/cards/${cardId}`);
  }

  getEnemyCards():Observable<EnemyCard[]>{
    return this.http.get<EnemyCard[]>(`${environment.apiUrl}/cards/enemy`)
  }

  getItemCards():Observable<ItemCard[]>{
    return this.http.get<ItemCard[]>(`${environment.apiUrl}/cards/item`)
  }

  // GAME-CARD CONTROLLER

  getGameCards(gameId: number):Observable<Card[]>{
    return this.http.get<Card[]>(`${environment.apiUrl}/games/${gameId}/cards`);
  }

  getGameCard(gameId: number, cardId: number):Observable<Card>{
    return this.http.get<Card>(`${environment.apiUrl}/games/${gameId}/cards/${cardId}`);
  }

  getGameEnemyCards(gameId: number):Observable<EnemyCard[]>{
    return this.http.get<EnemyCard[]>(`${environment.apiUrl}/games/${gameId}/cards/enemy`)
  }

  getGameItemCards(gameId: number):Observable<ItemCard[]>{
    return this.http.get<ItemCard[]>(`${environment.apiUrl}/games/${gameId}/cards/item`)
  }

  // CHARACTER CONTROLLER

  getCharacters():Observable<Character[]>{
    return this.http.get<Character[]>(`${environment.apiUrl}/characters`)
  }

  getCharacter(characterId: number):Observable<Character>{
    return this.http.get<Character>(`${environment.apiUrl}/characters/${characterId}`);
  }

  // GAME-CHARACTER CONTROLLER

  getGameCharacters(gameId: number):Observable<Character[]>{
    return this.http.get<Character[]>(`${environment.apiUrl}/games/${gameId}/characters`)
  }

  getGameCharacter(gameId: number, characterId: number):Observable<Character>{
    return this.http.get<Character>(`${environment.apiUrl}/games/${gameId}/characters/${characterId}`)
  }

  // FIELD CONTROLLER

  getFields():Observable<Field[]>{
    return this.http.get<Field[]>(`${environment.apiUrl}/fields`);
  }

  getField(fieldId: number):Observable<Field>{
    return this.http.get<Field>(`${environment.apiUrl}/fields/${fieldId}`);
  }

  // BOARD-FIELD CONTROLLER

  getBoardFields(boardId: number):Observable<Field[]>{
    return this.http.get<Field[]>(`${environment.apiUrl}/boards/${boardId}/fields`);
  }

  getBoardField(boardId: number, fieldId: number):Observable<Field>{
    return this.http.get<Field>(`${environment.apiUrl}/boards/${boardId}/fields/${fieldId}`);
  }

  // GAME-FIELD CONTROLLER

  getGameFields(gameId: number):Observable<Field[]>{
    return this.http.get<Field[]>(`${environment.apiUrl}/games/${gameId}/boards/fields`);
  }

  getGameField(gameId: number, fieldId: number):Observable<Field>{
    return this.http.get<Field>(`${environment.apiUrl}/games/${gameId}/boards/fields/${fieldId}`);
  }

  // GAME CONTROLLER

  getGames():Observable<Game>{
    return this.http.get<Game>(`${environment.apiUrl}/games`);
  }

  getGame(gameId: number):Observable<Game>{
    return this.http.get<Game>(`${environment.apiUrl}/games/${gameId}`)
  }

  getGameBoard(gameId: number):Observable<Board>{
    return this.http.get<Board>(`${environment.apiUrl}/games/${gameId}/board`)
  }
}
