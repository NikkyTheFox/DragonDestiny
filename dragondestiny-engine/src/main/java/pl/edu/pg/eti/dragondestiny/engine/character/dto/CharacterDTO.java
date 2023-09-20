package pl.edu.pg.eti.dragondestiny.engine.character.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldDTO;

/**
 * Data Transfer Object of Character instance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDTO {

    /**
     * Unique identifier of character.
     */
    private Integer id;
    /**
     * Name of the character.
     */
    private String name;

    /**
     * Profession / race of character
     */
    private String profession;

    /**
     * Story, description of character.
     */
    private String story;

    /**
     * Value of initial strength points of character.
     */
    private Integer initialStrength;

    /**
     * Value of initial health points of character.
     */
    private Integer initialHealth;

    /**
     * Initial position field on board.
     */
    private FieldDTO field;

}
