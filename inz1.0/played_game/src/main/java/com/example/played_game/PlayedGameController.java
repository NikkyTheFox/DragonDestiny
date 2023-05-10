package com.example.played_game;


import com.example.played_game.played_character.PlayedCharacter;
import com.example.played_game.played_character.PlayedCharacterService;
import com.example.played_game.played_game.PlayedGame;
import com.example.played_game.played_game.PlayedGameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/playedgame/{playedgameid}")
public class PlayedGameController
{
    private PlayedCharacterService playedCharacterService;
    private PlayedGameService playedGameService;

    @Autowired
    PlayedGameController(PlayedCharacterService playedCharacterService, PlayedGameService playedGameService) {
        this.playedCharacterService = playedCharacterService;
        this.playedGameService = playedGameService;
    }

    @GetMapping()
    public PlayedGame getGame(@PathVariable(name = "playedgameid") Integer playedgameid)
    {
        PlayedGame temp = null;
        for (PlayedGame g : PlayedGameApplication.playedGames)
        {
            if (g.getId() == playedgameid)
                temp = g;
        }
        if (temp == null)
            return null;
        return temp;
    }

    @GetMapping("/characters")
    public List<PlayedCharacter> getAllCharacters(@PathVariable(name = "playedgameid") Integer playedgameid)
    {
        PlayedGame temp = getGame(playedgameid);
        if (temp == null)
            return null;
        return temp.getCharactersInGame();
    }
    @GetMapping("/characters/{id}")
    public PlayedCharacter getCharacterById(@PathVariable(name = "playedgameid") Integer playedgameid, @PathVariable(name = "id") Integer id) {
        PlayedGame temp = getGame(playedgameid);
        if (temp == null)
            return null;
        PlayedCharacter character = null;
        for (PlayedCharacter c : temp.getCharactersInGame())
        {
            if (c.getId() == id)
                character = c;
        }
        if (character == null)
            return null;
        return character;
    }

    @PutMapping("/characters/{id}/update")
    public PlayedCharacter addStrengthToCharacter(@PathVariable(name = "playedgameid") Integer playedgameid, @PathVariable(name = "id") Integer id, @RequestBody PlayedCharacter playedCharacterRequest) {
        PlayedGame temp = getGame(playedgameid);
        if (temp == null)
            return null;
        PlayedCharacter character = getCharacterById(playedgameid, id);
        if (character == null)
            return null;
        character.setAdditionalStrength(character.getAdditionalStrength() + playedCharacterRequest.getAdditionalStrength());
        character.setAdditionalHealth(character.getAdditionalHealth() + playedCharacterRequest.getAdditionalHealth());
        character.setPositionOnField(playedCharacterRequest.getPositionOnField());
        return character;
    }

}
