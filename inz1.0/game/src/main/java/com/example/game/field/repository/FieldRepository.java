package com.example.game.field.repository;

import com.example.game.field.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {
    /**
     * Allows to return list of all fields in particular board.
     * @param id
     * @return
     */
    public List<Field> findFieldsByBoardId(Integer id);

}
