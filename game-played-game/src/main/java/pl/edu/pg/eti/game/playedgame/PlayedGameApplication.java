package pl.edu.pg.eti.game.playedgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FOR GRAPHICS - create a method in PlayedField/Card etc to call a GET request - ??
 * should it be like this - is it not waste of space ??
 */

@SpringBootApplication()
public class PlayedGameApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(PlayedGameApplication.class, args);
	}

}
