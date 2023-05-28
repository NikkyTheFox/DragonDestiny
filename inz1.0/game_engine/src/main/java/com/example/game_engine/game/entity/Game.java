package com.example.game_engine.game.entity;

import com.example.game_engine.board.entity.Board;
import com.example.game_engine.card.card.entity.Card;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
     * Board belonging to the game.
     */
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    /**
     * List of cards added to game engine.
     * Many-to-many relationship is represented by another table called games_cards.
     */
    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "games_cards",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    private List<Card> cardDeck = new ArrayList<>();

    /**
     * List of characters added to game engine.
     * Many-to-many relationship is represented by another table called games_characters.
     */
    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "games_characters",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<Card> characters = new ArrayList<>();

    /**
     * Number of cards added to game.
     */
    @Formula(value = "(SELECT count(*) FROM games_cards " +
            "WHERE game_id = game_id)")
    private Integer numOfCards;

    /**
     * Number of characters added to game.
     */
    @Formula(value = "(SELECT count(*) FROM games_characters " +
            "WHERE game_id = game_id)")
    private Integer numOfCharacters;


}
