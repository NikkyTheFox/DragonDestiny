package com.example.game_engine.character.controller;

import com.example.game_engine.character.dto.CharacterDTO;
import com.example.game_engine.character.entity.Character;
import com.example.game_engine.character.service.CharacterService;
import com.example.game_engine.game.entity.Game;
import com.example.game_engine.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
     * @param characterService
     * @param gameService
     * @param modelMapper
     */
    @Autowired
    GameCharacterController(CharacterService characterService, GameService gameService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.characterService = characterService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all characters added to game of gameId ID.
     * @param gameId - game identifier
     * @return list of cards in game
     */
    @GetMapping()
    public List<CharacterDTO> getAllCharacters(@PathVariable("gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
        List<Character> characterList = characterService.findAllByGameId(game.getId());
        List<CharacterDTO> characterDTOList = new ArrayList<>();
        for (Character character : characterList)
            characterDTOList.add(modelMapper.map(character, CharacterDTO.class));
        return characterDTOList;
    }

    /**
     * Retrieve character by its ID that is added to game of gameId ID.
     * @param id - identifier of character
     * @param gameId - game identifier
     * @return ResponseEntity containing CharacterDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getCharacterById(@PathVariable(name = "id") Integer id, @PathVariable(name = "gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
        Character character = characterService.findCharacterByGameIdAndId(game.getId(), id);
        CharacterDTO characterResponse = modelMapper.map(character, CharacterDTO.class);
        return ResponseEntity.ok().body(characterResponse);
    }

}
