package pl.edu.pg.eti.game.engine.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class BoardListDTO {

    private List<BoardDTO> boardList;

}
