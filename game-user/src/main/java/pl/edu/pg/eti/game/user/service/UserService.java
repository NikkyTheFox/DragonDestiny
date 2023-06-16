
package pl.edu.pg.eti.game.user.service;

import pl.edu.pg.eti.game.user.dto.UserLoginDTO;
import pl.edu.pg.eti.game.user.entity.User;
import pl.edu.pg.eti.game.user.repository.UserRepository;
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
        if (!updatedUser.getPlayedGames().isEmpty())
            newUser.setPlayedGames(updatedUser.getPlayedGames());
        else
            newUser.setPlayedGames(updatedUser.getPlayedGames());

        userRepository.delete(user);
        return userRepository.save(newUser);
    }

}
