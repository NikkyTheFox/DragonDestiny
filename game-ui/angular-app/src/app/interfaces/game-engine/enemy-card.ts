import {Card} from "./card";

export interface EnemyCard extends Card{
  initialHealth: number;
  initialStrength: number;
}
