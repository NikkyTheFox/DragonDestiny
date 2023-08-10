package pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto;

import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;
import lombok.Data;

/**
 * DTO allows to hide implementation from the client.
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
