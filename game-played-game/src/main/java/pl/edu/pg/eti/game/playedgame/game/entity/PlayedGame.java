package pl.edu.pg.eti.game.playedgame.game.entity;

import org.springframework.data.annotation.Transient;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Corresponds to ONE game played by players.
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
    private String id; // changed from ObjectId to String; Parametrized retrieval from database was not possible based on ObjectId

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
     * Game Manager for handling actions on cards, players and characters.
     */
    @JsonIgnore
    private GameManager gameManager;

}
