package pl.edu.pg.eti.game.user.dto;

import jakarta.persistence.OneToMany;
import lombok.Data;
import pl.edu.pg.eti.game.user.game.GameDTO;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserRegisterDTO {

    /**
     * User's login.
     */
    private String login;

    /**
     * User's name.
     */
    private String name;

    /**
     * User's password.
     */
    private String password;

    /**
     * List of played games of that user.
     */
    @OneToMany
    private List<GameDTO> playedGames = new ArrayList<>();

}
