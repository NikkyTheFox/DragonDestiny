package com.example.player.dto;

import lombok.*;
import com.example.player.entities.Player;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdatePlayerRequest {
    private String name;
    public static BiFunction<Player, UpdatePlayerRequest, Player> dtoToEntityMapper(){
        return (players,updatePlayerRequest)->{
            players.setName(updatePlayerRequest.getName());
            return players;
        };
    }
}
