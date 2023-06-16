package pl.edu.pg.eti.game.user.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    /**
     * User's login.
     */
    private String login;

    /**
     * User's password.
     */
    private String password;

}
