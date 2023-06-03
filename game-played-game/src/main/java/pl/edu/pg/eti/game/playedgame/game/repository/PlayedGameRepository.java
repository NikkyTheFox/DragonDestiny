package pl.edu.pg.eti.game.playedgame.game.repository;

import pl.edu.pg.eti.game.playedgame.board.entity.PlayedBoard;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGame;
import pl.edu.pg.eti.game.playedgame.player.Player;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * MongoRepository interface with domain type PlayedGame and ObjectId as id
 */

@Repository
public interface PlayedGameRepository extends MongoRepository<PlayedGame, ObjectId> {

    default Optional<Card> findCardByIdInUsedDeck(ObjectId gameId, Integer cardId) {
        return findById(gameId)
                .map(PlayedGame::getUsedCardDeck)
                .flatMap(cardList -> cardList.stream()
                        .filter(card -> card.getId().equals(cardId))
                        .findFirst());
    }

    default Optional<Card> findCardByIdInCardDeck(ObjectId gameId, Integer cardId) {
        return findById(gameId)
                .map(PlayedGame::getCardDeck)
                .flatMap(cardList -> cardList.stream()
                        .filter(card -> card.getId().equals(cardId))
                        .findFirst());
    }

    default Optional<Player> findPlayerInPlayers(ObjectId gameId, Integer playerId) {
        return findById(gameId)
                .map(PlayedGame::getPlayers)
                .flatMap(playerList -> playerList.stream()
                        .filter(player -> player.getId().equals(playerId))
                        .findFirst());
    }

    default Optional<ItemCard> findCardInPlayers(ObjectId gameId, Integer playerId, Integer cardId) {
        return findById(gameId)
                .map(PlayedGame::getPlayers)
                .flatMap(playerList -> playerList.stream()
                        .filter(player -> player.getId().equals(playerId))
                        .findFirst())
                .map(Player::getCardsOnHand)
                .flatMap(cardList -> cardList.stream()
                        .filter(card -> card.getId().equals(cardId))
                        .findFirst());
    }

    default Optional<Character> findCharacterInCharacters(ObjectId gameId, Integer characterId) {
        return findById(gameId)
                .map(PlayedGame::getCharactersInGame)
                .flatMap(characterList -> characterList.stream()
                        .filter(character -> character.getId().equals(characterId))
                        .findFirst());
    }

    default Optional<Field> findFieldOnBoard(ObjectId gameId, Integer fieldId) {
        return findById(gameId)
                .map(PlayedGame::getBoard)
                .map(PlayedBoard::getFieldsOnBoard)
                .flatMap(fieldList -> fieldList.stream()
                        .filter(field -> field.getId().equals(fieldId))
                        .findFirst());
    }

}
