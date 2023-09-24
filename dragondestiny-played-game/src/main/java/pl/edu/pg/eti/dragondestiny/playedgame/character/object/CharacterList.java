package pl.edu.pg.eti.dragondestiny.playedgame.character.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Structure containing a list of characters.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CharacterList {

    /**
     * A list of characters.
     */
    private List<Character> characterList = new ArrayList<>();

}
