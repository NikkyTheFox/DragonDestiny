package pl.edu.pg.eti.game.user.controller;

import pl.edu.pg.eti.game.user.dto.UserDTO;
import pl.edu.pg.eti.game.user.dto.UserListDTO;
import pl.edu.pg.eti.game.user.entity.User;
import pl.edu.pg.eti.game.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * UserService used to communicate with service layer that will communicate with database repository.
     */
    private final UserService userService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param userService
     * @param modelMapper
     */
    @Autowired
    UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all users.
     *
     * @return list of UserDTOs
     */
    @GetMapping()
    public ResponseEntity<UserListDTO> getUsers() {
        return ResponseEntity.ok().body(new UserListDTO(userService.findUsers().stream()
                .map(character -> modelMapper.map(character, UserDTO.class))
                .collect(Collectors.toList())));
    }

    /**
     * Retrieve user by login.
     *
     * @param login - identifier of user
     * @return ResponseEntity containing UserDTO
     */
    @GetMapping("/{login}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "login") String login) {
        Optional<User> user = userService.findUser(login);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        UserDTO userResponse = modelMapper.map(user.get(), UserDTO.class);
        return ResponseEntity.ok().body(userResponse);
    }

    /**
     * Creating or updating user.
     *
     * @param login
     * @param userRequest
     * @return
     */
    @PutMapping("/{login}")
    public ResponseEntity<UserDTO> putUser(@PathVariable(name = "login") String login, @RequestBody User userRequest) {
        Optional<User> user = userService.findUser(login);
        if (user.isEmpty()) { // create
            userRequest.setLogin(login);
            User userCreated = userService.save(userRequest);
            UserDTO userResponse = modelMapper.map(userCreated, UserDTO.class);
            return ResponseEntity.ok().body(userResponse);
        }
        User userUpdated = userService.save(userRequest); // update
        UserDTO userResponse = modelMapper.map(userUpdated, UserDTO.class);
        return ResponseEntity.ok().body(userResponse);
    }

    /**
     * Delete user by login.
     *
     * @param login
     */
    @DeleteMapping("/{login}")
    public void deleteUser(@PathVariable(name = "login") String login) {
        userService.deleteUser(login);
    }

}