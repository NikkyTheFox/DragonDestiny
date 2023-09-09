package pl.edu.pg.eti.dragondestiny.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.user.game.dto.GameDTO;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
