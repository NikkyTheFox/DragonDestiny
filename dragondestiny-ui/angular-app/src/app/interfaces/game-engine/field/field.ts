import { EnemyCard } from '../card/enemy-card/enemy-card';
import { FieldType } from './field-type';

export interface Field{
  id: number;
  type: FieldType;
  xposition: number;
  yposition: number;
  enemy: EnemyCard | null;
}
