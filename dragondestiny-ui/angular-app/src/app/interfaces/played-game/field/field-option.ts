import { Player } from "../player/player";
import { FieldOptionEnum } from "./field-option-enum";

export interface FieldOption{
    fieldOptionEnum: FieldOptionEnum;
    numOfCardsToTake: number;
    numOfTurnsToBlock: number;
    enemyPlayer: Player;
}