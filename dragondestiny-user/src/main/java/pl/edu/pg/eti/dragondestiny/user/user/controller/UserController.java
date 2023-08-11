package pl.edu.pg.eti.dragondestiny.user.user.controller;

import jakarta.validation.Valid;
import pl.edu.pg.eti.dragondestiny.user.game.dto.GameListDTO;
import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.user.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.user.game.service.GameService;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserDTO;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserListDTO;
import pl.edu.pg.eti.dragondestiny.user.user.entity.UserList;
import pl.edu.pg.eti.dragondestiny.user.user.entity.UserLogin;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserRegisteredDTO;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;
import pl.edu.pg.eti.dragondestiny.user.user.entity.UserRegistered;
import pl.edu.pg.eti.dragondestiny.user.user.service.UserService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents REST controller, allows to handle requests to get users' data.
 */
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
     * GameService used to communicate with service layer that will communicate with database repository.
     */
    private final GameService gameService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param userService A service to communicate with database of users.
     * @param gameService A service to communicate with database of games.
     * @param modelMapper A mapper used to transform objects to DTOs.
     */
    @Autowired
    UserController(UserService userService, ModelMapper modelMapper, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves all users.
     *
     * @return A structure containing a list of users.
     */
    @GetMapping()
    public ResponseEntity<UserListDTO> getUsers() {
        Optional<UserList> userList = userService.getUsers();
        return userList.map(list -> ResponseEntity.ok().body(userService.convertUserListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves user by login.
     *
     * @param userLogin An identifier of a user to be retrieved.
     * @return A retrieved user.
     */
    @GetMapping("/{login}")
    public ResponseEntity<UserDTO> getUserByLogin(@PathVariable(name = "login") String userLogin) {
        Optional<User> user = userService.findUser(userLogin);
        return user.map(value -> ResponseEntity.ok().body(modelMapper.map(value, UserDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Gets user by login & password combination.
     *
     * @param loginUserRequest A structure containing login and password.
     * @return A retrieved user.
     */
    @PutMapping("/login")
    public ResponseEntity<UserDTO> findUserByLoginAndPassword(@Valid @RequestBody UserLogin loginUserRequest) {
        Optional<User> user = userService.findUser(loginUserRequest.getLogin(), loginUserRequest.getPassword());
        return user.map(u -> ResponseEntity.ok().body(modelMapper.map(u, UserDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates new user based on provided details.
     *
     * @param userToRegister A structure containing data of a user to be persisted in the database.
     * @return A registered user.
     */
    @PutMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserRegistered userToRegister) {
        Optional<User> user = userService.registerUser(modelMapper.map(userToRegister, User.class));
        return user.map(value -> ResponseEntity.ok().body(modelMapper.map(value, UserDTO.class)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Gets played games by login of a user.
     *
     * @param userLogin An identifier of a user whose games are to be retrieved.
     * @return A structure containing list of games.
     */
    @GetMapping("/{login}/games")
    public ResponseEntity<GameListDTO> findGames(@PathVariable(name = "login") String userLogin) {
        Optional<GameList> gameList = userService.findGames(userLogin);
        return gameList.map(list -> ResponseEntity.ok().body(gameService.convertGameListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates a specified user.
     *
     * @param userLogin An identifier of a user to be updated.
     * @param updatedUser A structure containing details to be updated.
     * @return An updated user.
     */
    @PutMapping("/{login}/edit")
    public ResponseEntity<UserDTO> updateUser(@PathVariable(name = "login") String userLogin, @RequestBody UserRegisteredDTO updatedUser) {
        Optional<User> user = userService.updateUser(userLogin, modelMapper.map(updatedUser, User.class));
        return user.map(u -> ResponseEntity.ok().body(modelMapper.map(u, UserDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Adds specified game to given user's list of games. In the same time adds specified user to game's user list.
     *
     * @param userLogin An identifier of a user to be linked together.
     * @param gameId An identifier of a game to be linked together.
     * @return An updated user.
     */
    @PutMapping("/{login}/addGame/{gameId}")
    public ResponseEntity<UserDTO> addGameToUser(@PathVariable(name = "login") String userLogin, @PathVariable(name = "gameId") String gameId) {
        Optional<User> user = userService.addGameToUser(userLogin, gameId);
        return user.map(u -> ResponseEntity.ok().body(modelMapper.map(u, UserDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes user by login.
     *
     * @param userLogin An identifier of a user to delete.
     * @return A confirmation of deletion.
     */
    @DeleteMapping("/{login}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable(name = "login") String userLogin) {
        Optional<Boolean> optionalBoolean = userService.deleteUser(userLogin);
        return optionalBoolean.map(aBoolean -> ResponseEntity.ok().body(aBoolean))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Adds game to the database.
     *
     * @param gameId An identifier of a game to be added.
     */
    @PutMapping("/games/{gameId}")
    public ResponseEntity<Boolean> addGame(@PathVariable(name = "gameId") String gameId){
        Game game = new Game();
        game.setId(gameId);
        gameService.save(game);
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

    /**
     * Retrieves all games from database.
     *
     * @return A structure containing list of games.
     */
    @GetMapping("/games")
    public ResponseEntity<GameListDTO> getGames(){
        Optional<GameList> gameList = gameService.getGames();
        return gameList.map(list -> ResponseEntity.ok().body(gameService.convertGameListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}