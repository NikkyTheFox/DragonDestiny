package pl.edu.pg.eti.dragondestiny.engine.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class GameListDTO {

    /**
     * A list of games.
     */
    private List<GameDTO> gameList;

}
