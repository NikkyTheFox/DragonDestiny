package pl.edu.pg.eti.dragondestiny.user.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object of List Game DTOs.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameListDTO {

    /**
     * A list of games.
     */
    private List<GameDTO> gameList = new ArrayList<>();

}
