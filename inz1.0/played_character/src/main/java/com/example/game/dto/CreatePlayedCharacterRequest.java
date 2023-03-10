package com.example.game.dto;

import com.example.game.entities.character.PlayedCharacter;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreatePlayedCharacterRequest {
    private String name;

    public static Function<CreatePlayedCharacterRequest, PlayedCharacter> dtoToEntityMapper(){
        return createPlayedCharacterRequest -> PlayedCharacter.builder()
                .name(createPlayedCharacterRequest.getName())
                .build();
    }
}
