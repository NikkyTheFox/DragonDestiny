package pl.edu.pg.eti.dragondestiny.playedgame.character.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class CharacterListDTO {

    /**
     * A list of characters.
     */
    private List<CharacterDTO> characterDTOList;
}
