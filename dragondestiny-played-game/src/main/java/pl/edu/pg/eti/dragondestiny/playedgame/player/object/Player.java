package pl.edu.pg.eti.dragondestiny.playedgame.player.object;

import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
import pl.edu.pg.eti.dragondestiny.playedgame.interfaces.HealthCalculable;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.Character;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import pl.edu.pg.eti.dragondestiny.playedgame.PlayedGameProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Player participating in a game.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Player implements HealthCalculable {

    /**
     * Identifier of the player playing the game.
     */
    @Id
    private String login;

    /**
     * Most recent result for Player's fight roll.
     */
    private Integer fightRoll = 0;

    /**
     * Number of turns the player is blocked for (cannot make a move).
     */
    private Integer blockedTurns = 0;

    /**
     * Character chosen by the player.
     * Many players can play the same type of character.
     */
    private Character character;

    /**
     * Item cards owned by the player during the game.
     * They give additional health or strength points.
     */
    private List<ItemCard> cardsOnHand = new ArrayList<>();

    /**
     * List of enemy cards killed by the player.
     */
    private List<EnemyCard> trophies = new ArrayList<>();

    /**
     * Calculates total health points of a player's character.
     *
     * @return A number of total health.
     */
    public Integer getHealth(){

        return getCharacter().getHealth();
    }

    /**
     * Calculates total strength points of a player's character.
     *
     * @return A number of total strength.
     */
    public Integer getStrength(){

        return getCharacter().getStrength();
    }


    /**
     * Checks whether a player has place for another card in the hand.
     *
     * @return True/False boolean.
     */
    public boolean checkCardsOnHand() {

        return !(getCardsOnHand().size() >= PlayedGameProperties.numberOfCardsOnHand);
    }

    /**
     * Checks whether a player has enough trophies to increase his character's statistics.
     *
     * @return True/false boolean.
     */
    public boolean checkTrophies() {

        return getTrophies().size() >= PlayedGameProperties.numberOfTrophiesToGetPoint;
    }

    /**
     * Increases strength points from trophies and remove trophies from player.
     */
    public void moveAndIncreaseTrophies() {
        if (getTrophies().size() >= PlayedGameProperties.numberOfTrophiesToGetPoint) {
            addStrength(PlayedGameProperties.trophiesPointIncrease);
            removeCardsFromTrophies(PlayedGameProperties.numberOfTrophiesToGetPoint);
        }
    }

    /**
     * Increases strength points of player's character.
     *
     * @param value A number to increase strength with.
     */
    public void addStrength(Integer value) {

        getCharacter().addStrength(value);
    }

    /**
     * Decreases strength points of player's character.
     *
     * @param value A number to reduce strength with.
     */
    public void reduceStrength(Integer value) {

        getCharacter().reduceStrength(value);
    }

    /**
     * Increases health points of player's character.
     *
     * @param value A number to increase health with.
     */
    public void addHealth(Integer value) {

        getCharacter().addHealth(value);
    }

    /**
     * Decreases health points of player's character.
     *
     * @param value A number to decrease health with.
     */
    public void reduceHealth(Integer value) {

        getCharacter().reduceHealth(value);
    }

    /**
     * Retrieves position field of a player.
     *
     * @return A field that player's character stands on.
     */
    public Field getPositionField(){

        return getCharacter().getField();
    }

    /**
     * Sets player's character's position on board.
     *
    * @param field A field that player's character is moved to.
     */
    public void setPositionField(Field field) {

        getCharacter().setField(field);
    }

    /**
     * Adds card to player's cards on hand.
     *
     * @param card A card added to player's hand.
     */
    public void moveCardToPlayer(Card card) {
        getCardsOnHand().add((ItemCard) card);
        getCharacter().addCard((ItemCard) card);
    }

    /**
     * Removes card from player's cards on hand.
     *
     * @param card A card to be removed from player's hand.
     */
    public void removeCardFromPlayer(Card card) {
        OptionalInt index = IntStream.range(0, getCardsOnHand().size())
                .filter(i -> Objects.equals(getCardsOnHand().get(i).getId(), card.getId()))
                .findFirst();
        if(index.isPresent()){
            getCharacter().removeCard((ItemCard) card);
            getCardsOnHand().remove(index.getAsInt());
        }
    }

    /**
     * Adds card to player's trophies.
     *
     * @param card An enemy card to be added to player's trophies.
     */
    public void moveCardToTrophies(Card card) {

        getTrophies().add((EnemyCard) card);
    }

    /**
     * Removes N cards from trophies.
     *
     * @param N A number of trophies to be removed.
     */
    public void removeCardsFromTrophies(Integer N) {
        if(N > 0){
            getTrophies().subList(0, N).clear();
        }
    }

    /**
     * A method to check if character's health points are higher than 0.
     *
     * @return True/False.
     */
    public Boolean isAlive(){
        return character.getHealth() > 0;
    }
}
