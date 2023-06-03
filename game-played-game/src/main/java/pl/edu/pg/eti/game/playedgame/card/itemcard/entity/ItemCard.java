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
    private Integer additionalStrength;

    /**
     * Additional health points for character that owns this card.
     */
    private Integer additionalHealth;

}
