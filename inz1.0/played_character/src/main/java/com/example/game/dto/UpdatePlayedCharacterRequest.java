package com.example.game.dto;

import com.example.game.entities.character.PlayedCharacter;
import lombok.*;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdatePlayedCharacterRequest {
    private String name;

    public static BiFunction<PlayedCharacter, UpdatePlayedCharacterRequest, PlayedCharacter> dtoToEntityMapper(){
        return (playedCharacter, updatePlayedCharacterRequest) -> {
            playedCharacter.setName(updatePlayedCharacterRequest.getName());
            return playedCharacter;
        };
    }

}
