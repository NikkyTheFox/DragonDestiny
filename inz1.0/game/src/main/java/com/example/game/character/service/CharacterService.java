package com.example.game.character.service;

import com.example.game.character.entity.Character;
import com.example.game.character.repository.CharacterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CharacterService {

    private CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository)
    {
        this.characterRepository = characterRepository;
    }

    public Character findById(Integer id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent()) { // found
            return character.get();
        } else throw new RuntimeException("No character found");
    }
    public List<Character> findAll() {return characterRepository.findAll();}

    public List<Character> findAllByGameId(Integer id) {return characterRepository.findCharactersByGameId(id);}

    public Character findCharacterByGameIdAndId(Integer gameId, Integer characterId) {return characterRepository.findCharacterByGameIdAndId(gameId, characterId);}

    @Transactional
    public Character save(Character character) {
        return characterRepository.save(character);
    }

    @Transactional
    public void deleteById(Integer id) {
        characterRepository.findById(id).orElseThrow(() -> new RuntimeException("No character found"));
        characterRepository.deleteById(id);
    }
    @Transactional
    public Character update(Integer id, Character characterRequest) {
        Character character = characterRepository.findById(id).orElseThrow(() -> new RuntimeException("No character found"));
        character.setName(characterRequest.getName());
        character.setStory(characterRequest.getStory());
        character.setProfession(characterRequest.getProfession());
        character.setInitialHealth(characterRequest.getInitialHealth());
        character.setInitialStrength(characterRequest.getInitialStrength());
        return characterRepository.save(character);
    }

}
