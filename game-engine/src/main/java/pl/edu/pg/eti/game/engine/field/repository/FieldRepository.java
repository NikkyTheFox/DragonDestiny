package pl.edu.pg.eti.game.engine.field.repository;

import pl.edu.pg.eti.game.engine.board.entity.Board;
import pl.edu.pg.eti.game.engine.field.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPARepository interface with domain type Field and integer as id
 */

@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {

    /**
     * Method to retrieve all fields by board - all fields of particular board.
     *
     * @param board - board to get all fields from
     * @return list of fields on the board
     */
    List<Field> findAllByBoard(Board board);

    /**
     * Method to retrieve field by board and fieldId - one field from all on particular board.
     *
     * @param board - board to get field from
     * @param fieldId - identifier of card
     * @return field on board of boardId ID
     */
    Optional<Field> findByBoardAndId(Board board, Integer fieldId);

}
