package pl.edu.pg.eti.dragondestiny.engine;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * GAME ENGINE - represents a box, version of playable game.
 * Stores all information about game such as board, cards and characters.
 */
@ConfigurationProperties("played-game")
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Dragon Destiny Game Engine API", version = "v1", description =
        "Used to retrieve all information about game such as board, cards and characters"))
public class GameEngine {

    public static void main(String[] args) {
        SpringApplication.run(GameEngine.class, args);
    }

}
