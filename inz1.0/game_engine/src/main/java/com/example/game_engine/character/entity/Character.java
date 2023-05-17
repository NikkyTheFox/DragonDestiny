package com.example.game_engine.character.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
     * Identifier of game the character is added to.
     * Right now a character can belong to only one game.
     */
    Integer gameId;

}
