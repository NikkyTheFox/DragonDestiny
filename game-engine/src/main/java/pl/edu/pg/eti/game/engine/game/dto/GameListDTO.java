package pl.edu.pg.eti.game.engine.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GameListDTO {

    private List<GameDTO> gameList;

}
