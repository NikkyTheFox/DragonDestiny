package com.example.played_game.played_game;

import com.example.played_game.played_character.PlayedCharacter;
import com.example.played_game.played_character.PlayedCharacterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayedGameService {

    private PlayedGameRepository playedGameRepository;

    @Autowired
    public PlayedGameService(PlayedGameRepository playedGameRepository)
    {
        this.playedGameRepository = playedGameRepository;
    }

    public PlayedGame findById(Integer id) {
        System.out.println("IN SERVICE ID : " + id);
        Optional<PlayedGame> character = playedGameRepository.findById(id);
        if (character.isPresent()) { // found
            return character.get();
        } else throw new RuntimeException("No game found");
    }
    public List<PlayedGame> findAll() {return playedGameRepository.findAll();}

    @Transactional
    public PlayedGame save(PlayedGame character) {
        return playedGameRepository.save(character);
    }

    @Transactional
    public void deleteById(Integer id) {
        playedGameRepository.findById(id).orElseThrow(() -> new RuntimeException("No character found"));
        playedGameRepository.deleteById(id);
    }
    @Transactional
    public PlayedGame update(Integer id, PlayedGame characterRequest) {
        PlayedGame character = playedGameRepository.findById(id).orElseThrow(() -> new RuntimeException("No character found"));
        return playedGameRepository.save(character);
    }

}
