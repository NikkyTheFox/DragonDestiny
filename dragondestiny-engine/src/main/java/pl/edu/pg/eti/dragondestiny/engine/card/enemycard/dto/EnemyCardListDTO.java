package pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
public class EnemyCardListDTO {

    /**
     * A list of enemy cards.
     */
    private List<EnemyCardDTO> enemyCardList;

}
