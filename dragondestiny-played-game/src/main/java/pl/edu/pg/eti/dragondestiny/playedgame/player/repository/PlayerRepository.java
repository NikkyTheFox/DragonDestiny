package pl.edu.pg.eti.dragondestiny.playedgame.player.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;

/**
 * A repository with method to retrieve and update data in the database containing data about players.
 */
@Repository
public class PlayerRepository {
    private final WebClient client;

    /**
     * A constructor for PlayerRepository with WebClient instance.
     *
     * @param webClientBuilder The builder used to create the WebClient instance.
     */
    @Autowired
    public PlayerRepository(WebClient.Builder webClientBuilder){
        this.client = webClientBuilder.baseUrl("http://GAME-USER/api/users").build();
    }

    /**
     * Retrieves player from the database by a login.
     *
     * @param playerLogin An identifier of a player.
     * @return A retrieved player.
     */
    public Player findByLogin(String playerLogin){
        ResponseEntity<Player> playerDTOResponseEntity = client.get()
                .uri("/{playerLogin}", playerLogin)
                .retrieve()
                .toEntity(Player.class)
                .block();
        if(playerDTOResponseEntity == null){
            return null;
        }
        return playerDTOResponseEntity.getBody();
    }

    /**
     * Adds a specific game to the player's game history.
     *
     * @param playerLogin An identifier of a player.
     * @param playedGameId An identifier of a game.
     */
    public void addGame(String playerLogin, String playedGameId){
        client.put()
                .uri("/{login}/addGame/{gameId}", playerLogin, playedGameId)
                .retrieve()
                .toEntity(Player.class)
                .block();
    }
}
