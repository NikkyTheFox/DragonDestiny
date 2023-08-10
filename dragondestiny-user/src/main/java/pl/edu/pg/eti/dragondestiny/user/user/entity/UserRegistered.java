package pl.edu.pg.eti.dragondestiny.user.user.entity;

import lombok.Data;

/**
 * A structure containing detailed users data.
 */
@Data
public class UserRegistered {

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

}
