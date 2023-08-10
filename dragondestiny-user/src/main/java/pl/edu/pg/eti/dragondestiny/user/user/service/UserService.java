
package pl.edu.pg.eti.dragondestiny.user.user.service;

import pl.edu.pg.eti.dragondestiny.user.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.user.game.service.GameService;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;
import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.user.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<User> findUser(String userLogin) {

        return userRepository.findById(userLogin);
    }

    /**
     * Returns user by Login & Password combination.
     *
     * @param userLogin An identifier of a user to be retrieved.
     * @param password A password of a user to be retrieved.
     * @return A retrieved user.
     */
    public Optional<User> findUser(String userLogin, String password) {

        return userRepository.findUserByLoginAndPassword(userLogin, password);
    }

    /**
     * Returns all users found.
     *
     * @return A list of retrieved users.
     */
    public List<User> findUsers() {

        return userRepository.findAll();
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
        Optional<User> user = findUser(userLogin);
        if(user.isEmpty()){
            return Optional.empty();
        }
        userRepository.deleteById(userLogin);
        return Optional.of(Boolean.TRUE);
    }

    /**
     * Adds new user to the database.
     *
     * @param userRegistered A structure containing user's data.
     * @return Newly created user.
     */
    public Optional<User> registerUser(User userRegistered){
        Optional<User> user = findUser(userRegistered.getLogin());
        if(user.isPresent()){
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
    public void update(User updatedUser, User userToUpdate) {
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
        /*if (!updatedUser.getPlayedGames().isEmpty()) {
            userToUpdate.getPlayedGames().addAll(updatedUser.getPlayedGames());
            System.out.println("GAMES 12 ");
            userToUpdate.getPlayedGames().forEach(System.out::println);
        }
        else {
            userToUpdate.getPlayedGames().addAll(updatedUser.getPlayedGames());
            System.out.println("GAMES 22 ");
            userToUpdate.getPlayedGames().forEach(System.out::println);
        }
        System.out.println("GAMES AFTER ");
        userToUpdate.getPlayedGames().forEach(System.out::println);
        */
        userRepository.save(userToUpdate);
    }

    /**
     * Updates provided user's details.
     *
     * @param userLogin An identifier of a user to be updated.
     * @param updatedUser A structure with new data.
     * @return An updated user.
     */
    public Optional<User> updateUser(String userLogin, User updatedUser){
        Optional<User> userToUpdate = findUser(userLogin);
        if(userToUpdate.isEmpty()){
            return Optional.empty();
        }
        update(updatedUser, userToUpdate.get());
        return userToUpdate;
    }

    // ???????????????
    public User updateGamesList(User user, String gameId) {

        Game game = new Game();
        game.setId(gameId);
        game.getUserList().add(user);
        user.getPlayedGames().add(game);

        for (Game g : user.getPlayedGames())
            System.out.println(g.getId());

        return userRepository.save(user);
    }

    /**
     * Retrieves games for a user specified by login.
     *
     * @param userLogin An identifier of a user.
     * @return A structure containing list of games.
     */
    public Optional<GameList> findGames(String userLogin){
        Optional<User> user = findUser(userLogin);
        return user.map(value -> new GameList(gameService.findGames(value)));
    }

    /**
     * Adds game to the user's list of games. Also adds user to game's list of users.
     *
     * @param userLogin An identifier of a user.
     * @param gameId An identifier of a game.
     * @return Updated user.
     */
    public Optional<User> addGameToUser(String userLogin, String gameId){
        Optional<User> user = findUser(userLogin);
        Optional<Game> game = gameService.findGame(gameId);
        if(user.isEmpty() || game.isEmpty()){
            return Optional.empty();
        }
        user.get().addGame(game.get());
        game.get().addUser(user.get());
        gameService.save(game.get());
        save(user.get());
        return user;
    }
}
