package pl.edu.pg.eti.game.playedgame.character.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import java.util.List;

/**
 * Represents list of entities received from game engine.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CharacterList {
    private List<Character> characterList;

}
