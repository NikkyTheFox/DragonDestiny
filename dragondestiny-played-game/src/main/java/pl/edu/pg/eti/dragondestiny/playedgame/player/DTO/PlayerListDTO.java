package pl.edu.pg.eti.dragondestiny.playedgame.player.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class PlayerListDTO {

    /**
     * A list of players.
     */
    private List<PlayerDTO> playerDTOList;
}
