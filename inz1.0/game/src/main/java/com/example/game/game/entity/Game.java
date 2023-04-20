package com.example.game.game.entity;

import com.example.game.card.card.entity.Card;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Game class represents a 'box' where all elements of the game are stored.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;
    private Integer boardId;
    @OneToMany(mappedBy = "id")
    private List<Card> cardDeck = new ArrayList<>();

//    @OneToMany(mappedBy = "id")
//    private List<Card> usedCardDeck = new ArrayList<>();

}
