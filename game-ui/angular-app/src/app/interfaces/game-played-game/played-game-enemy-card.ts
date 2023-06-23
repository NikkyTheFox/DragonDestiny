import {PlayedGameCard} from "./played-game-card";

export interface PlayedGameEnemyCard extends PlayedGameCard{
  initialHealth: number;
  additionalHealth: number;
  initialStrength: number;
}
