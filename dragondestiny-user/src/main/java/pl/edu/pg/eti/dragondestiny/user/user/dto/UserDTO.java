package pl.edu.pg.eti.dragondestiny.user.user.dto;

import lombok.Data;
import pl.edu.pg.eti.dragondestiny.user.game.dto.GameDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
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
