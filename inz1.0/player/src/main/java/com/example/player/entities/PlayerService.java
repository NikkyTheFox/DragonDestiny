
package com.example.player.entities;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private PlayerRepository repository;

    @Autowired
    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Optional<Player> findById(Integer id) {
        return repository.findById(id);
    }

    public Iterable<Player> findAll() {
        return repository.findAll();
    }

    public Player save(Player videoCassette) {
        return repository.save(videoCassette);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

}
