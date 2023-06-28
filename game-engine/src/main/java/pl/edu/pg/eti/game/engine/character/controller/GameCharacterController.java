package pl.edu.pg.eti.game.engine.character.controller;

import pl.edu.pg.eti.game.engine.character.dto.CharacterDTO;
import pl.edu.pg.eti.game.engine.character.dto.CharacterListDTO;
import pl.edu.pg.eti.game.engine.character.entity.Character;
import pl.edu.pg.eti.game.engine.character.service.CharacterService;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import pl.edu.pg.eti.game.engine.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents REST controller, allows to handle requests to get characters in game.
 * Requests go through /api/games/{gameId}/characters - represent characters found in game of id gameId.
 */

@RestController
@RequestMapping(value = {"/api/games/{gameId}/characters"})
public class GameCharacterController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

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
     * @param characterService
     * @param gameService
     * @param modelMapper
     */
    @Autowired
    public GameCharacterController(CharacterService characterService, GameService gameService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.characterService = characterService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all characters added to game of gameId ID.
     *
     * @param gameId - game identifier
     * @return list of cards in game
    */
    @GetMapping()
    public ResponseEntity<CharacterListDTO> getCharacters(@PathVariable("gameId") Integer gameId) {
        // find game
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find characters
        List<CharacterDTO> characterDTOList = characterService.findCharacters(game.get()).stream()
                .map(card -> modelMapper.map(card, CharacterDTO.class))
                .collect(Collectors.toList());
        CharacterListDTO characterListDTO = new CharacterListDTO(characterDTOList);
        return ResponseEntity.ok().body(characterListDTO);
    }

    /**
     * Retrieve character by its ID that is added to game of gameId ID.
     *
     * @param id - identifier of character
     * @param gameId - game identifier
     * @return ResponseEntity containing CharacterDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable(name = "gameId") Integer gameId, @PathVariable(name = "id") Integer id) {
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<Character> character = characterService.findCharacter(game.get(), id);
        if (character.isEmpty())
            return ResponseEntity.notFound().build();
        CharacterDTO characterResponse = modelMapper.map(character, CharacterDTO.class);
        return ResponseEntity.ok().body(characterResponse);
    }

}
