package com.example.played_game.played_card;


import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Corresponds to item card in game.
 * Can be in deck, used deck or in hand of a player.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Entity
public class PlayedItemCard extends PlayedCard
{
    /**
     * Additional strength points for character that owns this card.
     */
    private Integer additionalStrength;
    /**
     * Additional health points for character that owns this card.
     */
    private Integer additionalHealth;

}
