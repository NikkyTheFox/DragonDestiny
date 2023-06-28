package pl.edu.pg.eti.game.user.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLoginDTO {

    /**
     * User's login.
     */
    @NotEmpty(message = "Login cannot be empty")
    private String login;

    /**
     * User's password.
     */
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
