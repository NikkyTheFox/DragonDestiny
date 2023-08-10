import {PlayedGameCharacter} from "./played-game-character";
import {PlayedGameCard} from "./played-game-card";
import {PlayedGameBoard} from "./played-game-board";
import {PlayedGamePlayer} from "./played-game-player";
import {PlayedGameRound} from "./played-game-round";

export interface PlayedGame {
  id: string;
  isStarted: boolean;
  activeRound: PlayedGameRound;
  players: PlayedGamePlayer[];
  board: PlayedGameBoard;
  cardDeck: PlayedGameCard[];
  usedCardDeck: PlayedGameCard[];
  charactersInGame: PlayedGameCharacter[];
}
