package pl.edu.pg.eti.game.playedgame.player.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.game.playedgame.PlayedGameApplication;
import pl.edu.pg.eti.game.playedgame.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class PlayerManager extends Player{

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
        for (ItemCard c : player.getCardsOnHand()) {
            addFromCards += c.getAdditionalStrength();
        }
        System.out.println("add from cards: " + addFromCards);
        System.out.println("initial: " + player.getCharacter().getInitialStrength());
        System.out.println("additional: " + player.getCharacter().getAdditionalStrength());
        return player.getCharacter().getInitialStrength() + player.getCharacter().getAdditionalStrength() + addFromCards;
    }

    /**
     * Checks if player has place on hand for new cards.
     *
     * @param player
     * @return
     */
    public boolean checkCardsOnHand(Player player) {
        if (player.getCardsOnHand().size() >= PlayedGameApplication.numOfCardsOnHand)
            return false;
        return true;
    }

    /**
     * Method to check additional strength points received from trophies collected by the player.
     *
     * @param player
     */
    public boolean checkTrophies(Player player) {
        int numOfTrophies = player.getTrophies().size();
        return numOfTrophies >= PlayedGameApplication.numOfTrophiesToGetPoint;
    }

    /**
     * Method to increase strenth points from trophies and remove trophies from player.
     *
     * @param player
     * @return
     */

    public Player moveAndIncreaseTrophies(Player player) {
        int numOfTrophies = player.getTrophies().size();
        if (numOfTrophies >= PlayedGameApplication.numOfTrophiesToGetPoint) {
            player.getPlayerManager().increaseStrength(player, PlayedGameApplication.trophiesPointIncrease);
            player = removeCardsFromTrophies(player, PlayedGameApplication.numOfTrophiesToGetPoint);
        }
        return player;
    }

    /**
     * Method to increase strength points of player's character by val.
     *
     * @param player
     * @param val
     * @return
     */
    public Player increaseStrength(Player player, Integer val) {
        player.getCharacter().increaseStrength(val);
        return player;
    }

    /**
     * Method to increase health points of player's character by val.
     *
     * @param player
     * @param val
     * @return
     */
    public Player increaseHealth(Player player, Integer val) {
        player.getCharacter().increaseHealth(val);
        return player;
    }

    /**
     * Method to decrease health of the Player.
     * Checks if Player has health item cards that act as a shield, if so removes health point from them.
     * If not, removes health point directly from Character.
     *
     * @param player
     * @param val
     * @return
     */
    public Player decreaseHealth(Player player, Integer val) {
        Optional<ItemCard> card = player.getCardsOnHand().stream().filter(itemCard -> itemCard.getHealth() > 0).findFirst();
        if (card.isEmpty()) {
            // no health cards
            player.getCharacter().decreaseHealth(val);
        } else {
            // decrease health card
            card.get().decreaseHealth(val);
            if (card.get().getHealth() <= 0) { // remove used up card
                player.getPlayerManager().removeCardFromPlayer(player, card.get());
            }
        }
        return player;
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
    public Player removeCardFromPlayer(Player player, Card card) {
        OptionalInt index = IntStream.range(0, player.getCardsOnHand().size())
                .filter(i -> Objects.equals(player.getCardsOnHand().get(i).getId(), card.getId()))
                .findFirst();
        if (index.isEmpty()) {
            return player;
        }
        player.getCardsOnHand().remove(index.getAsInt());
        return player;
    }

    /**
     * Method to add card to player's trophies.
     *
     * @param card
     */
    public Player moveCardToTrophies(Player player, Card card) {
        player.getTrophies().add((EnemyCard) card);
        return player;
    }

    /**
     * Method to remove N cards from trophies.
     *
     * @param player
     * @param N
     * @return
     */
    public Player removeCardsFromTrophies(Player player, Integer N) {
        for (int d = N - 1; d >= 0; d--) {
            System.out.println("removing " + d);
            player.getTrophies().remove(d);
        }
        return player;
    }

    /**
     * Method to set character to the player.
     *
     * @param character
     */
    public Player setCharacter(Player player, Character character) {
        player.setCharacter(character);
        return player;
    }

    /**
     * Method to set character to the player.
     *
     * @param val
     */
    public Player setFightRoll(Player player, Integer val) {
        player.setFightRoll(val);
        return player;
    }

}
