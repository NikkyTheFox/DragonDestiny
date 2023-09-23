package pl.edu.pg.eti.dragondestiny.user.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A structure containing list of games.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameList {

    /**
     * A list of games.
     */
    private List<Game> gameList;

}
