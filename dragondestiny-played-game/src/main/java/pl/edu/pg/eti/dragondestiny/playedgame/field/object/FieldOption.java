package pl.edu.pg.eti.dragondestiny.playedgame.field.object;

import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldOptionDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldOptionEnum;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;


/**
 * Represent actions possible to take while standing on field.
 */
public enum FieldOption {
    TAKE_ONE_CARD(FieldOptionEnum.TAKE_ONE_CARD, 1, 0, null),
    TAKE_TWO_CARDS(FieldOptionEnum.TAKE_TWO_CARDS, 2, 0, null),
    LOSE_ONE_ROUND(FieldOptionEnum.LOSE_ONE_ROUND, 0, 1, null),
    LOSE_TWO_ROUNDS(FieldOptionEnum.LOSE_TWO_ROUNDS, 0, 2, null),
    BRIDGE_FIELD(FieldOptionEnum.BRIDGE_FIELD, 0, 0, null),
    BOSS_FIELD(FieldOptionEnum.BOSS_FIELD, 0, 0, null),
    FIGHT_WITH_PLAYER(FieldOptionEnum.FIGHT_WITH_PLAYER, 0, 0, null),
    FIGHT_WITH_ENEMY_ON_FIELD(FieldOptionEnum.FIGHT_WITH_ENEMY_ON_FIELD, 0, 0, null);

    public final Integer numOfCardsToTake;
    public final Integer numOfTurnsToBlock;
    public FieldOptionEnum fieldOptionEnum;
    public Player enemyPlayer;

    FieldOption(FieldOptionEnum fieldOptionEnum, Integer numOfCardsToTake, Integer numOfTurnsToBlock, Player enemyPlayer) {
        this.fieldOptionEnum = fieldOptionEnum;
        this.numOfCardsToTake = numOfCardsToTake;
        this.numOfTurnsToBlock = numOfTurnsToBlock;
        this.enemyPlayer = enemyPlayer;
    }

    public FieldOptionDTO toDTO() {
        return new FieldOptionDTO(fieldOptionEnum, numOfCardsToTake, numOfTurnsToBlock, enemyPlayer);
    }

}
