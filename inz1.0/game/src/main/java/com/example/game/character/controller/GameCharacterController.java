package com.example.game.character.controller;

import com.example.game.character.dto.CharacterDTO;
import com.example.game.character.entity.Character;
import com.example.game.character.service.CharacterService;
import com.example.game.game.entity.Game;
import com.example.game.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"/api/games/{gameid}/characters"})
public class GameCharacterController {

    private ModelMapper modelMapper;
    private CharacterService characterService;

    private GameService gameService;
    @Autowired
    GameCharacterController(GameService gameService, CharacterService characterService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.characterService = characterService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<CharacterDTO> getAllCharacters(@PathVariable("gameid") Integer gameid) {
        Game game = gameService.findById(gameid);
        List<Character> characterList = characterService.findAllByGameId(game.getId());
        List<CharacterDTO> characterDTOList = new ArrayList<>();
        for (Character character : characterList)
            characterDTOList.add(modelMapper.map(character, CharacterDTO.class));
        return characterDTOList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getCharacterById(@PathVariable(name = "id") Integer id, @PathVariable(name = "gameid") Integer gameid) {
        Game game = gameService.findById(gameid);
        Character character = characterService.findCharacterByGameIdAndId(gameid, id);
        // convert character entity to DTO
        CharacterDTO characterResponse = modelMapper.map(character, CharacterDTO.class);
        return ResponseEntity.ok().body(characterResponse);
    }

}
