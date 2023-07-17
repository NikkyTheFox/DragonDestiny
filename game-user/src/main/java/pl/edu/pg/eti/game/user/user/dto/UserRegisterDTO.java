package pl.edu.pg.eti.game.user.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegisterDTO {

    /**
     * User's login.
     */
    @NotEmpty
    private String login;

    /**
     * User's name.
     */
   @NotEmpty
    private String name;

    /**
     * User's password.
     */
    @NotEmpty
    private String password;

}
