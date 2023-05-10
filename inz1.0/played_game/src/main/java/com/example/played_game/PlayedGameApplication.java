package com.example.played_game;

import com.example.played_game.played_game.PlayedGame;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

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
		System.out.println("INNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");

		// create new played game from GAME BOX 1
		InitializePlayedGame game = new InitializePlayedGame();
		PlayedGame playedGame = game.initialize(1, numOfPlayedGames);
		System.out.println("INITIALIZED ID : " + playedGame.getId());
		playedGames.add(playedGame);
		numOfPlayedGames++;

		// new game
		InitializePlayedGame game2 = new InitializePlayedGame();
		PlayedGame playedGame2 = game.initialize(1, numOfPlayedGames);
		System.out.println("INITIALIZED ID : " + playedGame2.getId());
		playedGames.add(playedGame2);
		numOfPlayedGames++;
	}

}
