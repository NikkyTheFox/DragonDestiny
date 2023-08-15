package pl.edu.pg.eti.dragondestiny.user.user.entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * A structure containing detailed users data.
 */
@Data
public class UserRegistered {

    /**
     * User's login.
     */
    @NotEmpty(message = "Login cannot be empty")
    @Column(unique = true)
    private String login;

    /**
     * User's name.
     */
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    /**
     * User's password.
     */
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
