package pl.edu.pg.eti.game.engine.card.enemycard.dto;

import pl.edu.pg.eti.game.engine.card.card.dto.CardDTO;
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
    private Integer initialHealth;

    /**
     * Initial value of strength points of enemy.
     */
    private Integer initialStrength;

}
