package pl.edu.pg.eti.game.playedgame.game.service;

import pl.edu.pg.eti.game.playedgame.PlayedGameApplication;
import pl.edu.pg.eti.game.playedgame.board.entity.PlayedBoard;
import pl.edu.pg.eti.game.playedgame.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.field.FieldOption;
import pl.edu.pg.eti.game.playedgame.field.FieldOptionList;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.game.entity.GameManager;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGame;
import pl.edu.pg.eti.game.playedgame.game.repository.PlayedGameRepository;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.game.playedgame.round.Round;
import pl.edu.pg.eti.game.playedgame.round.RoundManager;

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

    public Optional<PlayedBoard> findBoard(String gameId) {
        PlayedBoard board = playedGameRepository.findBoard(gameId);
        if (board == null)
            return Optional.empty();
        return Optional.of(board);
    }

    public List<Field> findFields(String gameId) {
        List<Field> list = playedGameRepository.findFieldsOnBoard(gameId);
        return list;
    }

    public Optional<Player> findPlayerByField(String gameId, Integer fieldId) {
        return playedGameRepository.findPlayerByField(gameId, fieldId);
    }

    public Optional<Player> findDifferentPlayerByField(String gameId, String playerId, Integer fieldId) {
        return playedGameRepository.findDifferentPlayerByField(gameId, playerId, fieldId);
    }

    /**
     * Method to start initialized PlayedGame.
     * Starts a first round and selects a random Players order.
     *
     * @param game
     * @return
     */
    public PlayedGame startGame(PlayedGame game) {
        Round round = new Round();
        round.setRoundManager(new RoundManager());
        List<Player> players = game.getPlayers();
        Collections.shuffle(players);
        round.getRoundManager().setPlayers(round, players);
        Player startingPlayer = players.get(0);
        round.getRoundManager().setActivePlayer(round, startingPlayer);
        game.getGameManager().startGame(game, round);
        return playedGameRepository.save(game);
    }

    /**
     * Add new Player to PlayedGame's players list.
     *
     * @param game
     * @param player
     * @return
     */
    public PlayedGame addPlayer(PlayedGame game, Player player) {
        GameManager gameManager = game.getGameManager();
        gameManager.addPlayerToGame(game, player);
        return playedGameRepository.save(game);
    }

    /**
     * Helping method to update list of players in PlayedGame with updated player.
     *
     * @param game
     * @param player
     * @return
     */
    private PlayedGame updatePlayer(PlayedGame game, Player player) {
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getLogin(), player.getLogin()))
                .findFirst();
        if (index.isEmpty())
            return game;
        players.set(index.getAsInt(), player);
        game.getGameManager().setPlayers(game, players);
        return game;
    }

    /**
     * Helping method to update list of cards deck in PlayedGame with updated card.
     *
     * @param game
     * @param card
     * @return
     */
    private PlayedGame updateCardDeck(PlayedGame game, Card card) {
        List<Card> cards = game.getCardDeck();
        OptionalInt index = IntStream.range(0, cards.size())
                .filter(i -> Objects.equals(cards.get(i).getId(), card.getId()))
                .findFirst();
        if (index.isEmpty())
            return game;
        cards.set(index.getAsInt(), card);
        game.getGameManager().setCardDeck(game, cards);
        return game;
    }

    /**
     * Helping method to update list of fields on board in PlayedGame with updated field.
     *
     * @param game
     * @param field
     * @return
     */
    private PlayedGame updateField(PlayedGame game, Field field) {
        List<Field> fields = game.getBoard().getFieldsOnBoard();
        OptionalInt index = IntStream.range(0, fields.size())
                .filter(i -> Objects.equals(fields.get(i).getId(), field.getId()))
                .findFirst();
        if (index.isEmpty())
            return game;
        fields.set(index.getAsInt(), field);
        PlayedBoard board = new PlayedBoard();
        board.setFieldsOnBoard(fields);
        game.getGameManager().setBoard(game, board);
        return game;
    }

    /**
     * Add Character to Player's played character.
     *
     * @param game
     * @param player
     * @param character
     * @return
     */
    public PlayedGame setCharacterToPlayer(PlayedGame game, Player player, Character character) {
        Player updatedPlayer = player.getPlayerManager().setCharacter(player, character);
        PlayedGame updatedGame = updatePlayer(game, updatedPlayer);
        return playedGameRepository.save(updatedGame);
    }

    /**
     * Set roll value to Player's fight roll value.
     *
     * @param game
     * @param player
     * @param roll
     * @return
     */
    public PlayedGame setPlayerFightRoll(PlayedGame game, Player player, Integer roll) {
        Player updatedPlayer = player.getPlayerManager().setFightRoll(player, roll);
        PlayedGame updatedGame = updatePlayer(game, updatedPlayer);
        return playedGameRepository.save(updatedGame);
    }

    /**
     * Removes card from Card Deck and adds to Used Card Deck.
     *
     * @param game
     * @param card
     * @return
     */

    public PlayedGame moveCardToUsed(PlayedGame game, Card card) {
        GameManager gameManager = game.getGameManager();
        gameManager.addCardToUsedDeck(game, card);
        gameManager.removeCardFromDeck(game, card);
        return playedGameRepository.save(game);
    }

    /**
     * Removes card from Card Deck and adds to Player's cards on hand.
     *
     * @param game
     * @param card
     * @param player
     * @return
     */
    public PlayedGame moveCardToPlayer(PlayedGame game, Card card, Player player) {
        GameManager gameManager = game.getGameManager();
        Player updatedPlayer = player.getPlayerManager().moveCardToPlayer(player, card);
        gameManager.removeCardFromDeck(game, card);
        PlayedGame updatedGame = updatePlayer(game, updatedPlayer);
        return playedGameRepository.save(updatedGame);
    }

    /**
     * Removes card from Card Deck and adds to Player's trophies.
     *
     * @param game
     * @param card
     * @param player
     * @return
     */
    public PlayedGame moveCardToTrophies(PlayedGame game, Card card, Player player) {
        GameManager gameManager = game.getGameManager();
        Player updatedPlayer = player.getPlayerManager().moveCardToTrophies(player, card);
        gameManager.removeCardFromDeck(game, card);
        PlayedGame updatedGame = updatePlayer(game, updatedPlayer);
        return playedGameRepository.save(updatedGame);
    }

    /**
     * Removes card from Players' hand and adds to Used Card Deck.
     *
     * @param game
     * @param player
     * @param card
     * @return
     */
    public PlayedGame moveCardFromPlayer(PlayedGame game, Player player, Card card) {
        Player updatedPlayer = player.getPlayerManager().removeCardFromPlayer(player, card);
        game.getGameManager().addCardToUsedDeck(game, card);
        PlayedGame updatedGame = updatePlayer(game, updatedPlayer);
        return playedGameRepository.save(game);
    }

    /**
     * Check if Player has enough trophies to get Strength point.
     * If so, increases Strength points and removes trophies from Player.
     *
     * @param game
     * @param player
     * @return
     */
    public PlayedGame checkTrophies(PlayedGame game, Player player) {
        if(player.getPlayerManager().checkTrophies(player)) {
            Player updatedPlayer = player.getPlayerManager().moveAndIncreaseTrophies(player);
            PlayedGame updatedGame = updatePlayer(game, updatedPlayer);
            return playedGameRepository.save(updatedGame);
        }
        return playedGameRepository.save(game);
    }

    /**
     * Change Player's character position Field.
     *
     * @param game
     * @param player
     * @param field
     * @return
     */
    public PlayedGame changePosition(PlayedGame game, Player player, Character character, Field field) {
        player.getPlayerManager().changeCharacterPosition(player, field);
        PlayedGame updatedGame = updatePlayer(game, player);
        return playedGameRepository.save(updatedGame);
    }

    /**
     * Checks type of field the player stands on, returns list of possible options on that field.
     *
     * @param game
     * @param player
     * @param field
     * @return
     */
    public FieldOptionList checkField(PlayedGame game, Player player, Field field) {
        FieldOptionList list = new FieldOptionList();
        list.getPossibleOptions().add(FieldOption.valueOf(field.getType().toString()));
        System.out.println("IN7");
        Optional<Player> enemy = findDifferentPlayerByField(game.getId(), player.getLogin(), field.getId());
        System.out.println("IN8");
        if (enemy.isEmpty())
            return list;
        list.getPossibleOptions().add(FieldOption.FIGHT_WITH_PLAYER);
        return list;
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
     * Method to calculate fight result between Player and Enemy from card or from field.
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
        Integer enemyResult = enemy.getCardManager().calculateTotalStrength(enemy) + enemyRoll;
        System.out.println("ENEMY " + enemyResult);
        if (playerResult >= enemyResult) {
            return true;
        }
        return false;
    }

    /**
     * Method to calculate fight result between Player and Player.
     *
     * @param game
     * @param player
     * @param enemy
     * @param playerRoll
     * @param enemyRoll
     * @return
     */
    public boolean calculateFight(PlayedGame game, Player player, Player enemy, Integer playerRoll, Integer enemyRoll) {
        Integer playerResult = player.getPlayerManager().calculateTotalStrength(player) + playerRoll;
        System.out.println("PLAYER " + playerResult);
        Integer enemyResult = enemy.getPlayerManager().calculateTotalStrength(enemy) + enemyRoll;
        System.out.println("ENEMY PLAYER " + enemyResult);
        if (playerResult >= enemyResult) {
            return true;
        }
        return false;
    }

    /**
     * Method to decrease health points of player by value (1).
     *
     * @param game
     * @param player
     * @param val
     * @return
     */
    public PlayedGame decreaseHealth(PlayedGame game, Player player, Integer val) {
        Optional<ItemCard> card = player.getCardsOnHand().stream().filter(itemCard -> itemCard.getCardManager().calculateHealth(itemCard) > 0).findFirst();
        if (card.isEmpty()) {
            // no health cards
            player.getPlayerManager().addHealth(player, val);
        } else {
            // decrease health card
            card.get().getCardManager().addHealth(card.get(), val);
            if (card.get().getCardManager().calculateHealth(card.get()) <= 0) { // remove used up card
                moveCardFromPlayer(game, player, card.get());
            }
        }
        PlayedGame updatedGame = updatePlayer(game, player);
        return playedGameRepository.save(updatedGame);
    }

    /**
     * Method to decrease health points of enemy from card by value (1).
     *
     * @param game
     * @param player
     * @param enemyCard
     * @param val
     * @return
     */
    public PlayedGame decreaseHealth(PlayedGame game, Player player, EnemyCard enemyCard, Integer val) {
        EnemyCard updatedEnemy = enemyCard.getCardManager().addHealth(enemyCard, val);
        if (updatedEnemy.getCardManager().calculateTotalHealth(updatedEnemy) <= 0) {
            PlayedGame updatedGame = moveCardToTrophies(game, updatedEnemy, player);
            updatedGame = checkTrophies(updatedGame, player);
            return playedGameRepository.save(updatedGame);
        }
        PlayedGame updatedGame = updateCardDeck(game, updatedEnemy);
        return playedGameRepository.save(updatedGame);
    }

    /**
     * Method to decrease health points of enemy from field by value (1).
     *
     * @param game
     * @param player
     * @param enemyCard
     * @param val
     * @return
     */
    public PlayedGame decreaseHealth(PlayedGame game, Player player, Field field, EnemyCard enemyCard, Integer val) {
        EnemyCard updatedEnemy = enemyCard.getCardManager().addHealth(enemyCard, val);
        Field updatedField = field.getFieldManager().setEnemy(field, updatedEnemy);
        PlayedGame updatedGame = updateField(game, updatedField);
        return playedGameRepository.save(updatedGame);
    }

    /**
     * Method to return random Integer value for roll of the dice.
     *
     * @return
     */
    public Integer rollDice() {
        Random randomId = new Random();
        return randomId.nextInt(PlayedGameApplication.lowDiceBound, PlayedGameApplication.upDiceBound + 1);
    }


}






















