package pl.edu.pg.eti.dragondestiny.playedgame.field.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Structure containing a list of fields.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FieldList {

    /**
     * A list of fields.
     */
    private List<Field> fieldList = new ArrayList<>();

}
