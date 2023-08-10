package pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardDTO;

import java.util.List;

/**
 * A structure containing list of item cards.
 */
@Data
@AllArgsConstructor
public class ItemCardList {

    /**
     * A list of item cards.
     */
    private List<ItemCard> itemCardList;

}
