package pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
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
    private List<CardDTO> cardList = new ArrayList<>();
}
