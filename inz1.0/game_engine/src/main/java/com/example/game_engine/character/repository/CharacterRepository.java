package com.example.game_engine.character.repository;

import com.example.game_engine.character.entity.Character;
import com.example.game_engine.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPARepository interface with domain type Character and Integer as id
 */
@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {

    /**
     * Method to retrieve character by game and characterId - one character from all in particular game.
     * @param game - game to find character from
     * @param characterId - identifier of character
     * @return card in game of ID gameId
     */
    Character findCharacterByGamesAndId(Game game, Integer characterId);

    /**
     * Method to retrieve all characters by game - all characters in particular game.
     * @param game - game to find all characters from
     * @return list of characters in game
     */
    List<Character> findAllByGames(Game game);

}
