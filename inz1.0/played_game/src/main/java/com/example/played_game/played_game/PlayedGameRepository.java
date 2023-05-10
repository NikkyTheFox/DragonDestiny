package com.example.played_game.played_game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayedGameRepository extends JpaRepository<PlayedGame, Integer> {
}
