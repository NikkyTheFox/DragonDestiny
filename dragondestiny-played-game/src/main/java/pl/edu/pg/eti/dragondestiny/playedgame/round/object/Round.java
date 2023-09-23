package pl.edu.pg.eti.dragondestiny.playedgame.round.object;

import jakarta.persistence.Id;
import lombok.*;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent Rounds used in played game documents.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Round {

    /**
     * Identifier of a round.
     */
    @Id
    private Integer id;

    /**
     * Player that has option to make a move.
     */
    private Player activePlayer;

    /**
     * List of players.
     */
    private List<Player> playerList = new ArrayList<>();

}
