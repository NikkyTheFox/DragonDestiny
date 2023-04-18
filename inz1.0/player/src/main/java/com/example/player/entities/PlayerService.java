
package com.example.player.entities;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Player> findAll() {
        return repository.findAll();
    }
    @Transactional
    public Player save(Player player) {
        return repository.save(player);
    }

    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
    @Transactional
    public void update(Player player){
        repository.save(player);
    }

}
