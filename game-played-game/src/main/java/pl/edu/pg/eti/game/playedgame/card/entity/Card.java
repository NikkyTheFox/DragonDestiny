package pl.edu.pg.eti.game.playedgame.card.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

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
    @Id
    private Integer id;

    /**
     * Type of card.
     */
    private CardType cardType;

}
