package pl.edu.pg.eti.dragondestiny.user.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class UserRegisteredDTO {

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
