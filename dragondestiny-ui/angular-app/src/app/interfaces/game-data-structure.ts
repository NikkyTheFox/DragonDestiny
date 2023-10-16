import { PlayedGame } from "./played-game/played-game/played-game";
import { Player } from "./played-game/player/player";

export interface GameDataStructure {
  game?: PlayedGame;
  player?: Player;
  bossFieldId?: number,
  bridgeFieldId?: number
}
