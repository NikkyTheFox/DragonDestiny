package com.example.game.card.charactercard;


import com.example.game.card.card.entity.Card;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a card with a playable character.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Table(name = "character_cards")
public class CharacterCard extends Card
{
    String profession;
    Integer initialStrength;
    Integer initialHealth;

    // different graphic showing just the character, not the card

}
