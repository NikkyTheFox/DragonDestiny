package pl.edu.pg.eti.dragondestiny.user.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A structure containing only user's login and password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
