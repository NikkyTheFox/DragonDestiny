package com.example.game_engine.character.dto;

import lombok.Data;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class CharacterDTO {

    /**
     * Unique identifier of character.
     */
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
    private Integer initialStrength;

    /**
     * Value of initial health points of character.
     */
    private Integer initialHealth;

}
