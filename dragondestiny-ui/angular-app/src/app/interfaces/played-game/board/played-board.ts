import { Field } from '../../game-engine/field/field';

export interface PlayedBoard{
  id: number;
  fieldsOnBoard: Field[];
}
