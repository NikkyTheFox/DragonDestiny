package pl.edu.pg.eti.game.engine.character.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CharacterListDTO {

    private List<CharacterDTO> characterList;

}
