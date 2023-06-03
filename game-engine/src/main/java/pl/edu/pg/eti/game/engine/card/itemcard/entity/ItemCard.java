package pl.edu.pg.eti.game.engine.card.itemcard.entity;

import pl.edu.pg.eti.game.engine.card.card.entity.Card;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.AccessLevel;
import lombok.ToString;

/**
 * ItemCard extending Card.
 * Adds elements specific to ItemCard.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@Table(name = "item_cards")
@DiscriminatorValue("ITEM_CARD")
public class ItemCard extends Card {

    /**
     * Value of strength points ItemCard gives.
     */
    @Column(name = "additional_strength")
    Integer additionalStrength;

    /**
     * Value of health points ItemCard gives.
     */
    @Column(name = "additional_health")
    Integer additionalHealth;

}
