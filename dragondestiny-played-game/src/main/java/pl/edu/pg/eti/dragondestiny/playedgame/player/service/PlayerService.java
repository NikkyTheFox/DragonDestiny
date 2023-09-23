package pl.edu.pg.eti.dragondestiny.playedgame.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;
import pl.edu.pg.eti.dragondestiny.playedgame.player.repository.PlayerRepository;

import java.util.Optional;

/**
 * A service to retrieve and modify data of player's database.
 */
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    /**
     * A constructor of PlayerService with PlayerRepository instance.
     *
     * @param playerRepository A repository with methods of data retrieval and manipulation.
     */
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {

        this.playerRepository = playerRepository;
    }

    /**
     * Retrieves data about specific player by player's login.
     *
     * @param playerLogin An identifier of a player.
     * @return A retrieved player.
     */
    public Optional<Player> findByLogin(String playerLogin) {
        Player player = playerRepository.findByLogin(playerLogin);
        if (player != null) {
            return Optional.of(player);
        } else
            return Optional.empty();
    }

    /**
     * Adds specific game to given player's game history.
     *
     * @param playerLogin  An identifier of a player.
     * @param playedGameId An identifier of a game.
     */
    public void addGame(String playerLogin, String playedGameId) {
        playerRepository.addGame(playerLogin, playedGameId);
    }
}
