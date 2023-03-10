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
    @Singular
    private List<String> playedCharacters;

    public static Function<Collection<PlayedCharacter>,GetPlayedCharactersResponse> entityToDtoMapper(){
        return playedCharacters -> {
            GetPlayedCharactersResponseBuilder responseBuilder = GetPlayedCharactersResponse.builder();
            playedCharacters.stream()
                    .map(PlayedCharacter::getName).forEach(responseBuilder::playedCharacter);
            return responseBuilder.build();
        };
    }
}
