package pl.edu.pg.eti.game.playedgame.card.itemcard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import lombok.*;

/**
 * Corresponds to item card in game.
 * Can be in deck, used deck or in hand of a player.
 */

@Getter
@Setter(AccessLevel.PROTECTED)
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
     * Number of health points already used up from the item.
     * If additionalHealth - usedAdditionalHealth = 0, card is used.
     */
    private Integer usedHealth = 0;

    /**
     * Item Card Manager;
     */
    @JsonIgnore
    private ItemCardManager cardManager;

    public void setCardManager(ItemCardManager cardManager) {
        this.cardManager = cardManager;
    }

}
