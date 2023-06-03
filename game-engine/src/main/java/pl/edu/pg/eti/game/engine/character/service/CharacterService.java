package pl.edu.pg.eti.game.engine.character.service;

import pl.edu.pg.eti.game.engine.character.entity.Character;
import pl.edu.pg.eti.game.engine.character.repository.CharacterRepository;
import pl.edu.pg.eti.game.engine.game.entity.Game;
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
     *
     * @param characterRepository
     */
    @Autowired
    public CharacterService(CharacterRepository characterRepository)
    {
        this.characterRepository = characterRepository;
    }

    /**
     * Returns character by ID.
     *
     * @param id
     * @return character found
     */
    public Optional<Character> findCharacter(Integer id) {
        return characterRepository.findById(id);
    }

    /**
     * Returns all characters found.
     *
     * @return list of characters
     */
    public List<Character> findCharacters() {
        return characterRepository.findAll();
    }

    /**
     * Returns all characters in particular game.
     *
     * @param game - game to find characters from.
     * @return list of characters in game of ID gameId
     */
    public List<Character> findCharacters(Game game) {
        return characterRepository.findAllByGames(game);
    }

    /**
     * Returns character of ID characterId found in particular game.
     *
     * @param game - game to find character from.
     * @param characterId - identifier of card
     * @return character of ID characterId if such exists in game
     */
    public Optional<Character> findCharacter(Game game, Integer characterId) {
        return characterRepository.findByGamesAndId(game, characterId);
    }

}
