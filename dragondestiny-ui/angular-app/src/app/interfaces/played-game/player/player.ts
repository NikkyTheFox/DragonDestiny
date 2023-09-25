import {PlayedGameCharacter} from '../character/character';
import {ItemCard} from '../card/item-card/item-card';
import {EnemyCard} from '../card/enemy-card/enemy-card';

export interface Player {
  login: string;
  fightRoll: number;
  blockedTurns: number;
  character: PlayedGameCharacter;
  cardsOnHand: ItemCard[];
  trophies: EnemyCard[];
}