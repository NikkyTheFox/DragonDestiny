package pl.edu.pg.eti.dragondestiny.playedgame.round.DTO;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.DTO.ItemCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldOptionDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldOptionListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.RoundState;

import java.util.List;

/**
 * DTO allows to hide implementation from the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundDTO {

    /**
     * Identifier of a round
     */
    @Id
    private Integer id;

    /**
     * Player that has option to make a move
     */
    private PlayerDTO activePlayer;

    /**
     * List of players
     */
    private List<PlayerDTO> playerList;

    /**
     * Current round state - what action should be performed now
     */
    private RoundState roundState;

    /**
     * List of next round states to be performed in game
     */
    private List<RoundState> roundStatesOrders;

    /**
     * Value of player's move roll
     */
    private Integer playerMoveRoll;

    /**
     * List of fields the player could move to
     */
    private List<FieldDTO> fieldListToMove;

    /**
     * List of possible options to choose from on the field
     */
    private FieldOptionListDTO fieldOptionList;

    /**
     * Chosen option field on the field
     */
    private FieldOptionDTO playerFieldOptionChosen;

    /**
     * Number of cards drawn by the active player
     */
    private Integer playerNumberOfCardsTaken;

    /**
     * Item Card drawn by active player
     */
    private ItemCardDTO itemCardToTake;

    /**
     * Item Card stolen by player from lost player
     */
    private ItemCardDTO itemCardStolen;

    /**
     * Enemy Card fought by active player
     */
    private EnemyCardDTO enemyFought;

    /**
     * Enemy Player fought by active player
     */
    private PlayerDTO enemyPlayerFought;

    /**
     * Value of player's fight roll
     */
    private Integer playerFightRoll;

    /**
     * Value of enemy's fight roll
     */
    private Integer enemyFightRoll;

}
