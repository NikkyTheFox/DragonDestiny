import {Field} from "../game-engine/field";

export interface PlayedGameCharacter {
  id: number;
  initialStrength: number;
  initialHealth: number;
  receivedStrength: number;
  receivedHealth: number;
  cardsStrength: number;
  cardsHealth: number
  field: Field | null;
}
