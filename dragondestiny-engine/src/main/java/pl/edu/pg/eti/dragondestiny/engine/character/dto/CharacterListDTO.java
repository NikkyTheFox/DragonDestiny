package pl.edu.pg.eti.dragondestiny.engine.character.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object of List of Character DTOs.
 */
@Data
@AllArgsConstructor
public class CharacterListDTO {

    /**
     * A list of characters.
     */
    private List<CharacterDTO> characterList;

}
