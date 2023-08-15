package pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto;

import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;
import lombok.Data;

/**
 * Data Transfer Object of Item Card instance.
 */
@Data
public class ItemCardDTO extends CardDTO {

    /**
     * Value of strength points ItemCard gives.
     */
    private Integer strength;

    /**
     * Value of health points ItemCard gives.
     */
    private Integer health;

}
