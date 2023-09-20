package pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;

/**
 * Data Transfer Object of Item Card instance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
