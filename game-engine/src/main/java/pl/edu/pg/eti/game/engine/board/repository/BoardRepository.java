package pl.edu.pg.eti.game.engine.board.repository;

import pl.edu.pg.eti.game.engine.board.entity.Board;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPARepository interface with domain type Board and integer as id
 */

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    Optional<Board> findByGames(Game game);

}
