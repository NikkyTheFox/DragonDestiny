
package com.example.player.controller;

import com.example.player.dto.CreatePlayerRequest;
import com.example.player.dto.GetPlayerResponse;
import com.example.player.dto.GetPlayersResponse;
import com.example.player.dto.UpdatePlayerRequest;
import com.example.player.entities.Player;
import com.example.player.entities.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<GetPlayersResponse> GetPlayers(){
        return ResponseEntity.ok(GetPlayersResponse.entityToDtoMapper().apply(playerService.findAll()));
    }

    @GetMapping("{playerId}")
    public ResponseEntity<GetPlayerResponse> getPlayer(@PathVariable("playerId") Integer playerId) {
        Optional<Player> player = playerService.findById(playerId);
        return player.map(value->ResponseEntity.ok(GetPlayerResponse.entityToDtoMapper().apply(value)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createPlayer(@RequestBody CreatePlayerRequest request, UriComponentsBuilder builder){
        Player player = CreatePlayerRequest.dtoToEntityMapper().apply(request);
        playerService.save(player);
        return ResponseEntity.created(
                builder.pathSegment("api","players","{id}")
                        .buildAndExpand(player.getId()).toUri()).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePlayer (@PathVariable("id") Integer id){
        Optional<Player> player = playerService.findById(id);
        if(player.isPresent()){
            playerService.deleteById(player.get().getId());
            return ResponseEntity.accepted().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePlayer(@RequestBody UpdatePlayerRequest request, @PathVariable("id") Integer id){
        Optional<Player> player = playerService.findById(id);
        if(player.isPresent()){
            UpdatePlayerRequest.dtoToEntityMapper().apply(player.get(), request);
            playerService.update(player.get());
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
