package pl.edu.pg.eti.dragondestiny.engine.card.card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of Card instance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    /**
     * Unique identifier of the card.
     */
    private Integer id;

    /**
     * Name of the card.
     */
    private String name;

    /**
     * Description, story told by the card.
     */
    private String description;

}
