package pl.edu.pg.eti.game.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class UserListDTO {

    private List<UserDTO> userList;

}
