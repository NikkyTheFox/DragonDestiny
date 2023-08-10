package pl.edu.pg.eti.dragondestiny.playedgame.fightresult.DTO;

import lombok.Data;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class FightResultDTO {

    /**
     * Value informing whether the attacking participant won the fight.
     */
    private Boolean attackerWon = false;

    /**
     * Value informing whether the enemy card (defender) was defeated and now can be collected as a trophy.
     */
    private Boolean enemyKilled = false;

    /**
     * Value informing whether enemy player (defender) was defeated to the death.
     */
    private Boolean playerDead = false;

    /**
     * Value informing whether the whole game was won after the fight.
     */
    private Boolean gameWon = false;

    /**
     * Value informing whether a player (attacker) has an option to steal a card from enemy (defending player) after winning a fight.
     */
    private Boolean chooseCardFromEnemyPlayer = false;

    /**
     * Value storing nickname of a player who has won the fight.
     */
    private String wonPlayer;

    /**
     * Value storing nickname of a player who has lost the fight.
     */
    private String lostPlayer;
}
