package pl.edu.pg.eti.dragondestiny.engine.field.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;

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
     * @param board A board to get all fields from.
     * @return A list of fields on the board.
     */
    List<Field> findAllByBoard(Board board);

    /**
     * Retrieves field by board and fieldId - one field from provided board.
     *
     * @param board   A board to get field from.
     * @param fieldId An identifier of a field to be retrieved.
     * @return A field on board.
     */
    Optional<Field> findByBoardAndId(Board board, Integer fieldId);

}
