import {Field} from "../game-engine/field";

export interface PlayedGameCharacter {
  id: number;
  initialStrength: number;
  initialHealth: number;
  additionalStrength?: number | null;
  additionalHealth?: number | null;
  field: Field | null;
  positionField: Field | null;
}
