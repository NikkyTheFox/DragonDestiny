package pl.edu.pg.eti.game.playedgame.card.itemcard.entity;


public class ItemCardManager extends ItemCard {


    /**
     * Method to return number of health points left on item.
     *
     * @return
     */
    public Integer calculateHealth(ItemCard card) {
        return card.getHealth() + card.getUsedHealth();
    }

    /**
     * Method to add or remove health points from the item.
     *
     * @param val
     */
    public ItemCard addHealth(ItemCard card, Integer val) {
        card.setUsedHealth(card.getUsedHealth() + val);
        return card;
    }

}
