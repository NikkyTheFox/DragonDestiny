package pl.edu.pg.eti.game.engine.card.enemycard.service;

import pl.edu.pg.eti.game.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.game.engine.card.enemycard.repository.EnemyCardRepository;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Enemy Card Service to communication with enemy cards' data saved in database.
 */

@Service
public class EnemyCardService {

    /**
     * JPA repository communicating with database.
     */
    private final EnemyCardRepository enemyCardRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param enemyCardRepository
     */
    @Autowired
    public EnemyCardService(EnemyCardRepository enemyCardRepository) {
        this.enemyCardRepository = enemyCardRepository;
    }

    /**
     * Returns all enemy cards found.
     *
     * @return list of enemy cards
     */
    public List<EnemyCard> findEnemyCards() {
        return enemyCardRepository.findAll();
    }

    /**
     * Returns all enemy cards in particular game.
     *
     * @param game - game to find cards from
     * @return list of enemy cards in game
     */
    public List<EnemyCard> findEnemyCards(Game game) {
        return enemyCardRepository.findAllByGames(game);
    }

}
