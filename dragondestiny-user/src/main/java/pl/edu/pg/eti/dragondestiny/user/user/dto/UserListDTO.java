package pl.edu.pg.eti.dragondestiny.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserListDTO {

    /**
     * A list of users.
     */
    private List<UserDTO> userList = new ArrayList<>();

}
