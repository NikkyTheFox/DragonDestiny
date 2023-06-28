package pl.edu.pg.eti.game.engine.field.service;

import pl.edu.pg.eti.game.engine.board.entity.Board;
import pl.edu.pg.eti.game.engine.field.entity.Field;
import pl.edu.pg.eti.game.engine.field.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Field Service to communication with fields' data saved in database.
 */

@Service
public class FieldService {

    /**
     * JPA repository communicating with database.
     */
    private final FieldRepository fieldRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param fieldRepository
     */
    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**
     * Returns field by ID.
     *
     * @param id
     * @return field found
     */
    public Optional<Field> findField(Integer id) {
        return fieldRepository.findById(id);
    }

    /**
     * Returns all fields found.
     *
     * @return list of fields
     */
    public List<Field> findFields() {
        return fieldRepository.findAll();
    }

    /**
     * Returns all fields on particular board.
     *
     * @param board - board to get all fields from
     * @return list of fields on board
     */
    public List<Field> findFields(Board board) {
        return fieldRepository.findAllByBoard(board);
    }

    /**
     * Returns field of ID fieldId found on board of boardId.
     *
     * @param board - board to get field from
     * @param fieldId - identifier of card
     * @return field of ID fieldId if such exists on board of ID boardId
     */
    public Optional<Field> findField(Board board, Integer fieldId) {
        return fieldRepository.findByBoardAndId(board, fieldId);
    }

}
