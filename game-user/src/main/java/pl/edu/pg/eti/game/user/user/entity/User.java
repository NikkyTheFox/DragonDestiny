package pl.edu.pg.eti.game.user.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.game.user.game.entity.Game;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

    /**
     * User's login - unique identifier.
     */
    @Id
    private String login;

    /**
     * User's password.
     */
    @ToString.Exclude
    private String password;

    /**
     * User's name.
     */
    private String name;

    /**
     * List of played games of that user.
     */
    @Transient
    @ManyToMany(mappedBy = "userList")
    private List<Game> playedGames = new ArrayList<>();

}
