package pl.edu.pg.eti.dragondestiny.engine.board.dto;

import lombok.Data;

/**
 * DTO allows to hide implementation from the client.
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
    private Integer xSize;

    /**
     * Size of board (number of fields) in y dimension.
     */
    private Integer ySize;

}
