package pl.edu.pg.eti.game.playedgame.player.entity;

import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;


public class PlayerManager {

    /**
     * Method to calculate total Health points of player.
     * Sum of initial health + additional health points (can be negative) + health points from cards.
     *
     * @return totalHealth
     */
    public Integer calculateTotalHealth(Player player) {
        Integer addFromCards = 0;
        for (ItemCard c : player.getCardsOnHand())
        {
            addFromCards += c.getAdditionalHealth();
        }
        return player.getCharacter().getInitialHealth() + player.getCharacter().getAdditionalHealth() + addFromCards;
    }

    /**
     * Method to calculate total Strength points of player.
     * Sum of initial strength + additional strength points + strength points from cards.
     *
     * @return totalStrength
     */
    public Integer calculateTotalStrength(Player player) {
        Integer addFromCards = 0;
        for (ItemCard c : player.getCardsOnHand())
        {
            addFromCards += c.getAdditionalStrength();
        }
        return player.getCharacter().getInitialStrength() + player.getCharacter().getAdditionalStrength() + addFromCards;
    }

    /**
     * Method to change player's character's position on board.
     *
     * @param player
     * @param field
     */
    public Player changeCharacterPosition(Player player, Field field) {
        player.getCharacter().setPositionField(field);
        return player;
    }

    /**
     * Method to add card to player's cards on hand.
     *
     * @param card
     */
    public Player moveCardToPlayer(Player player, Card card) {
        player.getCardsOnHand().add((ItemCard) card);
        return player;
    }

    /**
     * Method to remove card from player's cards on hand.
     *
     * @param card
     */
    public void removeCardFromPlayer(Player player, Card card) {
        player.getCardsOnHand().remove(card);
    }

    /**
     * Method to set character to the player.
     *
     * @param character
     */
    public void setCharacter(Player player, Character character) {
        player.setCharacter(character);
    }

}
