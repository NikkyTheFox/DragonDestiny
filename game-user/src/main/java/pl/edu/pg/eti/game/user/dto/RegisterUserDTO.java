package pl.edu.pg.eti.game.user.dto;

import lombok.Data;

@Data
public class RegisterUserDTO {

    /**
     * User's login.
     */
    private String login;

    /**
     * User's password.
     */
    private String password;

    /**
     * User's name.
     */
    private String name;
}
