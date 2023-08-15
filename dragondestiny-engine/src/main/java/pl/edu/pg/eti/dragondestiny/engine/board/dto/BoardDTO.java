package pl.edu.pg.eti.dragondestiny.engine.board.dto;

import lombok.Data;

/**
 * Data Transfer Object of Board instance.
 */
@Data
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
