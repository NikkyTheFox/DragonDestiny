package com.example.game_engine.field.service;

import com.example.game_engine.board.entity.Board;
import com.example.game_engine.field.entity.Field;
import com.example.game_engine.field.repository.FieldRepository;
import jakarta.transaction.Transactional;
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
     * @param fieldRepository
     */
    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**
     * Returns field by ID. If no field found, throws exception.
     * @param id
     * @return field found
     */
    public Field findById(Integer id) {
        Optional<Field> field = fieldRepository.findById(id);
        if (field.isPresent()) {
            return field.get();
        } else throw new RuntimeException("No field found");
    }

    /**
     * Returns all fields found.
     * @return list of fields
     */
    public List<Field> findAll() {
        return fieldRepository.findAll();
    }

    /**
     * Returns all fields on particular board.
     * @param board - board to get all fields from
     * @return list of fields on board
     */
    public List<Field> findAllByBoard(Board board) {
        return fieldRepository.findAllByBoard(board);
    }

    /**
     * Returns field of ID fieldId found on board of boardId.
     * @param board - board to get field from
     * @param fieldId - identifier of card
     * @return field of ID fieldId if such exists on board of ID boardId
     */
    public Field findFieldByBoardAndId(Board board, Integer fieldId) {
        return fieldRepository.findFieldByBoardAndId(board, fieldId);
    }

}
