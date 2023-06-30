package pl.edu.pg.eti.game.playedgame.game.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FightResult {

    private Boolean attackerWon = false;
    private Boolean enemyKilled = false;
    private Boolean playerDead = false;
    private Boolean gameWon = false;
    private Boolean chooseCardFromEnemyPlayer = false;
    private String wonPlayer;
    private String lostPlayer;

}
