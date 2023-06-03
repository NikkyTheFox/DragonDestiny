package pl.edu.pg.eti.game.engine.card.enemycard.repository;

import pl.edu.pg.eti.game.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPARepository interface with domain type EnemyCard and integer as id
 */

@Repository
public interface EnemyCardRepository extends JpaRepository<EnemyCard, Integer> {

    /**
     * Method to retrieve all enemy cards by game - all enemy cards in particular game.
     *
     * @param game - game to find all cards from
     * @return list of enemy cards in game
     */
    List<EnemyCard> findAllByGames(Game game);

}
