package pl.edu.pg.eti.game.playedgame.card.itemcard.entity;

import pl.edu.pg.eti.game.playedgame.card.entity.Card;
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
    private Integer additionalStrength = 0;

    /**
     * Additional health points for character that owns this card.
     */
    private Integer additionalHealth = 0;

    /**
     * Number of health points already used up from the item.
     * If additionalHealth - usedAdditionalHealth = 0, card is used.
     */
    private Integer usedAdditionalHealth = 0;

    /**
     * Method to return number of health points left on item.
     *
     * @return
     */
    public Integer getHealth() {
        return additionalHealth + usedAdditionalHealth;
    }

    /**
     * Method to remove health points from the item.
     *
     * @param val
     */
    public void decreaseHealth(Integer val) {
        usedAdditionalHealth = usedAdditionalHealth - val;
    }

}
