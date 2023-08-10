package pl.edu.pg.eti.dragondestiny.engine.character.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A structure containing list of characters.
 */
@Data
@AllArgsConstructor
public class CharacterList {

    /**
     * A list of characters.
     */
    private List<Character> characterList;

}
