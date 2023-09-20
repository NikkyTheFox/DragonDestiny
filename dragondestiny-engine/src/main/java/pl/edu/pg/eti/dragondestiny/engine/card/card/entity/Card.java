package pl.edu.pg.eti.dragondestiny.engine.card.card.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a card in the game.
 * Instances of this type are joined with instances of its child class - ItemCard or EnemyCard to have complete card information.
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
public class Card {

    /**
     * Unique identifier of the card.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Name of the card.
     */
    private String name;

    /**
     * Description, story told by the card.
     */
    private String description;

    /**
     * List of games the card belongs to.
     * One card can belong to many game engines (game boxes).
     */
    @ManyToMany(mappedBy = "cardDeck")
    private List<Game> games = new ArrayList<>();

}
