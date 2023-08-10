import {PlayedGameCard} from "./played-game-card";

export interface PlayedGameItemCard extends PlayedGameCard{
  strength: number;
  health: number;
  usedHealth: number;
}
