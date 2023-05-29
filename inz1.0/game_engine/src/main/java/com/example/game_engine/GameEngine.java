package com.example.game_engine;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * GAME ENGINE - represents a box, version of playable game.
 * Stores all information about game such as board, cards and characters.
 */

@SpringBootApplication
public class GameEngine {
	/**
	 * Model mapper used to map entities to Data Transfer Objects (DTO).
	 * @return modelMapper for the game
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(GameEngine.class, args);
	}

}
