package pl.edu.pg.eti.dragondestiny.engine.character.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterListDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.Character;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.CharacterList;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.engine.game.service.GameService;

import java.util.List;
import java.util.Optional;

/**
 * Game Character Service to communication with game's characters data saved in database.
 */
@Service
public class GameCharacterService {

    /**
     * CharacterService used to communicate with service layer that will communicate with database repository.
     */
    private final CharacterService characterService;

    /**
     * GameService used to communicate with service layer that will communicate with database repository.
     */
    private final GameService gameService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param characterService Service for data retrieval and manipulation.
     * @param gameService Service for data retrieval and manipulation.
     */
    @Autowired
    public GameCharacterService(CharacterService characterService, GameService gameService) {
        this.gameService = gameService;
        this.characterService = characterService;
    }

    /**
     * Retrieves all characters from the specified game.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of characters.
     */
    public Optional<CharacterList> getGameCharacters(Integer gameId){
        Optional<Game> game = gameService.findGame(gameId);
        if(game.isEmpty()){
            return Optional.empty();
        }
        List<Character> characterList = characterService.findCharacters(game.get());
        if(characterList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new CharacterList(characterList));
    }

    /**
     * Retrieves character specified by ID from the game.
     *
     * @param gameId An identifier of a game.
     * @param characterId An identifier of a character to be retrieved.
     * @return A retrieved character.
     */
    public Optional<Character> getGameCharacter(Integer gameId, Integer characterId){
        Optional<Game> game = gameService.findGame(gameId);
        if(game.isEmpty()){
            return Optional.empty();
        }
        return characterService.findCharacter(game.get(), characterId);
    }

    /**
     * Converts CharacterList into CharacterListDTO.
     *
     * @param modelMapper Mapper allowing conversion.
     * @param characterList A structure containing list of characters.
     * @return A DTO.
     */
    public CharacterListDTO convertCharacterListToDTO(ModelMapper modelMapper, CharacterList characterList){
        return characterService.convertCharacterListToDTO(modelMapper, characterList);
    }
}
