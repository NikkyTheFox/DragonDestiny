package pl.edu.pg.eti.game.playedgame.round;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Round {

    @Id
    private Integer id;

    private Player activePlayer;

    private List<Player> players = new ArrayList<>();

    /**
     * Round Manager.
     */
    private RoundManager roundManager;

    public void setRoundManager(RoundManager roundManager) {
        this.roundManager = roundManager;
    }



}
