import {PlayedGameCharacter} from "./played-game-character";
import {PlayedGameItemCard} from "./played-game-item-card";
import {PlayedGameEnemyCard} from "./played-game-enemy-card";

export interface PlayedGamePlayer {
  login: string;
  character: PlayedGameCharacter;
  cardsOnHand: PlayedGameItemCard[];
  trophies: PlayedGameEnemyCard[];
}
