package pl.edu.pg.eti.game.engine.card.itemcard.dto;

import pl.edu.pg.eti.game.engine.card.card.dto.CardDTO;
import lombok.Data;

/**
 * ItemCardDTO extending CardDTO.
 * Adds elements specific to ItemCard.
 */

@Data
public class ItemCardDTO extends CardDTO {

    /**
     * Value of strength points ItemCard gives.
     */
    private Integer additionalStrength;

    /**
     * Value of health points ItemCard gives.
     */
    private Integer additionalHealth;

}
