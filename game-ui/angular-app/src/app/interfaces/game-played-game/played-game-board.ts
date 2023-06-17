import {Field} from "../game-engine/field";

export interface PlayedGameBoard {
  id: number;
  fieldsOnBoard: Field[];
}
