import {Card} from "./card";

export interface ItemCard extends Card{
  additionalStrength: number;
  additionalHealth: number;
}
