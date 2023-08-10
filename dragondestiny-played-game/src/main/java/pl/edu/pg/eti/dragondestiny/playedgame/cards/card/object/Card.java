package pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object;

import lombok.*;

/**
 * Corresponds to cards in played game.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Card {

    /**
     * Identifier of the card.
     */
    private Integer id;

    /**
     * Type of card.
     */
    private CardType cardType;
}
