package pl.edu.pg.eti.game.playedgame.player.entity;


import org.springframework.data.annotation.Transient;
import pl.edu.pg.eti.game.playedgame.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Player playing a game.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Player {

    /**
     * Identifier of the player playing the game.
     */
    @Id
    private String login;

    /**
     * Most recent result for Player's fight roll.
     */
    private Integer fightRoll = 0;

    /**
     * Character chosen by the player.
     * Many players can play the same type of character.
     */
    private Character character;

    /**
     * Item cards owned by the player during the game.
     * They give additional health or strength points.
     */
    private List<ItemCard> cardsOnHand = new ArrayList<>();

    /**
     * List of enemy cards killed by the player.
     * 5 trophies correspond to 1 additional strength point of player's character.
     */
    private List<EnemyCard> trophies = new ArrayList<>();

    /**
     * Player Manager.
     */
    @JsonIgnore
    private PlayerManager playerManager;

}
