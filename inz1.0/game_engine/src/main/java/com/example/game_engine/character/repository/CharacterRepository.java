package com.example.game_engine.character.repository;

import com.example.game_engine.character.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPARepository interface with domain type Character and Integer as id
 */
@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {

    /**
     * Method to retrieve character by gameId and characterId - one character from all in particular game.
     * @param gameId - identifier of game
     * @param characterId - identifier of card
     * @return card in game of ID gameId
     */
    Character findCharacterByGameIdAndId(Integer gameId, Integer characterId);

    /**
     * Method to retrieve all characters by gameId - all characters in particular game.
     * @param gameId - identifier of game
     * @return list of characters in game
     */
    List<Character> findCharactersByGameId(Integer gameId);

}
