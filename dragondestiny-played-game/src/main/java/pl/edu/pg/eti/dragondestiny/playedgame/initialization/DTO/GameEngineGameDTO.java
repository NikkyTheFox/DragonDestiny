package pl.edu.pg.eti.dragondestiny.playedgame.initialization.DTO;

import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.Character;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;
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
public class GameEngineGameDTO {

    /**
     * Identifier of the game setup.
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
