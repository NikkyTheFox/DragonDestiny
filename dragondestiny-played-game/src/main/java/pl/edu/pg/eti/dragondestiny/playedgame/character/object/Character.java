package pl.edu.pg.eti.dragondestiny.playedgame.character.object;

import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Represents characters that are used in played game documents.
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
     * Overall value of health of a character.
     */
    private Integer strength = 0;

    /**
     * Overall value of health of a character.
     */
    private Integer health = 0;

    /**
     * Position of the character on the board - on particular field.
     * Many characters can stand on the same field.
     * At beginning set to initial position of character.
     */
    private Field field;


    /**
     * Increases additional (received) strength points by val.
     *
     * @param value A number to increase strength with.
     */
    public void addStrength(Integer value) {

        setStrength(getStrength() + value);
    }

    /**
     * Decreases additional (received) strength points by val.
     *
     * @param value A number to reduce strength with.
     */
    public void reduceStrength(Integer value){

        setStrength(getStrength() - value);
    }

    /**
     * Increases additional (received) health points by val.
     *
     * @param value A number to increase health with.
     */
    public void addHealth(Integer value) {

        setHealth(getHealth() + value);
    }

    /**
     * Decreases additional (received) health points by specified number.
     *
     * @param value A number to reduce health with.
     */
    public void reduceHealth(Integer value) {

        setHealth(getHealth() - value);
    }

    /**
     * Adds strength and health points from card to character.
     *
     * @param card A card added to the equipment.
     */
    public void addCard(ItemCard card) {
        setHealth(getHealth() + card.getHealth());
        setStrength(getStrength() + card.getStrength());
    }

    /**
     * Removes strength and health points from card to character.
     *
     * @param card A card to be removed from the equipment.
     */
    public void removeCard(ItemCard card) {
        setHealth(getHealth() - card.getHealth());
        setStrength(getStrength() - card.getStrength());
    }
}
