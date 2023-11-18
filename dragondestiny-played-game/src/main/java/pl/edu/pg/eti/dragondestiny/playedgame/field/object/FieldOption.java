package pl.edu.pg.eti.dragondestiny.playedgame.field.object;

/**
 * Represent actions possible to take while standing on field.
 */
public enum FieldOption {
    TAKE_ONE_CARD(1, 0),
    TAKE_TWO_CARDS(2, 0),
    LOSE_ONE_ROUND(0, 1),
    LOSE_TWO_ROUNDS(0, 2),
    BRIDGE_FIELD(0, 0),
    BOSS_FIELD(0, 0),
    FIGHT_WITH_PLAYER(0, 0),
    FIGHT_WITH_ENEMY_ON_FIELD(0, 0);

    public final Integer numOfCardsToTake;
    public final Integer numOfTurnsToBlock;

    FieldOption(Integer numOfCardsToTake, Integer numOfTurnsToBlock) {
        this.numOfCardsToTake = numOfCardsToTake;
        this.numOfTurnsToBlock = numOfTurnsToBlock;
    }
}
