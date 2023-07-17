import {PlayedGamePlayer} from "./played-game-player";

export interface PlayedGameRound{
  id: number;
  activePlayer: PlayedGamePlayer;
  players: PlayedGamePlayer[];
}
