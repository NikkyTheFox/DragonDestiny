package pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;

/**
 * EnemyCard extending Card. Adds elements specific to EnemyCard.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Table(name = "enemy_cards")
@DiscriminatorValue("ENEMY_CARD")
public class EnemyCard extends Card {

    /**
     * Initial value of health points of enemy.
     */
    @Column(name = "initial_health")
    private Integer initialHealth;

    /**
     * Initial value of strength points of enemy.
     */
    @Column(name = "initial_strength")
    private Integer initialStrength;

}
