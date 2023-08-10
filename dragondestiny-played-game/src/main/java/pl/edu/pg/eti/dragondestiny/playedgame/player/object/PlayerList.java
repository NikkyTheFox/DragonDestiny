package pl.edu.pg.eti.dragondestiny.playedgame.player.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Structure containing a list of cards.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerList {

    /**
     * A list of player.
     */
    private List<Player> playerList;

}
