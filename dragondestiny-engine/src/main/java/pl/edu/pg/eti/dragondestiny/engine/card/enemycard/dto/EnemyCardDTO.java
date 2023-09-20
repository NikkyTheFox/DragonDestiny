package pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;

/**
 * Data Transfer Object of Enemy Card instance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
