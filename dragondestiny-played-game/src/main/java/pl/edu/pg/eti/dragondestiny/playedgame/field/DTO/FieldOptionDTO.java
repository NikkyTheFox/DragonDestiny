package pl.edu.pg.eti.dragondestiny.playedgame.field.DTO;

import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;

public class FieldOptionDTO {
    public final FieldOptionEnum fieldOptionEnum;

    public final Integer numOfCardsToTake;

    public final Integer numOfTurnsToBlock;

    public Player enemyPlayer;

    public FieldOptionDTO(FieldOptionEnum fieldOptionEnum, Integer numOfCardsToTake, Integer numOfTurnsToBlock, Player enemyPlayer) {
        this.fieldOptionEnum = fieldOptionEnum;
        this.numOfCardsToTake = numOfCardsToTake;
        this.numOfTurnsToBlock = numOfTurnsToBlock;
        this.enemyPlayer = enemyPlayer;
    }

}
