package pl.edu.pg.eti.dragondestiny.playedgame.field.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class FieldOptionListDTO {

    /**
     * A list of actions possible to take while standing on a given field
     */
    private List<FieldOptionDTO> possibleOptions;
}
