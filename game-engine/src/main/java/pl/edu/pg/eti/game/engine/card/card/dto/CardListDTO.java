package pl.edu.pg.eti.game.engine.card.card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CardListDTO {

    private List<CardDTO> cardList;
}
