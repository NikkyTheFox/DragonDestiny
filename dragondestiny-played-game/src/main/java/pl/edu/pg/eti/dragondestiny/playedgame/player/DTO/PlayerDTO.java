package pl.edu.pg.eti.dragondestiny.playedgame.player.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.DTO.ItemCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.character.DTO.CharacterDTO;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * Whether the player has already defeated the Bridge Guardian and can go for boss.
     */
    private Boolean bridgeGuardianDefeated = false;

    /**
     * Whether the player has already defeated the Boss and won the game.
     */
    private Boolean bossDefeated = false;

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
