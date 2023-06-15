export interface PlayedGameCard {
  id: number;
  cardType: string;
  initialHealth?: number;
  additionalHealth?: number | null;
  initialStrength: number;
  additionalStrength?: number | null;
}
