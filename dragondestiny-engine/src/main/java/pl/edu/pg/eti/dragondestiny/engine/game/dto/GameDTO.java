package pl.edu.pg.eti.dragondestiny.engine.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of Game instance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    /**
     * Unique identifier of game.
     */
    private Integer id;

}
