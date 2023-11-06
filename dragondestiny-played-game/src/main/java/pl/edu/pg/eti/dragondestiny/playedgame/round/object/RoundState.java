package pl.edu.pg.eti.dragondestiny.playedgame.round.object;

public enum RoundState {

    WAITING_FOR_MOVE_ROLL, // GET playedgames/{id}/players/{login}/roll
    WAITING_FOR_FIELDS_TO_MOVE, // GET playedgames/{id}/players/{login}/field/move/{roll}/fields
    WAITING_FOR_MOVE, // PUT playedgames/{id}/players/{login}/field/{fieldId}
    WAITING_FOR_FIELD_OPTIONS, // GET playedgames/{id}/players/{login}/field/options
    WAITING_FOR_FIELD_ACTION_CHOICE, // PUT playedgames/{id}/players/{login}/action/{actionName}
    WAITING_FOR_CARD_DRAWN,
    WAITING_FOR_CARD_TO_HAND, //
    WAITING_FOR_CARD_TO_USED,

    WAITING_FOR_FIGHT_ROLL, // GET playedgames/{id}/players/{login}/roll
    WAITING_FOR_ENEMY_ROLL,  // GET playedgames/{id}/players/{login}/roll
    WAITING_FOR_FIGHT_RESULT,
    WAITING_FOR_CARD_TO_TROPHIES,

    WAITING_FOR_NEXT_ROUND,

    END_ROUND


}
