package com.example.played_game.played_card;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Corresponds to cards in played game.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Entity
public class PlayedCard
{
    /**
     * Identifier of the card.
     */
    @Id
    private Integer id;
    /**
     * Type of card.
     */
    private CardType cardType;

}
