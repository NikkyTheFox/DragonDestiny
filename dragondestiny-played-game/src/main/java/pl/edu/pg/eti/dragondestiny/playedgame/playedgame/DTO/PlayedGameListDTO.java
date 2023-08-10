package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class PlayedGameListDTO {

    /**
     * A list of played games.
     */
    private List<PlayedGameDTO> playedGameDTOList;
}
