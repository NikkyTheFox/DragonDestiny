import { PlayedGame } from "./played-game/played-game/played-game";
import { Player } from "./played-game/player/player";

export interface GamePlayerRequest{
  game: PlayedGame,
  player: Player,
}
