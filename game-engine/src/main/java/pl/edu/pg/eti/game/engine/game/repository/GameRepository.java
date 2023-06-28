package pl.edu.pg.eti.game.engine.game.repository;

import pl.edu.pg.eti.game.engine.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPARepository interface with domain type Game and integer as id
 */

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

}
