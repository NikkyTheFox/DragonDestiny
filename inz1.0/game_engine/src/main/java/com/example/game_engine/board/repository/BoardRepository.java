package com.example.game_engine.board.repository;

import com.example.game_engine.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPARepository interface with domain type Board and integer as id
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
