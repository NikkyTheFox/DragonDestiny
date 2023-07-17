package pl.edu.pg.eti.game.playedgame.card.enemycard.response;

import pl.edu.pg.eti.game.playedgame.card.enemycard.entity.EnemyCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents list of entities received from game engine.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnemyCardList {
    private List<EnemyCard> enemyCardList;

}
