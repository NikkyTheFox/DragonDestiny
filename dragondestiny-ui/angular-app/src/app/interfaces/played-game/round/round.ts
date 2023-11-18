import { Player } from '../player/player';
import { RoundState } from './round-state';

export interface Round{
  id: number;
  activePlayer: Player;
  roundState: RoundState
  playerList: Player[];
}
