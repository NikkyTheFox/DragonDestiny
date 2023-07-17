package pl.edu.pg.eti.game.user.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.game.user.user.entity.User;
import pl.edu.pg.eti.game.user.game.entity.Game;
import pl.edu.pg.eti.game.user.game.repository.GameRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    /**
     * JPA repository communicating with database.
     */
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Returns game by id
     *
     * @param id
     * @return game found
     */
    public Optional<Game> findGame(String id) {
        return gameRepository.findById(id);
    }

    /**
     * Returns all games found.
     *
     * @return list of games
     */
    public List<Game> findGames() {
        return gameRepository.findAll();
    }

    /**
     * Returns all games found by user.
     *
     * @return list of games
     */
    public List<Game> findGames(User user) {
        return gameRepository.findAllByUserList(user);
    }

    /**
     * Adds new game to the database,
     *
     * @param game - game request
     * @return saved game
     */
    public Game save(Game game) {
        return gameRepository.save(game);
    }

}
