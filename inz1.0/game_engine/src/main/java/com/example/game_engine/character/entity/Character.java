package com.example.game_engine.character.entity;


import com.example.game_engine.game.entity.Game;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
public class Character
{
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
     * List of games the character belongs to.
     * One character can belong to many game engines (game boxes).
     */
    @JsonBackReference
    @ManyToMany(mappedBy = "characters")
    private List<Game> games = new ArrayList<>();


}
