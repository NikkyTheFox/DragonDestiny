package com.example.game_engine.character.service;

import com.example.game_engine.character.entity.Character;
import com.example.game_engine.character.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Character Service to communication with characters' data saved in database.
 */
@Service
public class CharacterService {
    /**
     * JPA repository communicating with database.
     */
    private final CharacterRepository characterRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     * @param characterRepository
     */
    @Autowired
    public CharacterService(CharacterRepository characterRepository)
    {
        this.characterRepository = characterRepository;
    }

    /**
     * Returns character by ID. If no character found, throws exception.
     * @param id
     * @return character found
     */
    public Character findById(Integer id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent()) {
            return character.get();
        } else throw new RuntimeException("No character found");
    }

    /**
     * Returns all characters found.
     * @return list of characters
     */
    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    /**
     * Returns all characters in game of ID gameId.
     * @param gameId - identifier of game
     * @return list of characters in game of ID gameId
     */
    public List<Character> findAllByGameId(Integer gameId) {
        return characterRepository.findCharactersByGameId(gameId);
    }

    /**
     * Returns character of ID characterId found in game of ID gameId.
     * @param gameId - identifier of game
     * @param characterId - identifier of card
     * @return character of ID characterId if such exists in game of ID gameId
     */
    public Character findCharacterByGameIdAndId(Integer gameId, Integer characterId) {
        return characterRepository.findCharacterByGameIdAndId(gameId, characterId);
    }

}
