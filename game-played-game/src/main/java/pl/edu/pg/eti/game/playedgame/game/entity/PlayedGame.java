package pl.edu.pg.eti.game.playedgame.game.entity;

import pl.edu.pg.eti.game.playedgame.board.entity.PlayedBoard;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;
import org.springframework.data.annotation.Id;
import pl.edu.pg.eti.game.playedgame.round.Round;

import java.util.ArrayList;
import java.util.List;

/**
 * Corresponds to ONE game played by players.
 */

@Getter
@Setter(AccessLevel.PROTECTED)
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
     * Rounds in the game already performed. One round contains move of all players.
     */
    @JsonIgnore
    private List<Round> rounds = new ArrayList<>();

    /**
     * Game Manager for handling actions on cards, players and characters.
     */
    @JsonIgnore
    private GameManager gameManager;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
