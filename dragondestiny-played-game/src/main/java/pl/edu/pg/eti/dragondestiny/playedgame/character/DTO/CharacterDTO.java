package pl.edu.pg.eti.dragondestiny.playedgame.character.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldDTO;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO {

    /**
     * Identifier of character.
     */
    private Integer id;

    /**
     * Initial strength of the character - minimum.
     */
    private Integer initialStrength;

    /**
     * Initial health of the character.
     */
    private Integer initialHealth;

    /**
     * Overall sum of strength points of a character. Current value.
     */
    private Integer strength = 0;

    /**
     * Overall sum of health points of a character. Current value.
     */
    private Integer health = 0;

    /**
     * Position of the character on the board - on particular field.
     * Many characters can stand on the same field.
     * At beginning set to initial position of character.
     */
    private FieldDTO field;
}
