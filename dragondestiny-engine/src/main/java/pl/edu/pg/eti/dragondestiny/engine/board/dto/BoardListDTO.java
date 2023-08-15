package pl.edu.pg.eti.dragondestiny.engine.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * Data Transfer Object of List of Board DTOs.
 */
@Data
@AllArgsConstructor
public class BoardListDTO {

    /**
     * A list of boards.
     */
    private List<BoardDTO> boardList;

}
