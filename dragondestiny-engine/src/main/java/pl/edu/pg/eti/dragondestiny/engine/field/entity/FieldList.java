package pl.edu.pg.eti.dragondestiny.engine.field.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A structure containing list of fields.
 */
@Data
@AllArgsConstructor
public class FieldList {

    /**
     * A list of fields.
     */
    private List<Field> fieldList;

}
