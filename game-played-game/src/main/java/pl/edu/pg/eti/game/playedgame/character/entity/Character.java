package pl.edu.pg.eti.game.playedgame.character.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Represents characters cards that are used in PLAYED GAME.
 */

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor
public class Character implements Serializable {

    /**
     * Identifier of character.
     */
    @Id
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
     * Additional strength points received during the game - NOT FROM CARDS!.
     * Additional points from trophies.
     * Total strength of character is sum of initialStrength + additionalStrength + cards.
     */
    private Integer additionalStrength = 0;

    /**
     * Additional health points received during the game - NOT FROM CARDS!.
     * Can be negative.
     * Total number health points is sum of initialHealth + additionalHealth + cards.
     * If sum <= 0, character is dead and user that plays that character ends the game.
     */
    private Integer additionalHealth = 0;

    /**
     * Initial position field of character.
     */
    private Field field;

    /**
     * Position of the character on the board - on particular field.
     * Many characters can stand on the same field.
     */
    private Field positionField;

    /**
     * Character Manager.
     */
    @JsonIgnore
    private CharacterManager characterManager;
    public void setCharacterManager(CharacterManager characterManager) {
        this.characterManager = characterManager;
    }

}
