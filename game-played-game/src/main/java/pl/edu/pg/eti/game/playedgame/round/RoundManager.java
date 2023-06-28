package pl.edu.pg.eti.game.playedgame.round;

import pl.edu.pg.eti.game.playedgame.player.entity.Player;

import java.util.List;

public class RoundManager extends Round {

    public Round setActivePlayer(Round round, Player player) {
        round.setActivePlayer(player);
        return round;
    }

    public Round setPlayers(Round round, List<Player> playerList) {
        round.setPlayers(playerList);
        return round;
    }

    public Round setId(Round round, Integer id) {
        round.setId(id);
        return round;
    }

}
