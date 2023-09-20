package pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object;

import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import lombok.*;

/**
 * Corresponds to item card in game.
 * Can be in deck, used deck or in hand of a player.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ItemCard extends Card {

    /**
     * Additional strength points for character that owns this card.
     */
    private Integer strength = 0;

    /**
     * Additional health points for character that owns this card.
     */
    private Integer health = 0;

    /**
     * Check if card's health stats has been used out.
     *
     * @return True if no health remaining.
     */
    public Boolean isUsed(){
        return getHealth() <= 0;
    }

    /**
     * Adds health points from the item.
     *
     * @param value A number to increase health with.
     */
    public void addHealth(Integer value) {
        setHealth(getHealth() + value);
    }

    /**
     * Removes health points from the item.
     *
     * @param value A number to reduce health with.
     */
    public void reduceHealth(Integer value) {
        setHealth(getHealth() - value);
    }
}
