package pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object;

import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import lombok.*;
import pl.edu.pg.eti.dragondestiny.playedgame.interfaces.HealthCalculable;

/**
 * Corresponds to enemy card in game.
 * Can be in deck, used deck or in hand of a player.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class EnemyCard extends Card implements HealthCalculable {

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
     * Overall value of a health of an enemy card.
     */
    private Integer health = 0;

    /**
     * Increases received health point by val.
     *
     * @param value A number to increase health with.
     */
    public void addHealth(Integer value) {

        this.setHealth(this.getHealth() + value);
    }

    /**
     * Decreases health point by val.
     *
     * @param value A number to reduce health with.
     */
    public void reduceHealth(Integer value) {

        this.setHealth(this.getHealth() - value);
    }

    /**
     * A method to check if health points are higher than 0.
     *
     * @return True/False.
     */
    public Boolean isAlive(){
        return health > 0;
    }
}
