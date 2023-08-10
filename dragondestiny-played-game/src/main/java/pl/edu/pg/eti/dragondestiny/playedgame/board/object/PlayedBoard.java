package pl.edu.pg.eti.dragondestiny.playedgame.board.object;

import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Corresponds to board used in ONE played game.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayedBoard implements Serializable {

    /**
     * Identifier of board.
     */
    private Integer id;

    /**
     * List of fields in the board - one board => many fields.
     */
    private List<Field> fieldsOnBoard = new ArrayList<>();

    /**
     * Adds specified field to the board.
     *
     * @param field A field to be added to the board.
     */
    public void addFieldsInBoard(Field field) {

        this.fieldsOnBoard.add(field);
    }

}
