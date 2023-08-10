package pl.edu.pg.eti.dragondestiny.playedgame.player.DTO;

import lombok.Data;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.DTO.ItemCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.character.DTO.CharacterDTO;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
public class PlayerDTO {

    /**
     * Identifier of the player playing the game.
     */
    private String login;

    /**
     * Most recent result for Player's fight roll.
     */
    private Integer fightRoll;

    /**
     * Number of turns the player is blocked for (cannot make a move).
     */
    private Integer blockedTurns;

    /**
     * Character chosen by the player.
     */
    private CharacterDTO character;

    /**
     * Item cards owned by the player during the game.
     */
    private List<ItemCardDTO> cardsOnHand;

    /**
     * List of enemy cards killed by the player.
     */
    private List<EnemyCardDTO> trophies;
}
