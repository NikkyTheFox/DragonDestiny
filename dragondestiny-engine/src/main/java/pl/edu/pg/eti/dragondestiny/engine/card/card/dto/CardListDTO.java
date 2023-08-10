package pl.edu.pg.eti.dragondestiny.engine.card.card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class CardListDTO {

    /**
     * A list of cards.
     */
    private List<CardDTO> cardList;
}
