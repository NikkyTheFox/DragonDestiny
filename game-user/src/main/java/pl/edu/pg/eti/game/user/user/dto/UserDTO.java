package pl.edu.pg.eti.game.user.user.dto;

import lombok.Data;
import pl.edu.pg.eti.game.user.game.dto.GameDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    /**
     * User's login.
     */
    private String login;

    /**
     * User's name.
     */
    private String name;

    /**
     * List of played games of that user.
     */
    private List<GameDTO> playedGames = new ArrayList<>();

}
