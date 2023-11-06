package pl.edu.pg.eti.dragondestiny.playedgame.round.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.RoundState;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundDTO {

    /**
     * Identifier of a round.
     */
    private Integer id;

    /**
     * Player that has option to make a move.
     */
    private PlayerDTO activePlayer;

    private RoundState roundState;

    /**
     * List of players.
     */
    private List<PlayerDTO> playerList;

}
