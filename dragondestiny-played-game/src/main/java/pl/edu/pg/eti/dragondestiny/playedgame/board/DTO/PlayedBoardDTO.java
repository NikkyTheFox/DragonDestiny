package pl.edu.pg.eti.dragondestiny.playedgame.board.DTO;

import lombok.Data;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldDTO;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class PlayedBoardDTO {

    /**
     * Identifier of the board
     */
    private Integer id;

    /**
     * List of fields in the board - one board => many fields.
     */
    private List<FieldDTO> fieldsOnBoard;
}
