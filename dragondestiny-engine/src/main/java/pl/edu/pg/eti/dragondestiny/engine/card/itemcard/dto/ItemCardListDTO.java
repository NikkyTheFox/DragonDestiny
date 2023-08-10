package pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class ItemCardListDTO {

    /**
     * A list of item cards.
     */
    private List<ItemCardDTO> itemCardList;

}
