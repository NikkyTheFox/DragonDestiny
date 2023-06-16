package pl.edu.pg.eti.game.user.controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.game.user.dto.LoginUserDTO;
import pl.edu.pg.eti.game.user.dto.UserDTO;
import pl.edu.pg.eti.game.user.dto.UserListDTO;
import pl.edu.pg.eti.game.user.entity.User;
import pl.edu.pg.eti.game.user.game.GameDTO;
import pl.edu.pg.eti.game.user.game.GameListDTO;
import pl.edu.pg.eti.game.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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
        System.out.println("PRINTING ALL USERS");
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
     * Get user by login + password.
     *
     * @param loginUserRequest
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<UserDTO> findUser(@RequestBody LoginUserDTO loginUserRequest) {
        Optional<User> user = userService.findUser(loginUserRequest);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        UserDTO userResponse = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok().body(userResponse);
    }

    /**
     * Get played games by login of player.
     *
     * @param login
     * @return
     */
    @GetMapping("/{login}/games")
    public ResponseEntity<GameListDTO> findGames(@PathVariable(name = "login") String login) {
        Optional<User> user = userService.findUser(login);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        GameListDTO gameListDTO = new GameListDTO(user.get().getPlayedGames());
        return ResponseEntity.ok().body(gameListDTO);
    }

    /**
     * Creating new user using registration form.
     *
     * @param userRequest
     * @return
     */
    @PutMapping("/registration")
    public ResponseEntity<UserDTO> createUser(@RequestBody User userRequest) {
        System.out.println("REGISTRATION");
        User userCreated = userService.save(userRequest);
        UserDTO userResponse = modelMapper.map(userCreated, UserDTO.class);
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
