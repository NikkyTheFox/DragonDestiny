package com.example.game_engine.card.enemycard.dto;

import com.example.game_engine.card.card.dto.CardDTO;
import lombok.Data;

/**
 * EnemyCardDTO extending CardDTO.
 * Adds elements specific to EnemyCard.
 */
@Data
public class EnemyCardDTO extends CardDTO {

    /**
     * Initial value of health points of enemy.
     */
    Integer initialHealth;

    /**
     * Initial value of strength points of enemy.
     */
    Integer initialStrength;

}
