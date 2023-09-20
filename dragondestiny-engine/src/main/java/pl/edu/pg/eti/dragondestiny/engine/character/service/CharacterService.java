package pl.edu.pg.eti.dragondestiny.engine.character.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterListDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.Character;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.CharacterList;
import pl.edu.pg.eti.dragondestiny.engine.character.repository.CharacterRepository;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;

import java.util.ArrayList;
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
     * @param characterRepository Repository with methods for retrieval of data from database.
     */
    @Autowired
    public CharacterService(CharacterRepository characterRepository) {

        this.characterRepository = characterRepository;
    }

    /**
     * Returns character by ID.
     *
     * @param id An identifier of a character to be retrieved.
     * @return A retrieved character.
     */
    public Optional<Character> getCharacterById(Integer id) {

        return characterRepository.findById(id);
    }

    /**
     * Returns all characters in particular game.
     *
     * @param game A game to find characters from.
     * @return A list of characters in game of ID gameId.
     */
    public List<Character> getCharactersByGame(Game game) {

        return characterRepository.findAllByGames(game);
    }

    /**
     * Returns character of ID characterId found in particular game.
     *
     * @param game        A game to find character from.
     * @param characterId An identifier of character.
     * @return A retrieved character.
     */
    public Optional<Character> getCharacterByGame(Game game, Integer characterId) {
        
        return characterRepository.findByGamesAndId(game, characterId);
    }

    /**
     * Retrieves all characters from the database.
     *
     * @return A structure containing list of characters.
     */
    public Optional<CharacterList> getCharacters() {
        List<Character> characterList = characterRepository.findAll();
        if (characterList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new CharacterList(characterList));
    }

    /**
     * Converts CharacterList into CharacterListDTO.
     *
     * @param modelMapper   Allows conversion from object to DTO.
     * @param characterList A structure containing list of characters.
     * @return A DTO.
     */
    public CharacterListDTO convertCharacterListToDTO(ModelMapper modelMapper, CharacterList characterList) {
        List<CharacterDTO> characterDTOList = new ArrayList<>();
        characterList.getCharacterList().forEach(character -> {
            CharacterDTO characterDTO = modelMapper.map(character, CharacterDTO.class);
            characterDTOList.add(characterDTO);
        });
        return new CharacterListDTO(characterDTOList);
    }

}
