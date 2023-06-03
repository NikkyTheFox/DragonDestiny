package pl.edu.pg.eti.game.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * GAME ENGINE - represents a box, version of playable game.
 * Stores all information about game such as board, cards and characters.
 */

@Import(GameEngineConfig.class)
@SpringBootApplication
public class GameEngine {

	public static void main(String[] args) {
		SpringApplication.run(GameEngine.class, args);
	}

}
