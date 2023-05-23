
package com.example.user.controller;


import com.example.user.dto.UserDTO;
import com.example.user.entities.User;
import com.example.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * @return list of UserDTOs
     */
    @GetMapping()
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(character -> modelMapper.map(character, UserDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve user by login.
     * @param login - identifier of user
     * @return ResponseEntity containing UserDTO
     */
    @GetMapping("/{login}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "login") String login) {
        User user = userService.findByLogin(login);
        UserDTO userResponse = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok().body(userResponse);
    }

    /**
     * ?????
     * @param login
     * @param userRequest
     * @return
     */
    @PutMapping("/{login}")
    public ResponseEntity<UserDTO> createOrUpdateUser(@PathVariable(name = "login") String login, @RequestBody User userRequest) {
        User user = null;
        user = userService.findByLogin(login);
        if (user != null) { // update
            User userUpdated = userService.save(userRequest);
            UserDTO userResponse = modelMapper.map(userUpdated, UserDTO.class);
            return ResponseEntity.ok().body(userResponse);
        }
        else { // create
            userRequest.setLogin(login);
            User userCreated = userService.save(userRequest);
            UserDTO userResponse = modelMapper.map(userCreated, UserDTO.class);
            return ResponseEntity.ok().body(userResponse);
        }
    }

    /**
     * Delete user by login.
     * @param login
     */
    @DeleteMapping("/{login}")
    public void deleteUser(@PathVariable(name = "login") String login) {
        userService.deleteById(login);
    }

}
