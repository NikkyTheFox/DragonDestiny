import { EnemyCard } from '../card/enemy-card/enemy-card';
import { ItemCard } from '../card/item-card/item-card';
import { Field } from '../field/field';
import { FieldOption } from '../field/field-option';
import { FieldOptionList } from '../field/field-option-list';
import { Player } from '../player/player';
import { RoundState } from './round-state';

export interface Round{
  id: number;
  activePlayer: Player;
  playerList: Player[];
  roundState: RoundState;
  roundStatesOrder: RoundState[];
  playerMoveRoll: number;
  fieldListToMove: Field[];
  fieldOptionList: FieldOptionList;
  fieldOptionChosen: FieldOption;
  playerNumberOfCardsTaken: number;
  itemCardToTake: ItemCard;
  itemCardStolen: ItemCard;
  enemyFought: EnemyCard;
  enemyPlayerFought: Player;
  playerFightRoll: number;
  enemyFightRoll: number;
}
