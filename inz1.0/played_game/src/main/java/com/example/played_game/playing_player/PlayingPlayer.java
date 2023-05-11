package com.example.played_game.playing_player;


import com.example.played_game.played_card.PlayedCard;
import com.example.played_game.played_card.PlayedItemCard;
import com.example.played_game.played_character.PlayedCharacter;
import com.example.played_game.played_field.PlayedField;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Player playing a game.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
public class PlayingPlayer
{
    /**
     * Identifier of the player playing the game.
     */
    @Id
    private Integer id;
    /**
     * Character chosen by the player.
     * Many players can play the same type of character.
     */
    @OneToOne
    private PlayedCharacter playedCharacter;
    /**
     * Item cards owned by the player during the game.
     * They give additional health or strength points.
     */
    @OneToMany
    private List<PlayedItemCard> cardsOnHand = new ArrayList<>();

    /**
     * Method to calculate total Health points of player.
     * Sum of initial health + additional health points (can be negative) + health points from cards.
     * @return totalHealth
     */
    public Integer calculateTotalHealth()
    {
        Integer addFromCards = 0;
        for (PlayedItemCard c : cardsOnHand)
        {
            addFromCards += c.getAdditionalHealth();
        }
        return playedCharacter.getInitialHealth() + playedCharacter.getAdditionalHealth() + addFromCards;
    }
    /**
     * Method to calculate total Strength points of player.
     * Sum of initial strength + additional strength points + strength points from cards.
     * @return totalStrength
     */
    public Integer calculateTotalStrength()
    {
        Integer addFromCards = 0;
        for (PlayedItemCard c : cardsOnHand)
        {
            addFromCards += c.getAdditionalStrength();
        }
        return playedCharacter.getInitialStrength() + playedCharacter.getAdditionalStrength() + addFromCards;
    }

    /**
     * Method to change player's character's position on board.
     * @param field
     */
    public void changeCharacterPosition(PlayedField field)
    {
        this.playedCharacter.setPositionField(field);
    }

    public void addCardToPlayer(PlayedCard card)
    {
        this.cardsOnHand.add((PlayedItemCard) card);
    }
    public void removeCardFromPlayer(PlayedCard card)
    {
        this.cardsOnHand.remove(card);
    }
}
