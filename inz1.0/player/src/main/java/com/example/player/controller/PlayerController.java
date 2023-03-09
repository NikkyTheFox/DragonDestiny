
package com.example.player.controller;

import com.example.player.dto.GetPlayerResponse;
import com.example.player.entities.Player;
import com.example.player.entities.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("{playerId}")
    public ResponseEntity<GetPlayerResponse> getPlayer(@PathVariable("playerId") Integer playerId) {
        Optional<Player> player = playerService.findById(playerId);
        return player.map(value->ResponseEntity.ok(GetPlayerResponse.entityToDtoMapper().apply(value)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
