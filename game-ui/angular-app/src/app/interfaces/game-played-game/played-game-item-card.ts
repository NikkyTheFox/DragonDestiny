import {PlayedGameCard} from "./played-game-card";

export interface PlayedGameItemCard extends PlayedGameCard{
  additionalStrength: number;
  additionalHealth: number;
}
