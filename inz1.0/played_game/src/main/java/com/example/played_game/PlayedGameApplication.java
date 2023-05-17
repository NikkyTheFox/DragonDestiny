package com.example.played_game;

import com.example.played_game.played_game.PlayedGame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


/**
 * FOR GRAPHICS - create a method in PlayedField/Card etc to call a GET request - ??
 * should it be like this - is it not waste of space ??
 */
@SpringBootApplication()
public class PlayedGameApplication {

	static Integer numOfPlayedGames = 1;

	static List<PlayedGame> playedGames = new ArrayList<>();

	public static void main(String[] args)
	{
		SpringApplication.run(PlayedGameApplication.class, args);
	}

}
