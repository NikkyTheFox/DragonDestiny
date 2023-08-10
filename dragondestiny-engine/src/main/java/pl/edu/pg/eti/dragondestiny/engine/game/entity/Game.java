package pl.edu.pg.eti.dragondestiny.engine.game.entity;

import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.Character;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.EqualsAndHashCode;

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
    @ManyToMany
    @JoinTable(
            name = "games_cards",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cardDeck = new ArrayList<>();

    /**
     * List of characters added to game engine.
     * Many-to-many relationship is represented by another table called games_characters.
     */
    @ManyToMany
    @JoinTable(
            name = "games_characters",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<Character> characters = new ArrayList<>();

}
