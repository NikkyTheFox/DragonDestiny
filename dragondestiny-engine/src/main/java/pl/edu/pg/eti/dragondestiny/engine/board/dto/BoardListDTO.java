package pl.edu.pg.eti.dragondestiny.engine.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class BoardListDTO {

    /**
     * A list of boards.
     */
    private List<BoardDTO> boardList;

}
