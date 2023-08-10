package pl.edu.pg.eti.dragondestiny.engine.card.itemcard.repository;


import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPARepository interface with domain type EnemyCard and integer as id
 */
@Repository
public interface ItemCardRepository extends JpaRepository<ItemCard, Integer> {

    /**
     * Method to retrieve all item cards by game - all item cards in particular game.
     *
     * @param game A game to find all cards from.
     * @return A list of item cards.
     */
    List<ItemCard> findAllByGames(Game game);

}
