export enum FieldOptionEnum{
  TAKE_ONE_CARD = 'TAKE_ONE_CARD',
  TAKE_TWO_CARDS = 'TAKE_TWO_CARDS',
  LOSE_ONE_ROUND = 'LOSE_ONE_ROUND',
  LOSE_TWO_ROUNDS = 'LOSE_TWO_ROUNDS',
  BRIDGE_FIELD = 'BRIDGE_FIELD',  // WHILE ON BOSS => Go to bridge field & then fight it | OTHERWISE attack bridge guardian
  BOSS_FIELD = 'BOSS_FIELD',      // WHILE ON BRIDGE => Go to boss field & Then fight it | OTHERWISE attack boss
  FIGHT_WITH_PLAYER = 'FIGHT_WITH_PLAYER',
  FIGHT_WITH_ENEMY_ON_FIELD = 'FIGHT_WITH_ENEMY_ON_FIELD'
}
