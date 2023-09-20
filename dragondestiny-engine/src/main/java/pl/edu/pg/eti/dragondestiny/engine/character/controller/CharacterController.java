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
import pl.edu.pg.eti.dragondestiny.engine.character.service.CharacterService;

import java.util.Optional;

/**
 * Represents REST controller, allows to handle requests to get characters.
 * Requests go through /api/characters - they represent all characters, not only those assigned to games.
 */
@RestController
@RequestMapping(value = {"/api/characters"})
public class CharacterController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * CharacterService used to communicate with service layer that will communicate with database repository.
     */
    private final CharacterService characterService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param characterService Service with methods for data manipulation and retrieval.
     * @param modelMapper      Mapper allowing conversion from objects to DTOs.
     */
    @Autowired
    public CharacterController(CharacterService characterService, ModelMapper modelMapper) {
        this.characterService = characterService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves all characters.
     *
     * @return A structure containing list of characters.
     */
    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    public ResponseEntity<CharacterListDTO> getCharacters() {
        Optional<CharacterList> characterList = characterService.getCharacters();
        return characterList.map(list -> ResponseEntity.ok().body(characterService.convertCharacterListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves character by ID.
     *
     * @param id An identifier of character to be retrieved.
     * @return A retrieved character.
     */
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Character not found", content = @Content)})
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable(name = "id") Integer id) {
        Optional<Character> character = characterService.getCharacterById(id);
        return character.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CharacterDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
