
package pl.edu.pg.eti.game.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.pg.eti.game.user.dto.LoginUserDTO;
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
     * Password encoder.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param userRepository
     * @param passwordEncoder
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
     * Returns user by Login and Password
     *
     * @param loginUserDTO
     * @return user found
     */
    public Optional<User> findUser(LoginUserDTO loginUserDTO) {
        return userRepository.findUserByLoginAndPassword(loginUserDTO.getLogin(), loginUserDTO.getPassword());
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
        User user1 = new User();
        user1.setLogin(user.getLogin());
        user1.setName(user.getName());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user1);
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
    public User update(User user){
        return userRepository.save(user);
    }

}
