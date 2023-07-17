package pl.edu.pg.eti.game.engine.game.service;

import pl.edu.pg.eti.game.engine.game.entity.Game;
import pl.edu.pg.eti.game.engine.game.repository.GameRepository;
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
     * Returns game by ID.
     * @param id
     * @return game found or empty
     */
    public Optional<Game> findGame(Integer id) {
        return gameRepository.findById(id);
    }

    /**
     * Returns all games found.
     * @return list of games
     */
    public List<Game> findGames() {
        return gameRepository.findAll();
    }

}
