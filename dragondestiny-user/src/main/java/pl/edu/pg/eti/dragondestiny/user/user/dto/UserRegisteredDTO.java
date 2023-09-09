package pl.edu.pg.eti.dragondestiny.user.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
