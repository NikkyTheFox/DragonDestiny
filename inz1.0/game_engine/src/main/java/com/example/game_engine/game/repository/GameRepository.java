package com.example.game_engine.game.repository;

import com.example.game_engine.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPARepository interface with domain type Game and integer as id
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
}
