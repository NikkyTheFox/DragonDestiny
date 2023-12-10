package pl.edu.pg.eti.dragondestiny.engine.character.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterListDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.Character;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.CharacterList;
import pl.edu.pg.eti.dragondestiny.engine.character.service.GameCharacterService;

import java.util.Optional;

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
     * GameCharacterService used to communicate with service layer that will communicate with database repository.
     */
    private final GameCharacterService gameCharacterService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param modelMapper          Service for data retrieval and manipulation.
     * @param gameCharacterService Service for data retrieval and manipulation.
     */
    @Autowired
    public GameCharacterController(ModelMapper modelMapper, GameCharacterService gameCharacterService) {
        this.modelMapper = modelMapper;
        this.gameCharacterService = gameCharacterService;
    }

    /**
     * Retrieve all characters added to game of gameId ID.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of characters.
     */
    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)})
    public ResponseEntity<CharacterListDTO> getCharacters(@PathVariable("gameId") Integer gameId) {
        Optional<CharacterList> characterList = gameCharacterService.getGameCharacters(gameId);
        return characterList.map(list -> ResponseEntity.ok().body(gameCharacterService.convertCharacterListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve character by its ID that is added to game of gameId ID.
     *
     * @param gameId      An identifier of a game.
     * @param characterId An identifier of a character to be retrieved.
     * @return Retrieved character.
     */
    @GetMapping("/{characterId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Character in game not found", content = @Content)})
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable(name = "gameId") Integer gameId, @PathVariable(name = "id") Integer characterId) {
        Optional<Character> character = gameCharacterService.getGameCharacter(gameId, characterId);
        return character.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CharacterDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
