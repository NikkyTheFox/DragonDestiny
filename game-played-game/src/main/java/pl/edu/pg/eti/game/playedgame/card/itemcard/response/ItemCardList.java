package pl.edu.pg.eti.game.playedgame.card.itemcard.response;

import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents list of entities received from game engine.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCardList {
    private List<ItemCard> itemCardList;

}
