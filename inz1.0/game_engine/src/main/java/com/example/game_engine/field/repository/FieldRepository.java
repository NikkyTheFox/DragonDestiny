package com.example.game_engine.field.repository;

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
     * Method to retrieve all fields by boardId - all fields of particular board.
     * @param boardId - identifier of board
     * @return list of fields on the board
     */
    List<Field> findFieldsByBoardId(Integer boardId);

    /**
     * Method to retrieve field by boardId and fieldId - one field from all on particular board.
     * @param boardId - identifier of game
     * @param fieldId - identifier of card
     * @return field on board of boardId ID
     */
    Field findFieldByBoardIdAndId(Integer boardId, Integer fieldId);

}
