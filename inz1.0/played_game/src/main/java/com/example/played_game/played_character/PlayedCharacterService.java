package com.example.played_game.played_character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayedCharacterService {

    private PlayedCharacterRepository playedCharacterRepository;

    @Autowired
    public PlayedCharacterService(PlayedCharacterRepository playedCharacterRepository)
    {
        this.playedCharacterRepository = playedCharacterRepository;
    }

    public List<PlayedCharacter> findAll() {return playedCharacterRepository.findAll();}


    public PlayedCharacter save(PlayedCharacter character) {
        return playedCharacterRepository.save(character);
    }

    public PlayedCharacter findById(Integer id) {
        Optional<PlayedCharacter> character = playedCharacterRepository.findById(id);
        if (character.isPresent()) { // found
            return character.get();
        } else throw new RuntimeException("No character found");
    }

    public void deleteById(Integer id) {
        playedCharacterRepository.findById(id).orElseThrow(() -> new RuntimeException("No character found"));
        playedCharacterRepository.deleteById(id);
    }
    public PlayedCharacter update(Integer id, PlayedCharacter characterRequest) {
        PlayedCharacter character = playedCharacterRepository.findById(id).orElseThrow(() -> new RuntimeException("No character found"));
        character.setInitialHealth(characterRequest.getInitialHealth());
        character.setInitialStrength(characterRequest.getInitialStrength());
        character.setAdditionalHealth(characterRequest.getAdditionalHealth());
        character.setAdditionalStrength(characterRequest.getAdditionalStrength());
        return playedCharacterRepository.save(character);
    }


}
