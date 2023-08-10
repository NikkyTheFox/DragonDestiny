package pl.edu.pg.eti.dragondestiny.user.user.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * A structure containing only user's login and password.
 */
@Data
public class UserLogin {

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
