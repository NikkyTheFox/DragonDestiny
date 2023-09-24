package pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Structure containing a list of enemy cards.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnemyCardList {
    /**
     * A list of enemy cards.
     */
    private List<EnemyCard> enemyCardList = new ArrayList<>();
}
