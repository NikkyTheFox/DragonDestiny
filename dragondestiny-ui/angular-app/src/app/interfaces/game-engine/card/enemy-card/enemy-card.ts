import { Card } from '../card/card';

export interface EnemyCard extends Card{
  initialHealth: number;
  initialStrength: number;
}
