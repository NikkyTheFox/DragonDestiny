package pl.edu.pg.eti.dragondestiny.playedgame.round.object;

import jakarta.persistence.Id;
import lombok.*;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
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
     * Identifier of a round.
     */
    @Id
    private Integer id;

    /**
     * Player that has option to make a move.
     */
    private Player activePlayer;

    /**
     * States whether player has performed a move already in the round.
     */
    private Integer playerMoveRoll;
    private boolean playerMoved = false;

    private RoundState roundState;

    private List<Field> fieldListToMove = new ArrayList<>();


    private FieldOption playerFieldOptionChosen;

    private FieldOptionList fieldOptionList;

    private List<RoundState> roundStatesOrder = new ArrayList<>();

    // card:
    private Integer playerNumberOfCardsTaken = 0; // num of cards player has drawn
    // card - enemy:
    private EnemyCard playerEnemyFought;
    private Integer playerFightRoll;
    private Integer enemyFightRoll;
    // block:
    private boolean playerBlocked = false;
    // fight - player:
    private boolean playerToPlayerFightWon = false;

    /**
     * List of players.
     */
    private List<Player> playerList = new ArrayList<>();

    public void addRoundState(RoundState roundState) {
        roundStatesOrder.add(roundState);
    }

    public void increaseNumOfCardsTaken(Integer value) {
        this.playerNumberOfCardsTaken += value;
    }

    public void initiateRoundStates() {
        roundStatesOrder.add(RoundState.WAITING_FOR_MOVE_ROLL);
        roundStatesOrder.add(RoundState.WAITING_FOR_FIELDS_TO_MOVE);
        roundStatesOrder.add(RoundState.WAITING_FOR_MOVE);
        roundStatesOrder.add(RoundState.WAITING_FOR_FIELD_OPTIONS);
        roundStatesOrder.add(RoundState.WAITING_FOR_FIELD_ACTION_CHOICE);
        roundState = roundStatesOrder.get(0);
    }

    public void initiateRoundStatesBossField() {
        roundStatesOrder.add(RoundState.WAITING_FOR_FIELD_ACTION_CHOICE);
        roundStatesOrder.add(RoundState.WAITING_FOR_FIGHT_ROLL);
        roundStatesOrder.add(RoundState.WAITING_FOR_FIGHT_RESULT);
        roundState = roundStatesOrder.get(0);
    }

    public void nextRoundState() {
        roundStatesOrder.remove(0);
        roundState = roundStatesOrder.size() > 0 ? roundStatesOrder.get(0) : RoundState.END_ROUND;
    }
}
