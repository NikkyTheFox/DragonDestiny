import {PlayedGame} from "../game-played-game/played-game";

export interface GameUserShort{
  login: string;
  name: string;
  playedGames: PlayedGame[];
}
