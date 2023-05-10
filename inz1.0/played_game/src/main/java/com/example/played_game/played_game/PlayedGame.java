package com.example.played_game.played_game;

import com.example.played_game.played_board.PlayedBoard;
import com.example.played_game.played_card.PlayedCard;
import com.example.played_game.played_character.PlayedCharacter;
import com.example.played_game.playing_player.PlayingPlayer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Corresponds to ONE game played by players.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
@EqualsAndHashCode
public class PlayedGame
{
    /**
     * Identifier of the played game.
     */
    @Id
    private Integer id;
    /**
     * Players playing the game.
     */
    @OneToMany
    private List<PlayingPlayer> playingPlayers = new ArrayList<>();
    /**
     * Board used in the game.
     */
    @OneToOne
    private PlayedBoard board;
    /**
     * Cards in the game. At the beginning of the game all cards used are in cardDeck.
     */
    @OneToMany(mappedBy = "id")
    private List<PlayedCard> cardDeck = new ArrayList<>();
    /**
     * Cards in the game that have been used.
     */
    @OneToMany(mappedBy = "id")
    private List<PlayedCard> usedCardDeck = new ArrayList<>();
    /**
     * Characters available to play in the game - not those chosen by users!
     */
    @OneToMany(mappedBy = "id")
    private List<PlayedCharacter> charactersInGame = new ArrayList<>();

    /**
     * Method to add player to play in the game.
     * @param player
     */
    public void addPlayerToGame(PlayingPlayer player)
    {
        this.playingPlayers.add(player);
    }
    /**
     * Method to add character to game during initialization of played game.
     * @param character
     */
    public void addCharacterToGame(PlayedCharacter character)
    {
        this.charactersInGame.add(character);
    }

    /**
     * Method to add card to deck to game during initialization of played game.
     * @param card
     */
    public void addCardToDeck(PlayedCard card)
    {
        this.cardDeck.add(card);
    }

    /**
     * Method to add card to used deck during the game.
     * @param card
     */
    public void addCardToUsedDeck(PlayedCard card)
    {
        this.usedCardDeck.add(card);
    }
}
