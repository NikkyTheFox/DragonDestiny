package com.example.game_engine.game.dto;

import lombok.Data;
import lombok.Setter;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class GameDTO {

    /**
     * Unique identifier of game.
     */
    private Integer id;

    /**
     * Identifier of board belonging to the game.
     */
    private Integer boardId;

    /**
     * Number of cards added to game.
     */
    private Integer numOfCards;

    /**
     * Number of characters added to game.
     */
    private Integer numOfCharacters;

}
