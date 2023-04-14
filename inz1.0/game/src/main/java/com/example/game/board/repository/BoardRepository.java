package com.example.game.board.repository;

import com.example.game.board.entity.Board;
import com.example.game.field.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPARepository interface with domain type Board and integer as id
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

//    @Override
//    default Board save(Board boardRequest) {
//        Board board = new Board();
//        board.setId(boardRequest.getId());
//        board.setYSize(9999);
//        board.setXSize(9999);
//        for (Field f : boardRequest.getFieldsInBoard())
//        {
//            board.addFieldsInBoard(f);
//        }
//
//        return board;
//    }

}
