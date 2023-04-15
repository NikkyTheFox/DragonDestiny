package com.example.game.card.enemycard.entity;

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
@Table(name = "enemy_cards")
public class EnemyCard extends Card
{
    Integer initialHealth;
    Integer initialStrength;

    // enemy graphics -?


}
