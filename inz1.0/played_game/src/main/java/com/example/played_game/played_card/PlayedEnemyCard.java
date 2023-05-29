package com.example.played_game.played_card;


import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Entity
public class PlayedEnemyCard extends PlayedCard
{
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
     * @return totalHealth
     */
    public Integer calculateTotalHealth()
    {
        return initialHealth + additionalHealth;
    }

}
