package pl.edu.pg.eti.dragondestiny.engine.card.card.dto;

import lombok.Data;

/**
 * Data Transfer Object of Card instance.
 */
@Data
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
