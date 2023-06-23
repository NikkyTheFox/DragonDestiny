import {PlayedGameCharacter} from "./played-game-character";
import {PlayedGameCard} from "./played-game-card";
import {PlayedGameBoard} from "./played-game-board";
import {PlayedGamePlayer} from "./played-game-player";

export interface PlayedGame {
  id: string;
  players: PlayedGamePlayer[]; // Assuming players have their own interface
  board: PlayedGameBoard;
  cardDeck: PlayedGameCard[];
  usedCardDeck: PlayedGameCard[];
  charactersInGame: PlayedGameCharacter[];
}
