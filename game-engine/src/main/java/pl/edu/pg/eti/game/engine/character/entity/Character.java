package pl.edu.pg.eti.game.engine.character.entity;

import jakarta.persistence.*;
import pl.edu.pg.eti.game.engine.field.entity.Field;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a characters cards that are available to play.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Table(name = "characters")
public class Character {

    /**
     * Unique identifier of character.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Integer id;

    /**
     * Name of the character.
     */
    private String name;

    /**
     * Profession / race of character
     */
    private String profession;

    /**
     * Story, description of character.
     */
    private String story;

    /**
     * Value of initial strength points of character.
     */
    @Column(name = "initial_strength")
    private Integer initialStrength;

    /**
     * Value of initial health points of character.
     */
    @Column(name = "initial_health")
    private Integer initialHealth;

    /**
     * Initial position field on board.
     */
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    /**
     * List of games the character belongs to.
     * One character can belong to many game engines (game boxes).
     */
    //@JsonBackReference
    @ManyToMany(mappedBy = "characters")
    private List<Game> games = new ArrayList<>();

}
