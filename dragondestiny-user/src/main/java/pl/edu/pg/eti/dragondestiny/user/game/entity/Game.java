package pl.edu.pg.eti.dragondestiny.user.game.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Simplified entity representing a game by its ID and list of users (players).
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "games")
public class Game {

    /**
     * Identifier of a game.
     */
    @Id
    private String id;

    /**
     * A list of users that participate in the game.
     */
    @ManyToMany
    @JoinTable(
            name = "users_games",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> userList = new ArrayList<>();

    /**
     * Adds a given user to game's list of users.
     *
     * @param user A user to be added.
     */
    public void addUser(User user){

        userList.add(user);
    }
}
