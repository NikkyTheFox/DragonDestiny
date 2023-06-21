package pl.edu.pg.eti.game.engine.character.dto;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import pl.edu.pg.eti.game.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.game.engine.field.entity.Field;

/**
 * DTO allows to hide implementation from the client.
 */

@Data
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
