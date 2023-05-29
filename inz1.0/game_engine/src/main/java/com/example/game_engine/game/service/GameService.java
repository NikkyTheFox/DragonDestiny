package com.example.game_engine.game.service;

import com.example.game_engine.game.entity.Game;
import com.example.game_engine.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Game Service to communication with games' data saved in database.
 */
@Service
public class GameService {

    /**
     * JPA repository communicating with database.
     */
    private final GameRepository gameRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     * @param gameRepository
     */
    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Returns game by ID. If no card found, throws exception.
     * @param id
     * @return game found
     */
    public Game findById(Integer id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isPresent()) {
            return game.get();
        } else throw new RuntimeException("No game found");
    }

    /**
     * Returns all games found.
     * @return list of games
     */
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

}
