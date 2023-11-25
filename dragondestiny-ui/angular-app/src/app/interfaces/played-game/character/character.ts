import { Field } from '../../played-game/field/field';

export interface PlayedGameCharacter{
  id: number;
  initialStrength: number;
  initialHealth: number;
  strength: number;
  health: number;
  field: Field | null;
}
