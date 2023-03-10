package com.example.player.dto;

import lombok.*;
import com.example.player.entities.Player;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreatePlayerRequest {
    private String name;

    public static Function<CreatePlayerRequest, Player> dtoToEntityMapper() {
        return createPlayerRequest -> Player.builder()
                .name(createPlayerRequest.getName())
                .build();
    }
}
