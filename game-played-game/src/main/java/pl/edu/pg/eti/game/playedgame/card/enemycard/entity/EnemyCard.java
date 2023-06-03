package pl.edu.pg.eti.game.playedgame.card.enemycard.entity;

import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import lombok.*;

/**
 * Corresponds to enemy card in game.
 * Can be in deck, used deck or in hand of a player.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class EnemyCard extends Card {

    /**
     * Initial health points of the enemy.
     */
    private Integer initialHealth;

    /**
     * Health points received or lost during the game.
     * If sum of initialHealth + additionalHealth <= 0, enemy dies.
     */
    private Integer additionalHealth;

    /**
     * Initial strength points of enemy.
     * Cannot be changed during the game.
     */
    private Integer initialStrength;

    /**
     * Method to calculate total health points of enemy
     *
     * @return totalHealth
     */
    public Integer calculateTotalHealth()
    {
        return initialHealth + additionalHealth;
    }

}
