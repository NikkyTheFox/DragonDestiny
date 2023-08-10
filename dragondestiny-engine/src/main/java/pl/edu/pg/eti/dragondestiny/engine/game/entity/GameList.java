package pl.edu.pg.eti.dragondestiny.engine.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A structure containing list of games.
 */
@Data
@AllArgsConstructor
public class GameList {

    /**
     * A list of games.
     */
    private List<Game> gameList;

}
