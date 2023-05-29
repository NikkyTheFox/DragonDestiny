package com.example.game_engine.card.card.entity;


import com.example.game_engine.game.entity.Game;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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
     * List of games the card belongs to.
     * One card can belong to many game engines (game boxes).
     */
    @JsonBackReference
    @ManyToMany(mappedBy = "cardDeck")
    private List<Game> games = new ArrayList<>();


}
