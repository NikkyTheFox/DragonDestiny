package pl.edu.pg.eti.game.user.user.controller;

import jakarta.validation.Valid;
import org.springframework.web.ErrorResponse;
import pl.edu.pg.eti.game.user.user.dto.UserDTO;
import pl.edu.pg.eti.game.user.user.dto.UserListDTO;
import pl.edu.pg.eti.game.user.user.dto.UserLoginDTO;
import pl.edu.pg.eti.game.user.user.dto.UserRegisterDTO;
import pl.edu.pg.eti.game.user.user.entity.User;
import pl.edu.pg.eti.game.user.game.entity.Game;
import pl.edu.pg.eti.game.user.game.dto.GameDTO;
import pl.edu.pg.eti.game.user.game.dto.GameListDTO;
import pl.edu.pg.eti.game.user.game.service.GameService;
import pl.edu.pg.eti.game.user.user.service.UserService;
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
     * @param userService
     * @param gameService
     * @param modelMapper
     */
    @Autowired
    UserController(UserService userService, ModelMapper modelMapper, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
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
     * Get user by login + password.
     *
     * @param loginUserRequest
     * @return
     */
    @PutMapping("/login")
    public ResponseEntity<UserDTO> findUser(@Valid @RequestBody UserLoginDTO loginUserRequest) {
        Optional<User> user = userService.findUser(loginUserRequest);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        UserDTO userResponse = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok().body(userResponse);
    }

    /**
     * Creating new user using registration form.
     *
     * @param userRequest
     * @return
     */
    @PutMapping("/register")
    public ResponseEntity<UserRegisterDTO> createUser(@Valid @RequestBody UserRegisterDTO userRequest) {

        Optional<User> userFound = userService.findUser(userRequest.getLogin());
        if (userFound.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        User userCreated = userService.save(modelMapper.map(userRequest, User.class));
        UserRegisterDTO userResponse = modelMapper.map(userCreated, UserRegisterDTO.class);
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
        List<GameDTO> cardDTOList = gameService.findGames(user.get()).stream()
                .map(card -> modelMapper.map(card, GameDTO.class))
                .collect(Collectors.toList());
        GameListDTO gameListDTO = new GameListDTO(cardDTOList);
        return ResponseEntity.ok().body(gameListDTO);
    }

    /**
     * Update user.
     *
     * @param login
     * @param userRequest
     * @return
     */
    @PutMapping("/{login}/edit")
    public ResponseEntity<UserDTO> putUser(@PathVariable(name = "login") String login, @RequestBody UserRegisterDTO userRequest) {
        Optional<User> user = userService.findUser(login);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User userUpdated = userService.update(modelMapper.map(userRequest, User.class), user.get()); // update
        UserDTO userResponse = modelMapper.map(userUpdated, UserDTO.class);
        return ResponseEntity.ok().body(userResponse);
    }

    @PutMapping("/{login}/addGame/{gameId}")
    public ResponseEntity<UserDTO> putUser(@PathVariable(name = "login") String login, @PathVariable(name = "gameId") String gameId) {
        System.out.println("ADDING GAME " + gameId);
        Optional<User> user = userService.findUser(login);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty()) {
            System.out.println("no game found");
            Game createdGame = new Game();
            createdGame.setId(gameId);
            createdGame.getUserList().add(user.get());
            gameService.save(createdGame);
            user.get().getPlayedGames().add(createdGame);

        } else {
            System.out.println("game found");
            game.get().getUserList().add(user.get());
            user.get().getPlayedGames().add(game.get());
            gameService.save(game.get());
        }

        User userUpdated = userService.save(user.get());
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