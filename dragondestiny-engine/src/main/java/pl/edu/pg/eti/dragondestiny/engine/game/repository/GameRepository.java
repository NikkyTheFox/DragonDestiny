package pl.edu.pg.eti.dragondestiny.engine.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;

/**
 * JPARepository interface with domain type Game and integer as id
 */

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

}
