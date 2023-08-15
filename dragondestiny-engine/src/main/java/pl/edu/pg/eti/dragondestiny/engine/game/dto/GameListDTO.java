package pl.edu.pg.eti.dragondestiny.engine.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object of List of Game DTOs.
 */
@Data
@AllArgsConstructor
public class GameListDTO {

    /**
     * A list of games.
     */
    private List<GameDTO> gameList;

}
