package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.Character;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Corresponds to exactly one game played by many players.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "Game")
public class PlayedGame {

    /**
     * Identifier of the played game.
     */
    @Id
    private String id;

    /**
     * States whether the game started.
     */
    private Boolean isStarted = false;

    /**
     * Correspond to active round.
     */
    private Round activeRound;

    /**
     * Players playing the game.
     */
    private List<Player> players = new ArrayList<>();

    /**
     * Board used in the game.
     */
    private PlayedBoard board;

    /**
     * Cards in the game. At the beginning of the game all cards used are in cardDeck.
     */
    private List<Card> cardDeck = new ArrayList<>();

    /**
     * Cards in the game that have been used.
     */
    private List<Card> usedCardDeck = new ArrayList<>();

    /**
     * Characters available to play in the game - not those chosen by users!
     */
    private List<Character> charactersInGame = new ArrayList<>();

    /**
     * Adds player to play in the game.
     *
     * @param player The player to be added to the game.
     */
    public void addPlayerToGame(Player player) {

        this.getPlayers().add(player);
    }

    /**
     * Adds character to game during initialization of played game.
     *
     * @param character The character to be added to the game.
     */
    public void addCharacterToGame(Character character) {

        this.getCharactersInGame().add(character);
    }

    /**
     * Adds card to deck to game during played game.
     *
     * @param card The card to be added to the deck of cards.
     */
    public void addCardToDeck(Card card) {

        this.getCardDeck().add(card);
    }

    /**
     * Removes card from card deck
     *
     * @param card The card to be removed from the deck of cards.
     */
    public void removeCardFromDeck(Card card) {
        OptionalInt index = IntStream.range(0, this.getCardDeck().size())
                .filter(i -> Objects.equals(this.getCardDeck().get(i).getId(), card.getId()))
                .findFirst();
        if (index.isPresent()) {
            this.getCardDeck().remove(index.getAsInt());
        }
    }

    /**
     * Adds card to used deck during the game.
     *
     * @param card The card to be added to the deck of used cards.
     */
    public void addCardToUsedDeck(Card card) {
        this.getUsedCardDeck().add(card);
    }

    /**
     * Removes card from used card deck
     *
     * @param card The card to be removed from the deck of used cards.
     */
    public void removeCardFromUsedDeck(Card card) {
        OptionalInt index = IntStream.range(0, this.getUsedCardDeck().size())
                .filter(i -> Objects.equals(this.getUsedCardDeck().get(i).getId(), card.getId()))
                .findFirst();
        if (index.isPresent()) {
            this.getUsedCardDeck().remove(index.getAsInt());
        }
    }

    /**
     * Starts a game and sets new active round.
     *
     * @param round The round to be set as active.
     */
    public void startGame(Round round) {
        this.setIsStarted(true);
        this.setActiveRound(round);
    }

}
