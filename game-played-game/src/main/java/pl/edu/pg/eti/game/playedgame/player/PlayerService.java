package pl.edu.pg.eti.game.playedgame.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGame;

import java.util.Optional;

@Service
public class PlayerService {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public PlayerService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    public Optional<Player> findByLogin(String login) {

        WebClient client = webClientBuilder.build();

        ResponseEntity<Player> playerResponseEntity = client.get()
                .uri("http://GAME-USER/api/users/" + login)
                .retrieve()
                .toEntity(Player.class)
                .block();
        if (playerResponseEntity != null && playerResponseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("FOUND PLAYER: " + playerResponseEntity.getBody().getLogin());
            return Optional.ofNullable(playerResponseEntity.getBody());
        }
        else
            return Optional.empty();
    }

    public void addGame(String login, String gameId) {

        WebClient client = webClientBuilder.build();

        ResponseEntity<Player> playerResponseEntity = client.put()
                .uri("http://GAME-USER/api/users/" + login + "/addGame/" + gameId)
                .retrieve()
                .toEntity(Player.class)
                .block();

    }
}
