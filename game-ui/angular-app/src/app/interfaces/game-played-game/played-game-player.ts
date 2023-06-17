import {PlayedGameCharacter} from "./played-game-character";
import {PlayedGameItemCard} from "./played-game-item-card";

export interface PlayedGamePlayer {
  login: string;
  character: PlayedGameCharacter;
  cardsOnHand: PlayedGameItemCard[];
}
