package com.example.game.card.itemcard.entity;

import com.example.game.card.card.entity.Card;
import com.example.game.card.card.entity.CardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Table(name = "item_cards")
@DiscriminatorValue("ITEM_CARD")
//@PrimaryKeyJoinColumn(name = "card_id") // ????
public class ItemCard extends Card
{
    @Column(name = "additional_strength")
    Integer additionalStrength;
    @Column(name = "additional_health")
    Integer additionalHealth;

    @JsonIgnore
    public CardType whatType() {
        return CardType.ITEM_CARD;
    }

}
