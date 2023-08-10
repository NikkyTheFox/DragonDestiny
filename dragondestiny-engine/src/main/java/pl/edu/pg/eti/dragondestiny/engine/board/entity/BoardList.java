package pl.edu.pg.eti.dragondestiny.engine.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A structure containing list of boards.
 */
@Data
@AllArgsConstructor
public class BoardList {

    /**
     * A list of boards.
     */
    private List<Board> boardList;

}
