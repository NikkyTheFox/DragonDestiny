package com.example.game.entities.character;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlayedCharacterService {

    private PlayedCharacterRepository playedCharacterRepository;

    @Autowired
    public PlayedCharacterService(PlayedCharacterRepository character_repo) {
        this.playedCharacterRepository = character_repo;
    }

    public Optional<PlayedCharacter> findById(Integer id) {
        return playedCharacterRepository.findById(id);
    }

    public List<PlayedCharacter> findAll() {
        return playedCharacterRepository.findAll();
    }
    @Transactional
    public PlayedCharacter save(PlayedCharacter playedCharacter) {
        return playedCharacterRepository.save(playedCharacter);
    }
    @Transactional
    public void deleteById(Integer id) {
        playedCharacterRepository.deleteById(id);
    }

    @Transactional
    public void update(PlayedCharacter playedCharacter){ playedCharacterRepository.save(playedCharacter);}

}
