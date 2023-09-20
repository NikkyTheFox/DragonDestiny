package pl.edu.pg.eti.dragondestiny.engine.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of Board instance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    /**
     * Unique identifier of the board.
     */
    private Integer id;

    /**
     * Size of board (number of fields) in x dimension.
     */
    private Integer xsize;

    /**
     * Size of board (number of fields) in y dimension.
     */
    private Integer ysize;
}
