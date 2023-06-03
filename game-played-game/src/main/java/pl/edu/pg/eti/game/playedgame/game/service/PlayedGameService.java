package pl.edu.pg.eti.game.playedgame.game.service;

import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.game.entity.GameManager;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGame;
import pl.edu.pg.eti.game.playedgame.game.repository.PlayedGameRepository;
import pl.edu.pg.eti.game.playedgame.player.Player;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
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
    public Optional<PlayedGame> findPlayedGame(ObjectId id) {
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
    public void deleteById(ObjectId id) {
        playedGameRepository.findById(id).orElseThrow(() -> new RuntimeException("No game found"));
        playedGameRepository.deleteById(id);
    }

    /**
     * Updates played game in the database.
     *
     * @return updated game
     */
    public PlayedGame update(ObjectId id, PlayedGame playedGameRequest) {
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

    public Optional<Card> findCardInCardDeck(ObjectId gameId, Integer id) {
        return playedGameRepository.findCardByIdInCardDeck(gameId, id);
    }

    public Optional<Card> findCardInUsedDeck(ObjectId gameId, Integer id) {
        return playedGameRepository.findCardByIdInUsedDeck(gameId, id);
    }

    public Optional<ItemCard> findCardInPlayer(ObjectId gameId, Integer playerId, Integer cardId) {
        return playedGameRepository.findCardInPlayers(gameId, playerId, cardId);
    }

    public Optional<Player> findPlayer(ObjectId gameId, Integer id) {
        return playedGameRepository.findPlayerInPlayers(gameId, id);
    }

    public Optional<Character> findCharacter(ObjectId gameId, Integer id) {
        return playedGameRepository.findCharacterInCharacters(gameId, id);
    }

    public Optional<Field> findField(ObjectId gameId, Integer id) {
        return playedGameRepository.findFieldOnBoard(gameId, id);
    }

    public PlayedGame moveCardToUsed(PlayedGame game, Card card) {
        GameManager gameManager = game.getGameManager();
        gameManager.addCardToUsedDeck(card, game.getUsedCardDeck());
        gameManager.removeCardFromDeck(card, game.getCardDeck());
        return playedGameRepository.save(game);
    }

    public PlayedGame moveCardToPlayer(PlayedGame game, Card card, Player player) {
        GameManager gameManager = game.getGameManager();
        player.getPlayerManager().moveCardToPlayer(player, card);
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getId(), player.getId()))
                .findFirst();
        if (index.isEmpty())
            return null;
        players.set(index.getAsInt(), player);
        game.setPlayers(players);
        gameManager.removeCardFromDeck(card, game.getCardDeck());
        return playedGameRepository.save(game);
    }

    public PlayedGame addPlayer(PlayedGame game, Player player) {
        GameManager gameManager = game.getGameManager();
        gameManager.addPlayerToGame(player, game.getPlayers());
        return playedGameRepository.save(game);
    }

    public PlayedGame setCharacterToPlayer(PlayedGame game, Player player, Character character) {
        player.getPlayerManager().setCharacter(player, character);
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getId(), player.getId()))
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
                .filter(i -> Objects.equals(players.get(i).getId(), player.getId()))
                .findFirst();
        if (index.isEmpty())
            return null;
        players.set(index.getAsInt(), player);
        game.setPlayers(players);
        return playedGameRepository.save(game);
    }

}
