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
    @Singular
    private List<String> players;

    public static Function<Collection<Player>,GetPlayersResponse> entityToDtoMapper(){
        return players -> {
            GetPlayersResponseBuilder responseBuilder = GetPlayersResponse.builder();
            players.stream()
                    .map(Player::getName).forEach(responseBuilder::player);
            return responseBuilder.build();
        };
    }
}
