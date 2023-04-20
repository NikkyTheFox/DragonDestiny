package com.example.game.card.card.entity;


import com.example.game.card.enemycard.entity.EnemyCard;
import com.example.game.card.itemcard.entity.ItemCard;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@Table(name = "cards")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("CARD")
@DiscriminatorColumn(name = "card_type", discriminatorType = DiscriminatorType.STRING)
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "CardType")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = EnemyCard.class, name = "ENEMY_CARD"),
//        @JsonSubTypes.Type(value = ItemCard.class, name = "ITEM_CARD")
//        })
//@SequenceGenerator(name = "cards_seq", allocationSize = 100)
// allocation size - max?
public class Card
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String description;
    Integer gameId;


//    @Column(name = "card_type")
//    @Enumerated(EnumType.STRING)
//    CardType cardType;

//    public abstract CardType whatType();

    // GRAPHICS
    /*
    public void showCard();

    */
}
