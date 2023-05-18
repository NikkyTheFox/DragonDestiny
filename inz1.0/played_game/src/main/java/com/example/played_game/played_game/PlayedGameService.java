package com.example.played_game.played_game;

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
        Optional<PlayedGame> game = playedGameRepository.findById(id);
        if (game.isPresent()) { // found
            return game.get();
        } else throw new RuntimeException("No game found");
    }
    public List<PlayedGame> findAll() {return playedGameRepository.findAll();}

    public PlayedGame save(PlayedGame character) {
        return playedGameRepository.save(character);
    }

    public void deleteById(Integer id) {
        playedGameRepository.findById(id).orElseThrow(() -> new RuntimeException("No game found"));
        playedGameRepository.deleteById(id);
    }
    public PlayedGame update(Integer id, PlayedGame playedGameRequest) {
        PlayedGame game = playedGameRepository.findById(id).orElseThrow(() -> new RuntimeException("No game found"));
        game.setId(playedGameRequest.getId());
        game.setCharactersInGame(playedGameRequest.getCharactersInGame());
        game.setBoard(playedGameRequest.getBoard());
        game.setPlayingPlayers(playedGameRequest.getPlayingPlayers());
        game.setCardDeck(playedGameRequest.getCardDeck());
        game.setUsedCardDeck(playedGameRequest.getUsedCardDeck());
        return playedGameRepository.save(game);
    }

}
