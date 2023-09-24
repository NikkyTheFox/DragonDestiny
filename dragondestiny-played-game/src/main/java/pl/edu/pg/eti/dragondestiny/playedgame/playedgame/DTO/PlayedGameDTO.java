package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.playedgame.board.DTO.PlayedBoardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.character.DTO.CharacterDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.round.DTO.RoundDTO;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayedGameDTO {

    /**
     * Identifier of the played game.
     */
    private String id;

    /**
     * States whether the game started.
     */
    private Boolean isStarted;

    /**
     * Correspond to active round.
     */
    private RoundDTO activeRound;

    /**
     * Players playing the game.
     */
    private List<PlayerDTO> players;

    /**
     * Board used in the game.
     */
    private PlayedBoardDTO board;

    /**
     * Cards in the game. At the beginning of the game all cards used are in cardDeck.
     */
    private List<CardDTO> cardDeck;

    /**
     * Cards in the game that have been used.
     */
    private List<CardDTO> usedCardDeck;

    /**
     * Characters available to play in the game - not those chosen by users!
     */
    private List<CharacterDTO> charactersInGame;
}
