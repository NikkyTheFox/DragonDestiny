package com.example.game_engine.field.repository;

import com.example.game_engine.board.entity.Board;
import com.example.game_engine.field.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPARepository interface with domain type Field and integer as id
 */
@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {

    /**
     * Method to retrieve all fields by board - all fields of particular board.
     * @param board - board to get all fields from
     * @return list of fields on the board
     */
    List<Field> findAllByBoard(Board board);

    /**
     * Method to retrieve field by board and fieldId - one field from all on particular board.
     * @param board - board to get field from
     * @param fieldId - identifier of card
     * @return field on board of boardId ID
     */
    Field findFieldByBoardAndId(Board board, Integer fieldId);

}
