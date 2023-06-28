package pl.edu.pg.eti.game.engine.card.itemcard.service;

import pl.edu.pg.eti.game.engine.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.engine.card.itemcard.repository.ItemCardRepository;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCardService {

    /**
     * JPA repository communicating with database.
     */
    private final ItemCardRepository itemCardRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param itemCardRepository
     */
    @Autowired
    public ItemCardService(ItemCardRepository itemCardRepository) {
        this.itemCardRepository = itemCardRepository;
    }

    /**
     * Returns all item cards found.
     *
     * @return list of item cards
     */
    public List<ItemCard> findItemCards() {
        return itemCardRepository.findAll();
    }

    /**
     * Returns all item cards in particular game.
     *
     * @param game - game to find cards from
     * @return list of item cards in game
     */
    public List<ItemCard> findItemCards(Game game) {
        return itemCardRepository.findAllByGames(game);
    }

}
