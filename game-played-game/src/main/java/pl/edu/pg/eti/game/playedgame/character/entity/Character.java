package pl.edu.pg.eti.game.playedgame.character.entity;

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
@Setter
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
     * Position of the character on the board - on particular field.
     * Many characters can stand on the same field.
     */
    private Field positionField;

    /**
     * Method to increase additional (received) strength points by val.
     *
     * @param val
     */
    public void increaseStrength(Integer val) {
        this.additionalStrength += val;
    }

    /**
     * Method to increase additional (received) health points by val.
     *
     * @param val
     */
    public void increaseHealth(Integer val) {
        this.additionalHealth += val;
    }

}
