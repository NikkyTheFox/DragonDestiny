import { PlayedGame } from '../../played-game/played-game/played-game';

export interface User{
  login: string;
  name: string;
  playedGames: PlayedGame[];
}
