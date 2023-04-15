package com.example.game.card.enemycard.dto;

import com.example.game.card.card.dto.CardDTO;
import lombok.Data;

@Data
public class EnemyCardDTO extends CardDTO {

    Integer initialHealth;
    Integer initialStrength;

}
