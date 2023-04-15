package com.example.game.card.itemcard;

import com.example.game.card.card.entity.Card;
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
public class ItemCard extends Card
{
    Integer additionalStrength;
    Integer additionalHealth;

}
