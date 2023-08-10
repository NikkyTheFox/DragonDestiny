package pl.edu.pg.eti.dragondestiny.engine.character.controller;

import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterListDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.Character;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.CharacterList;
import pl.edu.pg.eti.dragondestiny.engine.character.service.CharacterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param modelMapper Mapper allowing conversion from objects to DTOs.
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
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable(name = "id") Integer id) {
        Optional<Character> character = characterService.findCharacter(id);
        return character.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CharacterDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
