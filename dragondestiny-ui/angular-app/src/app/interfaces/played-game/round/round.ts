import { Player } from '../player/player';

export interface Round{
  id: number;
  activePlayer: Player;
  players: Player[];
}
