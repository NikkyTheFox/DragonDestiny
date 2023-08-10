package pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardDTO;

/**
 * DTO allows to hide implementation from the client.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EnemyCardDTO extends CardDTO {

    /**
     * Initial health points of the enemy.
     */
    private Integer initialHealth = 0;

    /**
     * Initial strength points of enemy.
     * Cannot be changed during the game.
     */
    private Integer initialStrength = 0;

    /**
     * Value of current health point number.
     */
    private Integer health = 0;
}
