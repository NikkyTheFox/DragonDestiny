package pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * A structure containing a list of cards.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardList {

    /**
     * A list of cards.
     */
    private List<Card> cardList = new ArrayList<>();
}
