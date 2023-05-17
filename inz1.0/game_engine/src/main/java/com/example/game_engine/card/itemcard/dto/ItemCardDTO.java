package com.example.game_engine.card.itemcard.dto;

import com.example.game_engine.card.card.dto.CardDTO;
import lombok.Data;


/**
 * ItemCardDTO extending CardDTO.
 * Adds elements specific to ItemCard.
 */
@Data
public class ItemCardDTO extends CardDTO {

    /**
     * Value of strength points ItemCard gives.
     */
    Integer additionalStrength;

    /**
     * Value of health points ItemCard gives.
     */
    Integer additionalHealth;
}
