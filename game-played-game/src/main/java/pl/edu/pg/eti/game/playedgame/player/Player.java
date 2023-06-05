package pl.edu.pg.eti.game.playedgame.player;


import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Player playing a game.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Player {

    /**
     * Identifier of the player playing the game.
     */
    @Id
    private String login;

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
     * Player Manager
     */
    @JsonIgnore
    private PlayerManager playerManager;

    /**
     * Method to calculate total Health points of player.
     * Sum of initial health + additional health points (can be negative) + health points from cards.
     *
     * @return totalHealth
     */
//    public Integer calculateTotalHealth()
//    {
//        Integer addFromCards = 0;
//        for (ItemCard c : cardsOnHand)
//        {
//            addFromCards += c.getAdditionalHealth();
//        }
//        return character.getInitialHealth() + character.getAdditionalHealth() + addFromCards;
//    }
//
//    /**
//     * Method to calculate total Strength points of player.
//     * Sum of initial strength + additional strength points + strength points from cards.
//     *
//     * @return totalStrength
//     */
//    public Integer calculateTotalStrength()
//    {
//        Integer addFromCards = 0;
//        for (ItemCard c : cardsOnHand)
//        {
//            addFromCards += c.getAdditionalStrength();
//        }
//        return character.getInitialStrength() + character.getAdditionalStrength() + addFromCards;
//    }
//
//    /**
//     * Method to change player's character's position on board.
//     *
//     * @param field
//     */
//    public void changeCharacterPosition(Field field)
//    {
//        this.character.setPositionField(field);
//    }
//
//    /**
//     * Method to add card to player's cards on hand.
//     *
//     * @param card
//     */
//    public void addCardToPlayer(Card card)
//    {
//        this.cardsOnHand.add((ItemCard) card);
//    }
//
//    /**
//     * Method to remove card from player's cards on hand.
//     *
//     * @param card
//     */
//    public void removeCardFromPlayer(Card card)
//    {
//        this.cardsOnHand.remove(card);
//    }

}
