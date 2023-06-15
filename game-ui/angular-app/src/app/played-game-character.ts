export interface PlayedGameCharacter {
  id: number;
  initialStrength: number;
  initialHealth: number;
  additionalStrength?: number | null;
  additionalHealth?: number | null;
  positionField: number | null;
}
