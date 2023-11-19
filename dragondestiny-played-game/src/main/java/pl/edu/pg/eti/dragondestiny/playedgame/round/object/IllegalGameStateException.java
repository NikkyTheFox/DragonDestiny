package pl.edu.pg.eti.dragondestiny.playedgame.round.object;

public class IllegalGameStateException extends Exception {
    public static String GameNotStartedMessage = "The game is not started yet.";
    public static String GameStartedMessage = "The game is already started.";
    public static String GameCannotBeStartedPlayersMessage = "There must be at least one player in game to start.";
    public static String GameCannotBeStartedCharactersMessage = "All players must have a character assigned to start.";
    public static String GameNotFoundMessage = "The game with given ID was not found.";
    public static String PlayerNotFoundMessage = "The player with given Login was not found in given game.";
    public static String CardNotFoundMessage = "The card with given ID was not found in given game.";
    public static String CharacterNotFoundMessage = "The character with given ID was not found in given game.";
    public static String CharactersNotFoundMessage = "No characters found in played game.";
    public static String CharacterHasNoFieldAssignedMessage = "There was no position field assigned to given character.";
    public static String FieldNotFoundMessage = "The field with given ID was not found on the board in given game.";
    public static String PlayerHasNoCharacterAssignedMessage = "There was no character assigned to player with given login.";
    public static String CharacterChosenMessage = "Another player has already chosen this character in the game.";
    public static String PlayerAlreadyAddedMessage = "The player with given login is already added to the game.";
    public static String PlayerIsNotActiveMessage = "The player with given Login is not an active player in this round.";
    public static String PlayerWrongActionMessage = "The player with given Login is not an supposed to perform this action now.";
    public static String PlayerCannotBeBlockedMessage = "The player with given Login is not located on blocking field.";
    public static String PlayerCannotBeBlockedForNegativeTurnsMessage = "Number of turns to block the player must be greater than 0.";
    public static String PlayerDidNotRollMessage = "The player with given Login did not roll this value.";
    public static String PlayerDidNotDrawMessage = "The player with given Login did not draw this card.";
    public static String PlayerActionNotAllowedMessage = "The player with given Login is trying to choose an action not allowed in this round.";
    public static String PlayerWrongEnemyMessage = "The player with given Login is not supposed to fight this enemy.";
    public static String PlayerWrongEnemyTrophyMessage = "The player with given Login did not defeat this enemy.";
    public static String EnemyInvalidTypeMessage = "Only card of type ENEMY CARD can be treated as enemy.";
    public static String ItemInvalidTypeMessage = "Only card of type ITEM CARD can be added to player's hand.";
    public static String PlayerCannotMoveToGivenFieldMessage = "The player with given Login cannot perform a move to that field";
    public static String PlayerHasNoPlaceOnHandMessage = "The player with given Login has no place on hand.";
    public static String PlayerInvalidRollRangeMessage = "The player's roll value is not within set values";
    public static String PlayerWaitingForEnemyRollMessage = "Attacker roll assigned, waiting for attacked player's roll";

    public IllegalGameStateException(String errorMessage) {
        super(errorMessage);
    }
}
