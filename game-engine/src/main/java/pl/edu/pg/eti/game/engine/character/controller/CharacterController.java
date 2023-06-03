package pl.edu.pg.eti.game.engine.character.controller;

import pl.edu.pg.eti.game.engine.character.dto.CharacterDTO;
import pl.edu.pg.eti.game.engine.character.dto.CharacterListDTO;
import pl.edu.pg.eti.game.engine.character.entity.Character;
import pl.edu.pg.eti.game.engine.character.service.CharacterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @param characterService
     * @param modelMapper
     */
    @Autowired
    public CharacterController(CharacterService characterService, ModelMapper modelMapper) {
        this.characterService = characterService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all characters.
     *
     * @return list of CharacterDTOs
     */
    @GetMapping()
    public ResponseEntity<CharacterListDTO> getCharacters() {
        List<CharacterDTO> characterDTOList = characterService.findCharacters().stream()
                .map(card -> modelMapper.map(card, CharacterDTO.class))
                .collect(Collectors.toList());
        CharacterListDTO characterListDTO = new CharacterListDTO(characterDTOList);
        return ResponseEntity.ok().body(characterListDTO);
    }

    /**
     * Retrieve character by ID.
     *
     * @param id - identifier of character
     * @return ResponseEntity containing CharacterDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable(name = "id") Integer id) {
        Optional<Character> character = characterService.findCharacter(id);
        if (character.isEmpty())
            return ResponseEntity.notFound().build();
        CharacterDTO characterResponse = modelMapper.map(character.get(), CharacterDTO.class);
        return ResponseEntity.ok().body(characterResponse);
    }

}
