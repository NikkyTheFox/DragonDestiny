package pl.edu.pg.eti.game.playedgame.field.entity;

import pl.edu.pg.eti.game.playedgame.card.enemycard.entity.EnemyCard;

public class FieldManager extends Field {
    public Field setEnemy(Field field, EnemyCard enemyCard) {
        field.setEnemy(enemyCard);
        return field;
    }

}
