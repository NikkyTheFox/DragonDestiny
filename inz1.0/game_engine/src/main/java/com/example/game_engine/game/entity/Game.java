package com.example.game_engine.game.entity;

import com.example.game_engine.card.card.entity.Card;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

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

    /**
     * Unique identifier of game.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    /**
     * Identifier of board belonging to the game.
     */
    private Integer boardId;

//    @OneToMany(mappedBy = "id")
//    private List<Card> cardDeck = new ArrayList<>();

    /**
     * Number of cards added to game.
     */
    @Formula(value = "(SELECT count(*) FROM cards " +
            "WHERE cards.game_id = game_id)")
    private Integer numOfCards;

    /**
     * Number of characters added to game.
     */
    @Formula(value = "(SELECT count(*) FROM characters " +
            "WHERE characters.game_id = game_id)")
    private Integer numOfCharacters;


}
