import {PlayedGameCharacter} from "./played-game-character";
import {PlayedGameCard} from "./played-game-card";
import {PlayedGameBoard} from "./played-game-board";

export interface PlayedGame {
  id: string;
  players: any[]; // Assuming players have their own interface
  board: PlayedGameBoard;
  cardDeck: PlayedGameCard[];
  usedCardDeck: PlayedGameCard[];
  charactersInGame: PlayedGameCharacter[];
}
