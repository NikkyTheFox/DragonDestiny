import {Card} from "../game-engine/card";

export interface PlayedGameAbstractCard extends Card{
  stat1: number;
  stat2: number;
}
