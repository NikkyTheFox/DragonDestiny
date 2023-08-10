export interface PlayedGameFightResult{
  attackerWon: boolean;
  enemyKilled: boolean;
  playerDead: boolean;
  gameWon: boolean;
  chooseCardFromEnemyPlayer: boolean;
  wonPlayer: string;
  lostPlayer: string;
}
