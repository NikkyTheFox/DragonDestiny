import {EnemyCard} from "./enemy-card";

export interface Field{
  id: number;
  type: string;
  xposition: number;
  yposition: number;
  enemy: EnemyCard
}
