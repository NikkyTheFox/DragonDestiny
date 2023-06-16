
package pl.edu.pg.eti.game.user.user.service;

import pl.edu.pg.eti.game.user.user.dto.UserLoginDTO;
import pl.edu.pg.eti.game.user.user.entity.User;
import pl.edu.pg.eti.game.user.game.entity.Game;
import pl.edu.pg.eti.game.user.user.repository.UserRepository;
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
     * Autowired constructor - beans are injected automatically.
     *
     * @param userRepository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns user by Login
     *
     * @param login
     * @return user found
     */
    public Optional<User> findUser(String login) {
        return userRepository.findById(login);
    }

    /**
     * Returns user by Login + Password combination.
     *
     * @param userLoginDTO
     * @return user found
     */
    public Optional<User> findUser(UserLoginDTO userLoginDTO) {
        return userRepository.findUserByLoginAndPassword(userLoginDTO.getLogin(), userLoginDTO.getPassword());
    }

    /**
     * Returns all users found.
     *
     * @return list of users
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * Adds new user to the database,
     *
     * @param user - user request
     * @return saved user
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes user from database,
     *
     * @param login - user login
     */
    public void deleteUser(String login) {
        userRepository.deleteById(login);
    }

    /**
     * Update user's information.
     *
     * @param user - user request to update
     * @return updated user
     */
    public User update(User updatedUser, User user) {
        User newUser = new User();
        if (updatedUser.getLogin() != null)
            newUser.setLogin(updatedUser.getLogin());
        else
            newUser.setLogin(user.getLogin());
        if (updatedUser.getName() != null)
            newUser.setName(updatedUser.getName());
        else
            newUser.setName(user.getName());
        if (updatedUser.getPassword() != null)
            newUser.setPassword(updatedUser.getPassword());
        else
            newUser.setPassword(updatedUser.getPassword());
        System.out.println("GAMES BEFORE ");
        updatedUser.getPlayedGames().forEach(System.out::println);
        if (!updatedUser.getPlayedGames().isEmpty()) {
            newUser.getPlayedGames().addAll(updatedUser.getPlayedGames());
            System.out.println("GAMES 12 ");
            newUser.getPlayedGames().forEach(System.out::println);
        }
        else {
            newUser.getPlayedGames().addAll(updatedUser.getPlayedGames());
            System.out.println("GAMES 22 ");
            newUser.getPlayedGames().forEach(System.out::println);
        }
        System.out.println("GAMES AFTER ");
        newUser.getPlayedGames().forEach(System.out::println);

        userRepository.delete(user);
        return userRepository.save(newUser);
    }

    public User updateGamesList(User user, String gameId) {

        Game game = new Game();
        game.setId(gameId);
        game.getUserList().add(user);
        user.getPlayedGames().add(game);

        for (Game g : user.getPlayedGames())
            System.out.println(g.getId());

        return userRepository.save(user);
    }

}
