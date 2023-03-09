package com.example.game.entities.character;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class PlayedCharacterService {

    private PlayedCharacterRepository character_repo;

    @Autowired
    public PlayedCharacterService(PlayedCharacterRepository character_repo) {
        this.character_repo = character_repo;
    }

    public Optional<PlayedCharacter> findById(Integer id) {
        return character_repo.findById(id);
    }

    public Iterable<PlayedCharacter> findAll() {
        return character_repo.findAll();
    }

    public PlayedCharacter save(PlayedCharacter videoCassette) {
        return character_repo.save(videoCassette);
    }

    public void deleteById(Integer id) {
        character_repo.deleteById(id);
    }
}
