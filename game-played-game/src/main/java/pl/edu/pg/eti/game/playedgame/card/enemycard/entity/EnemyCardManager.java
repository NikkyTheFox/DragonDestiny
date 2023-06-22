package pl.edu.pg.eti.game.playedgame.card.enemycard.entity;

public class EnemyCardManager extends EnemyCard {

    /**
     * Method to calculate total health points of enemy
     *
     * @return totalHealth
     */
    public Integer calculateTotalHealth(EnemyCard enemyCard) {
        return enemyCard.getInitialHealth() + enemyCard.getAdditionalHealth();
    }

    /**
     * Method to calculate total strength points of enemy
     *
     * @return totalStrength
     */
    public Integer calculateTotalStrength(EnemyCard enemyCard) {
        return enemyCard.getInitialStrength();
    }

    /**
     * Method to decrease received health point by val.
     *
     * @param val
     */
    public EnemyCard decreaseHealth(EnemyCard enemyCard, Integer val) {
        enemyCard.setAdditionalHealth(enemyCard.getAdditionalHealth() - val);
        return this;
    }

}
