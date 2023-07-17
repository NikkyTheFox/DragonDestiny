package pl.edu.pg.eti.game.playedgame.board.entity;

import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;
import org.springframework.data.annotation.Id;

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
     * Identifier of field.
     */
    @Id
    private Integer id;

    /**
     * List of fields in the board - one board => many fields.
     */
    private List<Field> fieldsOnBoard = new ArrayList<>();

    /**
     * Method for adding fields to the board during initialization of played game.
     *
     * @param field
     */
    public void addFieldsInBoard(Field field) {
        this.fieldsOnBoard.add(field);
    }

}
