package pl.edu.pg.eti.dragondestiny.user.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of Game instance for User.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {

    /**
     * Identifier of a game.
     */
    private String id;

}
