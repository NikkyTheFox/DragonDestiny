package com.example.game_engine.card.itemcard.entity;

import com.example.game_engine.card.card.entity.Card;
import com.example.game_engine.card.card.entity.CardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * ItemCard extending Card.
 * Adds elements specific to ItemCard.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Table(name = "item_cards")
@DiscriminatorValue("ITEM_CARD")
public class ItemCard extends Card
{
    /**
     * Value of strength points ItemCard gives.
     */
    @Column(name = "additional_strength")
    Integer additionalStrength;

    /**
     * Value of health points ItemCard gives.
     */
    @Column(name = "additional_health")
    Integer additionalHealth;

}
