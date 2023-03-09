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
public class GetPlayedCharacterResponse {
    private Integer id;
    private String name;
    public static Function<PlayedCharacter, GetPlayedCharacterResponse> entityToDtoMapper(){
        return characters -> GetPlayedCharacterResponse.builder()
                .id(characters.getId())
                .name(characters.getName())
                .build();
    }


}
