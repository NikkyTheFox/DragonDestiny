package pl.edu.pg.eti.game.engine.card.itemcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemCardListDTO {

    private List<ItemCardDTO> itemCardList;

}
