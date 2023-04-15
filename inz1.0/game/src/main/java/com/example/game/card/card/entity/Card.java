package com.example.game.card.card.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represent a card in the game.
 * Inheritance type -> MappedSuperclass, no entities of card class can be created.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "cards")
public class Card
{
    @Id
   // @GeneratedValue(strategy = GenerationType.TABLE)
    Integer id;
    String name;
    String description;

    // GRAPHICS
    /*
    public void showCard();

    */
}
