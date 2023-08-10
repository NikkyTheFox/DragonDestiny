package pl.edu.pg.eti.dragondestiny.engine.board.repository;

import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPARepository interface with domain type Board and integer as id
 */

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    /**
     * Retrieves board by specified game.
     *
     * @param game A game which board is to be retrieved.
     * @return Retrieved board.
     */
    Optional<Board> findByGames(Game game);

}
