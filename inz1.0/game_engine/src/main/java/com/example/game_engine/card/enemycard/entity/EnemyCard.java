package com.example.game_engine.card.enemycard.entity;

import com.example.game_engine.card.card.entity.Card;
import com.example.game_engine.card.card.entity.CardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * EnemyCard extending Card.
 * Adds elements specific to EnemyCard.
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
public class EnemyCard extends Card
{
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
