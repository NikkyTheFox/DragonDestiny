package pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object of List of Enemy Card DTOs.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnemyCardListDTO {

    /**
     * A list of enemy cards.
     */
    private List<EnemyCardDTO> enemyCardList;

}
