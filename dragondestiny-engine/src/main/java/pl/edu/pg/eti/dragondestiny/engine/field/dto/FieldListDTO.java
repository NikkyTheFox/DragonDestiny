package pl.edu.pg.eti.dragondestiny.engine.field.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object of List of Field DTOs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldListDTO {

    /**
     * A list of fields.
     */
    private List<FieldDTO> fieldList;

}
