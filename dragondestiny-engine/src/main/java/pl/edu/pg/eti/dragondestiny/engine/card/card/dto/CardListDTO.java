package pl.edu.pg.eti.dragondestiny.engine.card.card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object of List of Card DTOs.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardListDTO {

    /**
     * A list of cards.
     */
    private List<CardDTO> cardList;
}
