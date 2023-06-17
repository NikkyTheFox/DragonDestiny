package pl.edu.pg.eti.game.playedgame.game.entity;

import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class GameManager {

    /**
     * Method to add player to play in the game.
     *
     * @param player
     */
    public void addPlayerToGame(Player player, List<Player> playerList) {
        playerList.add(player);
    }

    /**
     * Method to add character to game during initialization of played game.
     *
     * @param character
     */
    public void addCharacterToGame(Character character, List<Character> characterList) {
        characterList.add(character);
    }

    /**
     * Method to add card to deck to game during played game.
     *
     * @param card
     */
    public void addCardToDeck(Card card, List<Card> cardList) {
        cardList.add(card);
    }

    /**
     * Method to remove card from card deck
     *
     * @param card
     */
    public void removeCardFromDeck(Card card, List<Card> cardList) {
        cardList.remove(card);
        cardList.remove(card);
    }

    /**
     * Method to add card to used deck during the game.
     *
     * @param card
     */
    public void addCardToUsedDeck(Card card, List<Card> cardList) {
        cardList.add(card);
    }

    /**
     * Method to remove card from used card deck
     *
     * @param card
     */
    public void removeCardFromUsedDeck(Card card, List<Card> cardList) {
        cardList.remove(card);
    }

}
