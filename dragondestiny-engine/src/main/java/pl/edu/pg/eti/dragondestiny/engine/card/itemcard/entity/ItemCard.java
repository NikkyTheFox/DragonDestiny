package pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;

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
    @Column(name = "strength")
    Integer strength;

    /**
     * Value of health points ItemCard gives.
     */
    @Column(name = "health")
    Integer health;

}
