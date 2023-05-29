
package com.example.user.service;

import com.example.user.entities.User;
import com.example.user.repository.UserRepository;
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
     * @param userRepository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns user by Login. If no user found, throws exception.
     * @param login
     * @return user found
     */
    public User findByLogin(String login) {
        Optional<User> user = userRepository.findById(login);
        if (user.isPresent()) {
            return user.get();
        } //else throw new RuntimeException("No user found");
        else return null;
    }

    /**
     * Returns all users found.
     * @return list of users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Adds new user to the database,
     * @param user - user request
     * @return saved user
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes user from database,
     * @param login - user login
     */
    public void deleteById(String login) {
        userRepository.deleteById(login);
    }

    /**
     * Update user's information.
     * @param user - user request to update
     * @return updated user
     */
    public User update(User user){
        return userRepository.save(user);
    }

}
