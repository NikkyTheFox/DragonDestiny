package pl.edu.pg.eti.game.playedgame.card.itemcard.entity;


public class ItemCardManager extends ItemCard {


    /**
     * Method to return number of health points left on item.
     *
     * @return
     */
    public Integer calculateHealth(ItemCard card) {
        return card.getAdditionalHealth() + card.getUsedAdditionalHealth();
    }

    /**
     * Method to remove health points from the item.
     *
     * @param val
     */
    public ItemCard decreaseHealth(ItemCard card, Integer val) {
        card.setUsedAdditionalHealth(card.getUsedAdditionalHealth() - val);
        return card;
    }

}
