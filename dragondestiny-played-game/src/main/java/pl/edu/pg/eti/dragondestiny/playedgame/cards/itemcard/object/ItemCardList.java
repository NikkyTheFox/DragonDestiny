package pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Structure containing a list of item cards.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCardList {
    /**
     * A list of item cards.
     */
    private List<ItemCard> itemCardList = new ArrayList<>();

}
