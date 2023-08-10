package pl.edu.pg.eti.dragondestiny.playedgame.field.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class FieldListDTO {

    /**
     * A list of fields.
     */
    private List<FieldDTO> fieldDTOList;
}
