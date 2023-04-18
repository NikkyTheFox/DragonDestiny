package com.example.player.dto;

import lombok.*;
import com.example.player.entities.Player;

import java.util.Collection;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPlayersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Player{
        private Integer id;
        private String name;
    }

    @Singular
    private List<Player> players;
    
    public static Function<Collection<com.example.player.entities.Player>,GetPlayersResponse> entityToDtoMapper(){
        return players -> {
            GetPlayersResponseBuilder response =GetPlayersResponse.builder();
            players.stream()
                    .map(player -> Player.builder()
                            .id(player.getId())
                            .name(player.getName())
                            .build())
                    .forEach(response::player);
            return response.build();
        };
    }
}
