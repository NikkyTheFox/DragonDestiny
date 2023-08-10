package pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardDTO;

/**
 * DTO allows to hide implementation from the client.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ItemCardDTO extends CardDTO {

    /**
     * Additional strength points for character that owns this card.
     */
    private Integer strength = 0;

    /**
     * Additional health points for character that owns this card.
     */
    private Integer health = 0;
}
