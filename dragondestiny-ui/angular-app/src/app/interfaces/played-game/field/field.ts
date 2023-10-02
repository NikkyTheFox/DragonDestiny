import { FieldType } from './field-type';
import { EnemyCard } from '../card/enemy-card/enemy-card';

export interface Field{
  id: number;
  type: FieldType;
  xPosition: number;
  yPosition: number;
  enemy: EnemyCard | null;
}
