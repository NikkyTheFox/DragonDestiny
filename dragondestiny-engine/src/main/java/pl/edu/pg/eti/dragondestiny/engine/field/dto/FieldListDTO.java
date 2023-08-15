package pl.edu.pg.eti.dragondestiny.engine.field.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object of List of Field DTOs.
 */
@Data
@AllArgsConstructor
public class FieldListDTO {

    /**
     * A list of fields.
     */
    private List<FieldDTO> fieldList;

}
