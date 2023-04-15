package com.example.game.game.service;


import com.example.game.game.entity.Game;
import com.example.game.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
    }

    public Game findById(Integer id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isPresent()) { // found
            return game.get();
        } else throw new RuntimeException("No game found");
    }
    public List<Game> findAll() {return gameRepository.findAll();}
    @Transactional
    public Game save(Game board) {
        return gameRepository.save(board);
    }
    @Transactional
    public void deleteById(Integer id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("No game found"));
        gameRepository.deleteById(id);
    }
    @Transactional
    public Game update(Integer id, Game gameRequest) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("No game found"));
        game.setBoardId(gameRequest.getBoardId());
        return gameRepository.save(game);
    }

}
