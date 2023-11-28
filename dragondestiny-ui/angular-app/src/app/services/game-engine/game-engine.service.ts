import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Game } from '../../interfaces/game-engine/game/game';
import { Board } from '../../interfaces/game-engine/board/board';
import { Field } from '../../interfaces/game-engine/field/field';
import { EnemyCard } from '../../interfaces/game-engine/card/enemy-card/enemy-card';
import { Character } from '../../interfaces/game-engine/character/character';
import { Card } from '../../interfaces/game-engine/card/card/card';
import { BoardList } from '../../interfaces/game-engine/board/board-list';
import { CardList } from '../../interfaces/game-engine/card/card/card-list';
import { EnemyCardList } from '../../interfaces/game-engine/card/enemy-card/enemy-card-list';
import { ItemCardList } from '../../interfaces/game-engine/card/item-card/item-card-list';
import { CharacterList } from '../../interfaces/game-engine/character/character-list';
import { FieldList } from '../../interfaces/game-engine/field/field-list';
import { GameList } from '../../interfaces/game-engine/game/game-list';

@Injectable({
  providedIn: 'root'
})
export class GameEngineService{

  constructor(private http: HttpClient){

  }

  // BOARD CONTROLLER

  getBoards(): Observable<BoardList>{
    return this.http.get<BoardList>(`${environment.api}/boards`);
  }

  getBoard(boardId: number): Observable<Board>{
    return this.http.get<Board>(`${environment.api}/boards/${boardId}`);
  }

  // CARD CONTROLLER

  getCards(): Observable<CardList>{
    return this.http.get<CardList>(`${environment.api}/cards`);
  }

  getCard(cardId: number): Observable<Card>{
    return this.http.get<Card>(`${environment.api}/cards/${cardId}`);
  }

  getEnemyCards(): Observable<EnemyCardList>{
    return this.http.get<EnemyCardList>(`${environment.api}/cards/enemy`)
  }

  getItemCards(): Observable<ItemCardList>{
    return this.http.get<ItemCardList>(`${environment.api}/cards/item`)
  }

  // GAME-CARD CONTROLLER

  getGameCards(gameId: number): Observable<CardList>{
    return this.http.get<CardList>(`${environment.api}/games/${gameId}/cards`);
  }

  getGameCard(gameId: number, cardId: number): Observable<Card>{
    return this.http.get<Card>(`${environment.api}/games/${gameId}/cards/${cardId}`);
  }

  getGameEnemyCards(gameId: number): Observable<EnemyCardList>{
    return this.http.get<EnemyCardList>(`${environment.api}/games/${gameId}/cards/enemy`)
  }

  getGameItemCards(gameId: number): Observable<ItemCardList>{
    return this.http.get<ItemCardList>(`${environment.api}/games/${gameId}/cards/item`)
  }

  // CHARACTER CONTROLLER

  getCharacters(): Observable<CharacterList>{
    return this.http.get<CharacterList>(`${environment.api}/characters`)
  }

  getCharacter(characterId: number): Observable<Character>{
    return this.http.get<Character>(`${environment.api}/characters/${characterId}`);
  }

  // GAME-CHARACTER CONTROLLER

  getGameCharacters(gameId: number): Observable<CharacterList>{
    return this.http.get<CharacterList>(`${environment.api}/games/${gameId}/characters`)
  }

  getGameCharacter(gameId: number, characterId: number): Observable<Character>{
    return this.http.get<Character>(`${environment.api}/games/${gameId}/characters/${characterId}`)
  }

  // BOARD-FIELD CONTROLLER

  getBoardFields(boardId: number): Observable<FieldList>{
    return this.http.get<FieldList>(`${environment.api}/boards/${boardId}/fields`);
  }

  getBoardField(boardId: number, fieldId: number): Observable<Field>{
    return this.http.get<Field>(`${environment.api}/boards/${boardId}/fields/${fieldId}`);
  }

  getBoardFieldEnemy(boardId: number, fieldId: number): Observable<EnemyCard>{
    return this.http.get<EnemyCard>(`${environment.api}/boards/${boardId}/fields/${fieldId}/enemy`);
  }

  // FIELD CONTROLLER

  getFields(): Observable<FieldList>{
    return this.http.get<FieldList>(`${environment.api}/fields`);
  }

  getField(fieldId: number): Observable<Field>{
    return this.http.get<Field>(`${environment.api}/fields/${fieldId}`);
  }

  getFieldEnemy(fieldId: number): Observable<EnemyCard>{
    return this.http.get<EnemyCard>(`${environment.api}/fields/${fieldId}/enemy`);
  }

  // GAME-FIELD CONTROLLER

  getGameFields(gameId: number): Observable<FieldList>{
    return this.http.get<FieldList>(`${environment.api}/games/${gameId}/board/fields`);
  }

  getGameField(gameId: number, fieldId: number): Observable<Field>{
    return this.http.get<Field>(`${environment.api}/games/${gameId}/board/fields/${fieldId}`);
  }

  getGameFieldEnemy(gameId: number, fieldId: number): Observable<EnemyCard>{
    return this.http.get<EnemyCard>(`${environment.api}/games/${gameId}/board/fields/${fieldId}/enemy`);
  }

  // GAME CONTROLLER

  getGames(): Observable<GameList>{
    return this.http.get<GameList>(`${environment.api}/games`);
  }

  getGame(gameId: number): Observable<Game>{
    return this.http.get<Game>(`${environment.api}/games/${gameId}`)
  }

  getGameBoard(gameId: number): Observable<Board>{
    return this.http.get<Board>(`${environment.api}/games/${gameId}/board`)
  }
}
