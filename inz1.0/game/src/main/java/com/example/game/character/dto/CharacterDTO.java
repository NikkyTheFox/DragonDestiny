package com.example.game.character.dto;

import lombok.Data;

/**
 * DTO allows to hide implementation from the client.
 *
 */
@Data
public class CharacterDTO {
    private Integer id;
    private String name;
    private String profession;
    private String story;
    private Integer initialStrength;
    private Integer initialHealth;
    Integer gameId;
}
