package pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A structure containing list of enemy cards.
 */
@Data
@AllArgsConstructor
public class EnemyCardList {

    /**
     * A list of enemy cards.
     */
    private List<EnemyCard> enemyCardList;

}
