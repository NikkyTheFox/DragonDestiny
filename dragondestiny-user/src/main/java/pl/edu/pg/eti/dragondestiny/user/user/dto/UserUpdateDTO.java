package pl.edu.pg.eti.dragondestiny.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO to update the User.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    /**
     * User's name.
     */
    private String name;

    /**
     * User's password.
     */
    private String password;

}
