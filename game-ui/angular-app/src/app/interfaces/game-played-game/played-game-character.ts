import {Field} from "../game-engine/field";

export interface PlayedGameCharacter {
  id: number;
  initialStrength: number;
  initialHealth: number;
  additionalStrength?: number | null;
  additionalHealth?: number | null;
  positionField: Field | null;
}
