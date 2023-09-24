package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Structure containing a list of cards.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PlayedGameList {

    /**
     * A list of played games.
     */
    private List<PlayedGame> playedGameList = new ArrayList<>();

}
