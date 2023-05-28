package com.example.game_engine.card.card.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represent a card in the game.
 * Instances of this type are joined with instances of its child class - ItemCard or EnemyCard to have complete card information.
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "cards")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("CARD")
@DiscriminatorColumn(name = "card_type", discriminatorType = DiscriminatorType.STRING)
public class Card
{
    /**
     * Unique identifier of the card.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * Name of the card.
     */
    String name;

    /**
     * Description, story told by the card.
     */
    String description;

    /**
     * Identifier of game the card is added to.
     * Right now a card can belong to only one game.
     */
    Integer gameId;

}
