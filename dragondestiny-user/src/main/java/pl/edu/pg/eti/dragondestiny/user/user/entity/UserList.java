package pl.edu.pg.eti.dragondestiny.user.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserDTO;

import java.util.List;

/**
 * An object storing list of users.
 */
@AllArgsConstructor
@Setter
@Getter
public class UserList {

    /**
     * A list of users.
     */
    private List<User> userList;

}
