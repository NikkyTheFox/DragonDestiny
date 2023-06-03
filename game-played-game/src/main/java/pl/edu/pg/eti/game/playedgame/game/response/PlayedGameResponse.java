package pl.edu.pg.eti.game.playedgame.game.response;
import pl.edu.pg.eti.game.playedgame.board.entity.PlayedBoard;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents response received from game engine.
 * Will be a base for PlayedGame.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayedGameResponse {

    /**
     * Identifier of the played game.
     */
    @Id
    private Integer id;

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

}
