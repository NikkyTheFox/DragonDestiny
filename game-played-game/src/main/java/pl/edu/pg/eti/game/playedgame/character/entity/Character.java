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
    private Integer receivedStrength = 0;

    /**
     * Additional health points received during the game - NOT FROM CARDS!.
     * Can be negative.
     * Total number health points is sum of initialHealth + additionalHealth + cards.
     * If sum <= 0, character is dead and user that plays that character ends the game.
     */
    private Integer receivedHealth = 0;

    /**
     * Additional strength points received from cards.
     * Sum of points from cards.
     */
    private Integer cardsStrength = 0;

    /**
     * Additional health points received from cards.
     * Sum of points from cards.
     */
    private Integer cardsHealth = 0;

    /**
     * Position of the character on the board - on particular field.
     * Many characters can stand on the same field.
     * At beginning set to initial position of character.
     */
    private Field field;

    /**
     * Character Manager.
     */
    @JsonIgnore
    private CharacterManager characterManager;
    public void setCharacterManager(CharacterManager characterManager) {
        this.characterManager = characterManager;
    }

}
