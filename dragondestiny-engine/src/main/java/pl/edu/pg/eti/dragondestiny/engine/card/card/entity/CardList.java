package pl.edu.pg.eti.dragondestiny.engine.card.card.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A structure containing list of cards.
 */
@Data
@AllArgsConstructor
public class CardList {

    /**
     * A list of cards.
     */
    private List<Card> cardList;
}
