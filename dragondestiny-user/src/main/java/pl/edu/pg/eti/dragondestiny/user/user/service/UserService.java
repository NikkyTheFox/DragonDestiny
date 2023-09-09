
package pl.edu.pg.eti.dragondestiny.user.user.service;

import org.modelmapper.ModelMapper;
import pl.edu.pg.eti.dragondestiny.user.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.user.game.service.GameService;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserDTO;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserListDTO;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;
import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.user.user.entity.UserList;
import pl.edu.pg.eti.dragondestiny.user.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * User Service to communication with users' data saved in database.
 */
@Service
public class UserService {

    /**
     * JPA repository communicating with database.
     */
    private final UserRepository userRepository;

    /**
     * Game Service used to communicate with service layer that will communicate with database repository.
     */
    private final GameService gameService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param userRepository Repository with methods for data retrieval and manipulation.
     * @param gameService Service with methods of manipulation of game database data.
     */
    @Autowired
    public UserService(UserRepository userRepository, GameService gameService) {
        this.userRepository = userRepository;
        this.gameService = gameService;
    }

    /**
     * Returns user by Login.
     *
     * @param userLogin An identifier of a user.
     * @return A retrieved user.
     */
    public Optional<User> getUser(String userLogin) {

        return userRepository.findById(userLogin);
    }

    /**
     * Returns user by Login & Password combination.
     *
     * @param userLogin An identifier of a user to be retrieved.
     * @param password A password of a user to be retrieved.
     * @return A retrieved user.
     */
    public Optional<User> getUser(String userLogin, String password) {

        return userRepository.findUserByLoginAndPassword(userLogin, password);
    }

    /**
     * Retrieves all users.
     *
     * @return A structure containing list of users.
     */
    public UserList getUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            return new UserList();
        }
        return new UserList(userList);
    }

    /**
     * Adds new user to the database,
     *
     * @param user A user to be persisted in database.
     */
    public void save(User user) {

        userRepository.save(user);
    }

    /**
     * Deletes user from database,
     *
     * @param userLogin An identifier of a user to be deleted.
     */
    public Optional<Boolean> deleteUser(String userLogin) {
        Optional<User> user = getUser(userLogin);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        userRepository.deleteById(userLogin);
        return Optional.of(Boolean.TRUE);
    }

    /**
     * Adds new user to the database unless already one exists with the same login.
     *
     * @param userRegistered A structure containing user's data.
     * @return Newly created user.
     */
    public Optional<User> registerUser(User userRegistered) {
        Optional<User> user = getUser(userRegistered.getLogin());
        if (user.isPresent()) {
            return Optional.empty();
        }
        save(userRegistered);
        return Optional.of(userRegistered);
    }

    /**
     * Update user's information.
     *
     * @param updatedUser A structure with new data for user's details.
     * @param userToUpdate A user to be updated with new data.
     */
    private void update(User updatedUser, User userToUpdate) {
        if(updatedUser.getLogin() != null){
            // update login to new value
            userToUpdate.setLogin(updatedUser.getLogin());
        }

        if(updatedUser.getName() != null){
            // update name to new value
            userToUpdate.setName(updatedUser.getName());
        }
        if(updatedUser.getPassword() != null){
            // update password to new value
            userToUpdate.setPassword(updatedUser.getPassword());
        }
        userRepository.save(userToUpdate);
    }

    /**
     * Updates provided user's details.
     *
     * @param userLogin An identifier of a user to be updated.
     * @param updatedUser A structure with new data.
     * @return An updated user.
     */
    public Optional<User> updateUser(String userLogin, User updatedUser) {
        Optional<User> userToUpdate = getUser(userLogin);
        if (userToUpdate.isEmpty()) {
            return Optional.empty();
        }
        update(updatedUser, userToUpdate.get());
        return userToUpdate;
    }

    /**
     * Retrieves games for a user specified by login.
     *
     * @param userLogin An identifier of a user.
     * @return A structure containing list of games.
     */
    public Optional<GameList> findGames(String userLogin){
        Optional<User> user = getUser(userLogin);
        if (user.isEmpty())
            return Optional.empty();
        return gameService.getGames(user.get());
    }

    /**
     * Adds game to the user's list of games. Also adds user to game's list of users.
     *
     * @param userLogin An identifier of a user.
     * @param gameId An identifier of a game.
     * @return Updated user.
     */
    public Optional<User> addGameToUser(String userLogin, String gameId) {
        Optional<User> user = getUser(userLogin);
        Optional<Game> game = gameService.getGame(gameId);
        if (user.isEmpty() || game.isEmpty()) {
            return Optional.empty();
        }
        user.get().addGame(game.get());
        game.get().addUser(user.get());
        gameService.save(game.get());
        save(user.get());
        return user;
    }

    /**
     * Converts UserList into UserListDTO.
     *
     * @param modelMapper Mapper allowing conversion,
     * @param userList A structure containing list of users.
     * @return A DTO.
     */
    public UserListDTO convertUserListToDTO(ModelMapper modelMapper, UserList userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.getUserList().forEach(user -> {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTOList.add(userDTO);
        });
        return new UserListDTO(userDTOList);
    }
}
