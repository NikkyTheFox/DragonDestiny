import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { PlayedGame } from '../../interfaces/played-game/played-game/played-game';
import { Player } from '../../interfaces/played-game/player/player';
import { ItemCard } from '../../interfaces/played-game/card/item-card/item-card';
import { PlayedGameCharacter } from '../../interfaces/played-game/character/character';
import { Card } from '../../interfaces/played-game/card/card/card';
import { PlayedBoard } from '../../interfaces/played-game/board/played-board';
import { Field } from '../../interfaces/game-engine/field/field';
import { Round } from '../../interfaces/played-game/round/round';
import { FightResult } from '../../interfaces/played-game/fight-result/fight-result';
import { PlayedGameList } from '../../interfaces/played-game/played-game/played-game-list';
import { FieldList } from '../../interfaces/played-game/field/field-list';
import { CardList } from '../../interfaces/played-game/card/card/card-list';
import { CharacterList } from '../../interfaces/played-game/character/character-list';
import { PlayerList } from '../../interfaces/played-game/player/player-list';
import { ItemCardList } from '../../interfaces/played-game/card/item-card/item-card-list';
import { EnemyCardList } from '../../interfaces/played-game/card/enemy-card/enemy-card-list';
import { FieldOptionList } from '../../interfaces/played-game/field/field-option-list';

@Injectable({
  providedIn: 'root'
})
export class PlayedGameService{

  constructor(private http: HttpClient){

  }

  // GAME----------------------------------------------------------

  getGame(playedGameId: string): Observable<PlayedGame>{
    return this.http.get<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}`);
  }

  getAllGames(): Observable<PlayedGameList>{
    return this.http.get<PlayedGameList>(`${environment.apiUrl}/playedgames`);
  }

  initializeGame(baseGameId: number): Observable<PlayedGame>{
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${baseGameId}`, null);
  }

  startGame(playedGameId: string): Observable<PlayedGame>{
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/start`, null);
  }

  deleteGame(playedGameId: string): Observable<string>{
    return this.http.delete<string>(`${environment.apiUrl}/playedgames/${playedGameId}`);
  }

  // ROUND ------------------------------------------------------------------

  getActiveRound(playedGameId: string): Observable<Round>{
    return this.http.get<Round>(`${environment.apiUrl}/playedgames/${playedGameId}/round`);
  }

  setNextRound(playedGameId: string): Observable<Round>{
    return this.http.put<Round>(`${environment.apiUrl}playedgames/${playedGameId}/round/next`, null);
  }

  // BOARD + FIELDS ----------------------------------------------------------

  getBoard(playedGameId: string): Observable<PlayedBoard>{
    return this.http.get<PlayedBoard>(`${environment.apiUrl}/playedgames/${playedGameId}/board`);
  }

  getFields(playedGameId: string): Observable<FieldList>{
    return this.http.get<FieldList>(`${environment.apiUrl}/playedgames/${playedGameId}/board/fields`);
  }

  getField(playedGameId: string, fieldId: number): Observable<Field>{
    return this.http.get<Field>(`${environment.apiUrl}/playedgames/${playedGameId}/board/fields/${fieldId}`);
  }

  // CARDS----------------------------------------------------------

  getCardsDeck(playedGameId: string): Observable<CardList>{
    return this.http.get<CardList>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/deck`);
  }

  getUsedCardsDeck(playedGameId: string): Observable<CardList>{
    return this.http.get<CardList>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/used`);
  }

  getCardFromDeck(playedGameId: string, cardId: number): Observable<Card>{
    return this.http.get<Card>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/deck/${cardId}`);
  }

  getCardFromUsedCardDeck(playedGameId: string, cardId: number): Observable<Card>{
    return this.http.get<Card>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/used/${cardId}`);
  }

  moveCardFromCardDeckToUsedCardDeck(playedGameId: string, cardId: number): Observable<PlayedGame>{
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/used/${cardId}`, null);
  }

  moveCardFromPlayedHandToUsedCardDeck(playedGameId: string, playerLogin: string, cardId: number): Observable<PlayedGame>{
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards/used/${cardId}`, null);
  }

  moveItemCardFromDeckToPlayerHand(playedGameId: string, cardId: number, playerLogin: string): Observable<PlayedGame>{
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards/hand/${cardId}`, null);
  }

  moveCardToPlayerTrophies(playedGameId: string, playerLogin: string, cardId: number): Observable<PlayedGame>{
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards/trophies/${cardId}`, null);
  }

  drawRandomCard(playedGameId: string): Observable<Card>{
    return this.http.get<Card>(`${environment.apiUrl}/playedgames/${playedGameId}/cards/deck/draw`);
  }

  // CHARACTERS -------------------------------------------------------

  getAllCharacters(playedGameId: string): Observable<CharacterList>{
    return this.http.get<CharacterList>(`${environment.apiUrl}/playedgames/${playedGameId}/characters`);
  }

  getCharacter(playedGameId: string, characterId: number): Observable<PlayedGameCharacter>{
    return this.http.get<PlayedGameCharacter>(`${environment.apiUrl}/playedgames/${playedGameId}/characters/${characterId}`);
  }

  getCharactersInUse(playedGameId: string): Observable<CharacterList>{
    return this.http.get<CharacterList>(`${environment.apiUrl}/playedgames/${playedGameId}/characters/used`);
  }

  getCharacterNotInUse(playedGameId: string): Observable<CharacterList>{
    return this.http.get<CharacterList>(`${environment.apiUrl}/playedgames/${playedGameId}/characters/free`);
  }

  // PLAYERS -------------------------------------------------------------

  getPlayers(playedGameId: string): Observable<PlayerList>{
    return this.http.get<PlayerList>(`${environment.apiUrl}/playedgames/${playedGameId}/players`);
  }

  getPlayer(playedGameId: string, playerLogin: string): Observable<Player>{
    return this.http.get<Player>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}`);
  }

  getPlayersOnField(playedGameId: string, fieldId: number): Observable<PlayerList>{
    return this.http.get<PlayerList>(`${environment.apiUrl}/playedgames/${playedGameId}/players/field/${fieldId}`);
  }

  getPlayersCharacter(playedGameId: string, playerLogin: string): Observable<PlayedGameCharacter>{
    return this.http.get<PlayedGameCharacter>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/character`);
  }

  getCardsFromPlayerHand(playedGameId: string, playerLogin: string): Observable<ItemCardList>{
    return this.http.get<ItemCardList>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards/hand`);
  }

  getHealthCardsFromPlayerHand(playedGameId: string, playerLogin: string): Observable<ItemCardList>{
    return this.http.get<ItemCardList>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards/hand/health`);
  }

  getStrengthCardsFromPlayerHand(playedGameId: string, playerLogin: string): Observable<ItemCardList>{
    return this.http.get<ItemCardList>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards/hand/strength`);
  }

  getCardFromPlayerHand(playedGameId: string, playerLogin: string, cardId: number): Observable<ItemCard>{
    return this.http.get<ItemCard>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards/hand/${cardId}`);
  }

  getTrophies(playedGameId: string, playerLogin: string): Observable<EnemyCardList>{
    return this.http.get<EnemyCardList>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/cards/trophies`);
  }

  addPlayerToGameByLogin(playedGameId: string, playerLogin: string): Observable<PlayedGame>{
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}`, null);
  }

  selectCharacter(playedGameId: string, playerLogin: string, characterId: number): Observable<PlayedGame>{
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/character/${characterId}`, null);
  }

  checkPossibleNewPositions(playedGameId: string, playerLogin: string, rollValue: number): Observable<FieldList>{
    return this.http.get<FieldList>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/field/move/${rollValue}/fields`);
  }

  changeFieldPositionOfCharacter(playedGameId: string, playerLogin: string, fieldId: number): Observable<PlayedGame>{
    return this.http.put<PlayedGame>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/field/${fieldId}`, null);
  }

  getPlayersPossibleActions(playedGameId: string, playerLogin: string): Observable<FieldOptionList>{
    return this.http.get<FieldOptionList>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/field/options`);
  }

  getPlayersToFightWith(playedGameId: string, playerLogin: string): Observable<PlayerList>{
    return this.http.get<PlayerList>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/field/options/players`);
  }

  getEnemiesToFightWith(playedGameId: string, playerLogin: string): Observable<EnemyCardList>{
    return this.http.get<EnemyCardList>(`${environment.apiUrl}/playedGames/${playedGameId}/players/${playerLogin}/field/options/enemies`);
  }

  blockTurnsOfPlayerByNumber(playedGameId: string, playerLogin: string, blockedNum: number): Observable<Player>{
    return this.http.put<Player>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/block/${blockedNum}`, null);
  }

  automaticallyBlockTurnsOfPlayer(playedGameId: string, playerLogin: string): Observable<Player>{
    return this.http.put<Player>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/block`, null);
  }

  // ACTIONS

  rollDice(playedGameId: string, playerLogin: string): Observable<number>{
    return this.http.get<number>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/roll`);
  }

  handleFightWithEnemyCard(playedGameId: string, playerLogin: string, playerRoll: number, enemyCardId: number, enemyRoll: number): Observable<FightResult>{
    return this.http.put<FightResult>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/fight/roll/${playerRoll}/enemy/${enemyCardId}/roll/${enemyRoll}`, null);
  }

  /*
  handleFightWithEnemyField(playedGameId: string, playerLogin: string, playerRoll: number, enemyRoll: number): Observable<FightResult>{
    return this.http.put<FightResult>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/roll/${playerRoll}/enemy/roll/${enemyRoll}`, null);
  }*/

  handleFightWithPlayer(playedGameId: string, playerLogin: string, playerRoll: number, enemyPlayerLogin: string): Observable<FightResult>{
    return this.http.put<FightResult>(`${environment.apiUrl}/playedgames/${playedGameId}/players/${playerLogin}/fight/roll/${playerRoll}/${enemyPlayerLogin}`, null);
  }

}
