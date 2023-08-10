package pl.edu.pg.eti.dragondestiny.playedgame.round.DTO;

import lombok.Data;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerDTO;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class RoundDTO {

    /**
     * Identifier of a round.
     */
    private Integer id;

    /**
     * Player that has option to make a move.
     */
    private PlayerDTO activePlayer;

    /**
     * List of players.
     */
    private List<PlayerDTO> playerList;

}
