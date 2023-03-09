
package com.example.player.dto;


import com.example.player.entities.Player;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPlayerResponse {
    private Integer id;
    private String name;

    public static Function<Player,GetPlayerResponse> entityToDtoMapper(){
        return players -> GetPlayerResponse.builder()
                .id(players.getId())
                .name(players.getName())
                .build();
    }


}
