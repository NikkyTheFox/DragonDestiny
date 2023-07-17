package pl.edu.pg.eti.game.user.game.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.edu.pg.eti.game.user.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "games")
public class Game {
    @Id
    private String id;

    @ManyToMany
    @JoinTable(
            name = "users_games",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> userList = new ArrayList<>();

}
