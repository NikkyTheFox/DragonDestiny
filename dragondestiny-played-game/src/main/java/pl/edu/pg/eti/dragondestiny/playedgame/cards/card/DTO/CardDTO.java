package pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO;

import lombok.Data;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardType;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class CardDTO {

    /**
     * Identifier of the card.
     */
    private Integer id;

    /**
     * Type of the card.
     */
    private CardType cardType;
}
