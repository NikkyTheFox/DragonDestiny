package pl.edu.pg.eti.game.playedgame.field.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;

import java.util.List;

/**
 * Represents list of entities received from game engine.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FieldList {

    private List<Field> fieldList;

}
