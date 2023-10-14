import { PlayedGameCharacter } from '../character/character';
import { Card } from '../card/card/card';
import { PlayedBoard } from '../board/played-board';
import { Player } from '../player/player';
import { Round } from '../round/round';

export interface PlayedGame{
  id: string;
  isStarted: boolean;
  activeRound: Round;
  players: Player[];
  board: PlayedBoard;
  cardDeck: Card[];
  usedCardDeck: Card[];
  charactersInGame: PlayedGameCharacter[];
}
