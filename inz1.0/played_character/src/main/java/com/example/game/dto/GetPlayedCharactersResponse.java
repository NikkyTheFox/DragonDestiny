package com.example.game.dto;

import com.example.game.entities.character.PlayedCharacter;
import com.netflix.eventbus.spi.Subscribe;
import lombok.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPlayedCharactersResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class PlayedCharacter{
        private Integer id;
        private String name;
    }
    @Singular
    private List<PlayedCharacter> playedCharacters;

    public static Function<Collection<com.example.game.entities.character.PlayedCharacter>,GetPlayedCharactersResponse> entityToDtoMapper(){
        return playedCharacters -> {
            GetPlayedCharactersResponseBuilder response = GetPlayedCharactersResponse.builder();
            playedCharacters.stream()
                    .map(playedCharacter -> PlayedCharacter.builder()
                            .id(playedCharacter.getId())
                            .name(playedCharacter.getName())
                            .build())
                    .forEach(response::playedCharacter);
            return response.build();
        };
    }
}
