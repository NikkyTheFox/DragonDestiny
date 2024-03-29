package pl.edu.pg.eti.game.playedgame.game.entity;

import pl.edu.pg.eti.game.playedgame.board.entity.PlayedBoard;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.game.playedgame.player.entity.PlayerManager;
import pl.edu.pg.eti.game.playedgame.round.Round;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@AllArgsConstructor
@Getter
@Setter
public class GameManager extends PlayedGame {

    /**
     * Method to add player to play in the game.
     *
     * @param game
     * @param player
     */
    public void addPlayerToGame(PlayedGame game, Player player) {
        game.getPlayers().add(player);
    }

    /**
     * Method to add character to game during initialization of played game.
     *
     * @param game
     * @param character
     */
    public void addCharacterToGame(PlayedGame game, Character character) {
        game.getCharactersInGame().add(character);
    }

    /**
     * Method to add card to deck to game during played game.
     *
     * @param game
     * @param card
     */
    public void addCardToDeck(PlayedGame game, Card card) {
        game.getCardDeck().add(card);
    }

    /**
     * Method to remove card from card deck
     *
     * @param game
     * @param card
     */
    public void removeCardFromDeck(PlayedGame game, Card card) {
        OptionalInt index = IntStream.range(0, game.getCardDeck().size())
                .filter(i -> Objects.equals(game.getCardDeck().get(i).getId(), card.getId()))
                .findFirst();
        if (index.isEmpty()) {
            return;
        }
        game.getCardDeck().remove(index.getAsInt());
    }

    /**
     * Method to add card to used deck during the game.
     *
     * @param game
     * @param card
     */
    public void addCardToUsedDeck(PlayedGame game, Card card) {
        game.getUsedCardDeck().add(card);
    }

    /**
     * Method to remove card from used card deck
     *
     * @param game
     * @param card
     */
    public void removeCardFromUsedDeck(PlayedGame game, Card card) {
        OptionalInt index = IntStream.range(0, game.getUsedCardDeck().size())
                .filter(i -> Objects.equals(game.getUsedCardDeck().get(i).getId(), card.getId()))
                .findFirst();
        if (index.isEmpty()) {
            return;
        }
        game.getUsedCardDeck().remove(index.getAsInt());
    }

    public PlayedGame startGame(PlayedGame game, Round round) {
        game.setIsStarted(true);
        game.setActiveRound(round);
        return game;
    }

    public PlayedGame setBoard(PlayedGame game, PlayedBoard board) {
        game.setBoard(board);
        return game;
    }

    public PlayedGame setCharactersInGame(PlayedGame game, List<Character> characters) {
        game.setCharactersInGame(characters);
        return game;
    }

    public PlayedGame setPlayers(PlayedGame game, List<Player> players) {
        game.setPlayers(players);
        return game;
    }

    public PlayedGame setCardDeck(PlayedGame game, List<Card> cards) {
        game.setCardDeck(cards);
        return game;
    }

    public PlayedGame setUsedCardDeck(PlayedGame game, List<Card> cards) {
        game.setUsedCardDeck(cards);
        return game;
    }
}
