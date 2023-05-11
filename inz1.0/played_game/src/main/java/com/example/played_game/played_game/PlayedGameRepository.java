package com.example.played_game.played_game;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayedGameRepository extends MongoRepository<PlayedGame, Integer> {
}
