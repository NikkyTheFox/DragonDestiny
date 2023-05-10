package com.example.game.card.enemycard.entity;

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
@Table(name = "enemy_cards")
@DiscriminatorValue("ENEMY_CARD")
public class EnemyCard extends Card
{
    @Column(name = "initial_health")
    private Integer initialHealth;
    @Column(name = "initial_strength")
    private Integer initialStrength;

    @JsonIgnore
    public CardType whatType() {
        return CardType.ENEMY_CARD;
    }

    // enemy graphics -?


}
