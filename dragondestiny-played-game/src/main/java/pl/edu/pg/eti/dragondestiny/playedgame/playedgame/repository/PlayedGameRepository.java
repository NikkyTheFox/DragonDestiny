package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.Character;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGame;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.Round;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MongoRepository interface with domain type PlayedGame and ObjectId as id
 */
@Repository
public interface PlayedGameRepository extends MongoRepository<PlayedGame, String> {

    /*
    Usage of List<Object> instead of Object when we try to retrieve only single document
     allows for easier existence check in the Service.
     .stream().findFirst() returns Optional, no additional, manual checks of successful retrieval are needed.
     */

    /**
     * Retrieves a list of cards in the deck.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @return A list of cards.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$cardDeck'}",
            "{$project: {_id: 0, card: '$cardDeck'}}",
            "{$replaceRoot: {newRoot: '$card'}}"
    })
    List<Card> findCardDeck(String playedGameId);

    /**
     * Retrieves list of cards in the used cards deck.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @return A list of cards.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$usedCardDeck'}",
            "{$project: {_id: 0, card: '$usedCardDeck'}}",
            "{$replaceRoot: {newRoot: '$card'}}"
    })
    List<Card> findUsedCardDeck(String playedGameId);

    /**
     * Retrieves specified card from the used cards deck.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param cardId An identifier of a card to be retrieved.
     * @return A list containing retrieved card.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$usedCardDeck'}",
            "{$match: {'usedCardDeck._id': ?1}}",
            "{$project: {_id: 0, card: '$usedCardDeck'}}",
            "{$replaceRoot: {newRoot: '$card'}}"
    })
    List<Card> findCardByIdInUsedDeck(String playedGameId, Integer cardId);

    /**
     * Retrieves specified card from the card deck.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param cardId An identifier of a card to be retrieved.
     * @return A list containing retrieved card.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$cardDeck'}",
            "{$match: {'cardDeck._id': ?1}}",
            "{$project: {_id: 0, card: '$cardDeck'}}",
            "{$replaceRoot: {newRoot: '$card'}}"

    })
    List<Card> findCardByIdInCardDeck(String playedGameId, Integer cardId);

    /**
     * Retrieves specified card from the deck by its index.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param index An index of card to be retrieved.
     * @return A list containing retrieved card.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$cardDeck'}",
            "{$skip: ?1}",
            "{$limit: 1}",
            "{$project: {_id: 0, card: '$cardDeck'}}",
            "{$replaceRoot: {newRoot: '$card'}}"
    })
    List<Card> findCardByIndexInCardDeck(String playedGameId, Integer index);

    /**
     * Retrieves list of all players participating in the specified game.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @return A list of players.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$players'}",
            "{$project: {_id: 0, players: 1}}",
            "{$replaceRoot: {newRoot: '$players'}}"
    })
    List<Player> findPlayers(String playedGameId);

    /**
     * Retrieves specified player from players participating in the game.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param playerLogin An identifier of a player to be retrieved.
     * @return A list containing retrieved player.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$players'}",
            "{$match: {'players._id': ?1}}",
            "{$project: {_id: 0, player: '$players'}}",
            "{$replaceRoot: {newRoot: '$player'}}"
    })
    List<Player> findPlayerByLogin(String playedGameId, String playerLogin);

    /**
     * Retrieves all players whose character stands on a field given by ID.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param FieldId An identifierof a field to be checked.
     * @return A list of players.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$players'}",
            "{$unwind: '$players.character.field'}",
            "{$match: {'players.character.field._id': ?1}}",
            "{$project: {_id: 0, players: 1}}",
            "{$replaceRoot: {newRoot: '$players'}}"
    })
    List<Player> findPlayersByField(String playedGameId, Integer FieldId);

    /**
     * Retrieves different (other than specified) players whose character stands on a specified field.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param playerLogin An identifier of a player to be excluded from the list.
     * @param fieldId An identifier of a field to be checked.
     * @return A list of players.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$players'}",
            "{$unwind: '$players.character.field'}",
            "{$match: {'players.character.field._id': ?2}}",
            "{$match: {'players._id': {$ne: ?1}}}",
            "{$project: {_id: 0, players: 1}}",
            "{$replaceRoot: {newRoot: '$players'}}"
    })
    List<Player> findDifferentPlayersByField(String playedGameId, String playerLogin, Integer fieldId);

    /**
     * Retrieves specified hand card of a given player.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param playerLogin An identifier of a player whose hand cards are to be filtered.
     * @param cardId An identifier of a card to be retrieved.
     * @return A list containing retrieved item card.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$players'}",
            "{$match: {'players._id': ?1}}",
            "{$unwind: '$players.cardsOnHand'}",
            "{$match: {'players.cardsOnHand._id': ?2}}",
            "{$project: {_id: 0, card: '$players.cardsOnHand'}}",
            "{$replaceRoot: {newRoot: '$card'}}"
    })
    List<ItemCard> findCardByIdInPlayerHand(String playedGameId, String playerLogin, Integer cardId);

    /**
     * Retrieves all cards held by specified player.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param playerLogin An identifier of a player whose hand cards are to be retrieved.
     * @return A list of cards.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$players'}",
            "{$match: {'players._id': ?1}}",
            "{$unwind: '$players.cardsOnHand'}",
            "{$project: {_id: 0, card: '$players.cardsOnHand'}}",
            "{$replaceRoot: {newRoot: '$card'}}"
    })
    List<ItemCard> findCardsInPlayerHand(String playedGameId, String playerLogin);

    /**
     * Retrieves list of trophies (enemy cards) that belong to specified player.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param playerLogin An identifier of a player whose trophies are to be retrieved.
     * @return A list of enemy cards.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$players'}",
            "{$match: {'players._id': ?1}}",
            "{$unwind: '$players.trophies'}",
            "{$project: {_id: 0, trophy: '$players.trophies'}}",
            "{$replaceRoot: {newRoot: '$trophy'}}"
    })
    List<EnemyCard> findPlayersTrophies(String playedGameId, String playerLogin);

    /**
     * Retrieves and filters player's hand cards in order to find only strength buffing cards.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param playerLogin An identifier of a player whose hand cards are to be filtered.
     * @return A list of item cards.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$players'}",
            "{$match: {'players._id': ?1}}",
            "{$project: {_id: 0, healthCards: {$filter: {input: '$players.cardsOnHand', as: 'card', cond: {$gt: ['$$card.strength', 0]}}}}}",
            "{$unwind: '$healthCards'}",
            "{$replaceRoot: {newRoot: '$healthCards'}}"
    })
    List<ItemCard> findStrengthCardsInPlayerHand(String playedGameId, String playerLogin);

    /**
     * Retrieves and filters player's hand cards in order to find only health buffing cards.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param playerLogin An identifier of a player whose hand cards are to be filtered.
     * @return A list of item cards.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$players'}",
            "{$match: {'players._id': ?1}}",
            "{$project: {_id: 0, healthCards: {$filter: {input: '$players.cardsOnHand', as: 'card', cond: {$gt: ['$$card.health', 0]}}}}}",
            "{$unwind: '$healthCards'}",
            "{$replaceRoot: {newRoot: '$healthCards'}}"
    })
    List<ItemCard> findHealthCardsInPlayerHand(String playedGameId, String playerLogin);

    /**
     * Retrieves a list of all characters in the specified game.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @return A list of characters.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$charactersInGame'}",
            "{$project: {_id: 0, character: '$charactersInGame'}}",
            "{$replaceRoot: {newRoot: '$character'}}"
    })
    List<Character> findCharacters(String playedGameId);

    /**
     * Retrieves a character specified by its ID.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param characterId An identifier of a character to be retrieved.
     * @return A list containing retrieved character.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$charactersInGame'}",
            "{$match: {'charactersInGame._id': ?1}}",
            "{$project: {_id: 0, character: '$charactersInGame'}}",
            "{$replaceRoot: {newRoot: '$character'}}"

    })
    List<Character> findCharacterById(String playedGameId, Integer characterId);

    /**
     * Retrieves currently ongoing (active) round of a specified game.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @return A list containing retrieved round.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$project: {_id: 0, round: '$activeRound'}}",
            "{$replaceRoot: {newRoot: '$round'}}"
    })
    List<Round> findActiveRound(String playedGameId);

    /**
     * Retrieves board used in the specified game.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @return A retrieved board.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$project: {_id: 0, board: 1}}",
            "{$replaceRoot: {newRoot: '$board'}}"
    })
    List<PlayedBoard> findBoard(String playedGameId);

    /**
     * Retrieves list of fields used in the board of a specified game.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @return A list of fields.
     */
    @Aggregation(pipeline = {
            "{$match: {_id:  ObjectId(?0)}}",
            "{$unwind: '$board.fieldsOnBoard'}",
            "{$project: {_id: 0, field: '$board.fieldsOnBoard'}}",
            "{$replaceRoot: {newRoot: '$field'}}"
    })
    List<Field> findFieldsOnBoard(String playedGameId);

    /**
     * Retrieves specified field from the board of a game.
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param fieldId An identifier of a field to be retrieved.
     * @return A retrieved field.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$board.fieldsOnBoard'}",
            "{$match: {'board.fieldsOnBoard._id': ?1}}",
            "{$project: {_id: 0, field: '$board.fieldsOnBoard'}}",
            "{$replaceRoot: {newRoot: '$field'}}"
    })
    List<Field> findFieldOnBoard(String playedGameId, Integer fieldId);

    /**
     * Retrieves all enemy cards that are currently on a field given by ID.
     *
     * @param playedGameId An identifier of a game which data is to be retrieved.
     * @param fieldId An identifier of a field to be checked.
     * @return A list of enemy cards.
     */
    @Aggregation(pipeline = {
            "{$match: {_id: ObjectId(?0)}}",
            "{$unwind: '$board.fieldsOnBoard'}",
            "{$match: {'board.fieldsOnBoard._id': ?1}}",
            "{$unwind: '$board.fieldsOnBoard.enemy'}",
            "{$project: {_id: 0, enemy: '$board.fieldsOnBoard.enemy'}}",
            "{$replaceRoot: {newRoot: '$enemy'}}"
    })
    List<EnemyCard> findEnemyOnField(String playedGameId, Integer fieldId);
}
