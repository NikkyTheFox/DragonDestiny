package pl.edu.pg.eti.dragondestiny.engine.card.card.entity;


import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.AccessLevel;
import lombok.ToString;

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
