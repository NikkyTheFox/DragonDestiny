package com.example.game_engine.card.card.dto;

import lombok.Data;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class CardDTO
{
    /**
     * Unique identifier of the card.
     */
    Integer id;

    /**
     * Name of the card.
     */
    String name;

    /**
     * Description, story told by the card.
     */
    String description;
}
