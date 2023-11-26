package pl.edu.pg.eti.dragondestiny.playedgame.round.object;

import jakarta.persistence.Id;
import lombok.*;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldOption;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldOptionList;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent Rounds used in played game documents.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Round {

    /**
     * Identifier of a round
     */
    @Id
    private Integer id;

    /**
     * Player that has option to make a move
     */
    private Player activePlayer;

    /**
     * List of players
     */
    private List<Player> playerList = new ArrayList<>();

    /**
     * Current round state - what action should be performed now
     */
    private RoundState roundState;

    /**
     * List of next round states to be performed in game
     */
    private List<RoundState> roundStatesOrder = new ArrayList<>();

    /**
     * Value of player's move roll
     */
    private Integer playerMoveRoll;

    /**
     * List of fields the player could move to
     */
    private List<Field> fieldListToMove = new ArrayList<>();

    /**
     * List of possible options to choose from on the field
     */
    private FieldOptionList fieldOptionList;

    /**
     * Chosen option field on the field
     */
    private FieldOption playerFieldOptionChosen;

    /**
     * Number of cards drawn by the active player
     */
    private Integer playerNumberOfCardsTaken = 0;

    /**
     * Item Card drawn by active player
     */
    private ItemCard itemCardToTake;

    /**
     * Item Card stolen by player from lost player
     */
    private ItemCard itemCardStolen;

    /**
     * Enemy Card fought by active player
     */
    private EnemyCard enemyFought;

    /**
     * Enemy Player fought by active player
     */
    private Player enemyPlayerFought;

    /**
     * Value of player's fight roll
     */
    private Integer playerFightRoll;

    /**
     * Value of enemy's fight roll
     */
    private Integer enemyFightRoll;


    public void addRoundState(RoundState roundState) {

        roundStatesOrder.add(roundState);
    }

    public void increaseNumOfCardsTaken(Integer value) {

        this.playerNumberOfCardsTaken += value;
    }

    public void initiateRoundStates() {
        roundStatesOrder.clear();
        roundStatesOrder.add(RoundState.WAITING_FOR_MOVE_ROLL);
        roundStatesOrder.add(RoundState.WAITING_FOR_FIELDS_TO_MOVE);
        roundStatesOrder.add(RoundState.WAITING_FOR_MOVE);
        roundStatesOrder.add(RoundState.WAITING_FOR_FIELD_OPTIONS);
        roundStatesOrder.add(RoundState.WAITING_FOR_FIELD_ACTION_CHOICE);
        roundState = roundStatesOrder.get(0);
    }

    public void initiateRoundStatesBossField() {
        roundStatesOrder.clear();
        roundStatesOrder.add(RoundState.WAITING_FOR_FIELD_OPTIONS);
        roundStatesOrder.add(RoundState.WAITING_FOR_FIELD_ACTION_CHOICE);
        roundState = roundStatesOrder.get(0);
    }

    public void nextRoundState() {
        if (!roundStatesOrder.isEmpty()) {
            roundStatesOrder.remove(0);
            roundState = roundStatesOrder.size() > 0 ? roundStatesOrder.get(0) : RoundState.WAITING_FOR_NEXT_ROUND;
        } else {
            roundState = RoundState.WAITING_FOR_NEXT_ROUND;
        }
    }
}
