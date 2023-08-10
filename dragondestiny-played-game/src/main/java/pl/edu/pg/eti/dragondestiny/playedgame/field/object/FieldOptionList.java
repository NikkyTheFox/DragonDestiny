package pl.edu.pg.eti.dragondestiny.playedgame.field.object;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Structure containing a list of actions possible to do while standing on a field.
 */
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class FieldOptionList {

    /**
     * A list of actions possible to take while standing on a given field
     */
    private List<FieldOption> possibleOptions = new ArrayList<>();

}
