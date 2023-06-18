package pl.edu.pg.eti.game.playedgame.game.service;

import pl.edu.pg.eti.game.playedgame.PlayedGameApplication;
import pl.edu.pg.eti.game.playedgame.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.card.entity.CardType;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.game.entity.GameManager;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGame;
import pl.edu.pg.eti.game.playedgame.game.repository.PlayedGameRepository;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Played Game Service to communication with played games' data saved in database.
 */

@Service
public class PlayedGameService {

    /**
     * Mongo repository communicating with database.
     */
    private final PlayedGameRepository playedGameRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param playedGameRepository
     */
    @Autowired
    public PlayedGameService(PlayedGameRepository playedGameRepository) {
        this.playedGameRepository = playedGameRepository;
    }

    /**
     * Returns played game by ID. If no game found, throws exception.
     *
     * @param id
     * @return game found
     */
    public Optional<PlayedGame> findPlayedGame(String id) {
        return playedGameRepository.findById(id);
    }

    /**
     * Returns all played games found.
     *
     * @return list of played games
     */
    public List<PlayedGame> findPlayedGames() {
        return playedGameRepository.findAll();
    }

    /**
     * Adds new played game to database.
     *
     * @return saved game
     */
    public PlayedGame save(PlayedGame game) {
        return playedGameRepository.save(game);
    }

    /**
     * Deletes played game from database by id.
     */
    public void deleteById(String id) {
        playedGameRepository.findById(id).orElseThrow(() -> new RuntimeException("No game found"));
        playedGameRepository.deleteById(id);
    }

    /**
     * Updates played game in the database.
     *
     * @return updated game
     */
    public PlayedGame update(String id, PlayedGame playedGameRequest) {
        Optional<PlayedGame> game = playedGameRepository.findById(id);
        if (game.isEmpty())
            return null;
        game.get().setId(playedGameRequest.getId());
        game.get().setCharactersInGame(playedGameRequest.getCharactersInGame());
        game.get().setBoard(playedGameRequest.getBoard());
        game.get().setPlayers(playedGameRequest.getPlayers());
        game.get().setCardDeck(playedGameRequest.getCardDeck());
        game.get().setUsedCardDeck(playedGameRequest.getUsedCardDeck());
        return playedGameRepository.save(game.get());
    }

    public Optional<Card> findCardInCardDeck2(String gameId, Integer id) {
        return playedGameRepository.findCardByIdInCardDeck(gameId, id);
    }

    public Optional<Card> findCardInCardDeck(String gameId, Integer id) {
        Optional<PlayedGame> game = findPlayedGame(gameId);
        for (Card c : game.get().getCardDeck()) {
            if (c.getId().equals(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public Optional<Card> findCardInUsedDeck(String gameId, Integer id) {
        return playedGameRepository.findCardByIdInUsedDeck(gameId, id);
    }

    public Optional<ItemCard> findCardInPlayer(String gameId, String playerLogin, Integer cardId) {
        return playedGameRepository.findCardInPlayers(gameId, playerLogin, cardId);
    }

    public Optional<Player> findPlayer(String gameId, String playerLogin) {
        return playedGameRepository.findPlayerInPlayers(gameId, playerLogin);
    }

    public Optional<Character> findCharacter(String gameId, Integer id) {
        return playedGameRepository.findCharacterInCharacters(gameId, id);
    }

    public Optional<Field> findField(String gameId, Integer id) {
        return playedGameRepository.findFieldOnBoard(gameId, id);
    }

    public PlayedGame moveCardToUsed(PlayedGame game, Card card) {
        GameManager gameManager = game.getGameManager();
        gameManager.addCardToUsedDeck(game, card);
        gameManager.removeCardFromDeck(game, card);
        return playedGameRepository.save(game);
    }

    public PlayedGame moveCardToPlayer(PlayedGame game, Card card, Player player) {
        GameManager gameManager = game.getGameManager();
        player.getPlayerManager().moveCardToPlayer(player, card);
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getLogin(), player.getLogin()))
                .findFirst();
        if (index.isEmpty())
            return null;
        players.set(index.getAsInt(), player);
        game.setPlayers(players);
        gameManager.removeCardFromDeck(game, card);
        return playedGameRepository.save(game);
    }

    public PlayedGame moveCardToTrophies(PlayedGame game, Card card, Player player) {
        GameManager gameManager = game.getGameManager();
        Player updatedPlayer = player.getPlayerManager().moveCardToTrophies(player, card);
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getLogin(), updatedPlayer.getLogin()))
                .findFirst();
        if (index.isEmpty())
            return null;
        players.set(index.getAsInt(), updatedPlayer);
        game.setPlayers(players);
        gameManager.removeCardFromDeck(game, card);
        return playedGameRepository.save(game);
    }


    public PlayedGame moveCardFromPlayer(PlayedGame game, Player player, Card card) {
        Player updatedPlayer = player.getPlayerManager().removeCardFromPlayer(player, card);
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getLogin(), updatedPlayer.getLogin()))
                .findFirst();
        if (index.isEmpty())
            return null;
        players.set(index.getAsInt(), updatedPlayer);
        game.setPlayers(players);
        game.getGameManager().addCardToUsedDeck(game, card);
        return playedGameRepository.save(game);
    }

    public PlayedGame checkTrophies(PlayedGame game, Player player) {
        if(player.getPlayerManager().checkTrophies(player)) {
            Player updatedPlayer = player.getPlayerManager().moveTrophies(player);
            List<Player> players = game.getPlayers();
            OptionalInt index = IntStream.range(0, players.size())
                    .filter(i -> Objects.equals(players.get(i).getLogin(), updatedPlayer.getLogin()))
                    .findFirst();
            if (index.isEmpty())
                return null;
            players.set(index.getAsInt(), updatedPlayer);
            game.setPlayers(players);
        }
        return playedGameRepository.save(game);
    }

    public PlayedGame addPlayer(PlayedGame game, Player player) {
        GameManager gameManager = game.getGameManager();
        gameManager.addPlayerToGame(game, player);
        return playedGameRepository.save(game);
    }

    public PlayedGame setCharacterToPlayer(PlayedGame game, Player player, Character character) {
        player.getPlayerManager().setCharacter(player, character);
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getLogin(), player.getLogin()))
                .findFirst();
        if (index.isEmpty())
            return null;
        players.set(index.getAsInt(), player);
        game.setPlayers(players);
        return playedGameRepository.save(game);
    }

    public PlayedGame changePosition(PlayedGame game, Player player, Character character, Field field) {
        player.getPlayerManager().changeCharacterPosition(player, field);
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getLogin(), player.getLogin()))
                .findFirst();
        if (index.isEmpty())
            return null;
        players.set(index.getAsInt(), player);
        game.setPlayers(players);
        return playedGameRepository.save(game);
    }

    /**
     * Returns a random card from card deck.
     *
     * @param game
     * @return
     */
    public Optional<Card> drawCard(PlayedGame game) {
        Random randomId = new Random();
        Card card = game.getCardDeck().get(randomId.nextInt(game.getCardDeck().size()));
        if (card == null)
            return Optional.empty();
        return Optional.of(card);
    }

    /**
     * Method to calculate fight result between Player and Enemy from card.
     *
     * @param game
     * @param player
     * @param enemy
     * @param playerRoll
     * @param enemyRoll
     * @return
     */
    public boolean calculateFight(PlayedGame game, Player player, EnemyCard enemy, Integer playerRoll, Integer enemyRoll) {
        Integer playerResult = player.getPlayerManager().calculateTotalStrength(player) + playerRoll;
        System.out.println("PLAYER " + playerResult);
        Integer enemyResult = enemy.calculateTotalStrength() + enemyRoll;
        System.out.println("ENEMY " + enemyResult);
        if (playerResult >= enemyResult) {
            return true;
        }
        return false;
    }
}
