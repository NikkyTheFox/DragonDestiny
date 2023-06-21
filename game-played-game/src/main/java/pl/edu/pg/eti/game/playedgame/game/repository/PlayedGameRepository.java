package pl.edu.pg.eti.game.playedgame.game.repository;

import org.bouncycastle.crypto.signers.PlainDSAEncoding;
import pl.edu.pg.eti.game.playedgame.board.entity.PlayedBoard;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGame;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MongoRepository interface with domain type PlayedGame and ObjectId as id
 */

@Repository
public interface PlayedGameRepository extends MongoRepository<PlayedGame, String> {

    default Optional<Card> findCardByIdInUsedDeck(String gameId, Integer cardId) {
        return findById(gameId)
                .map(PlayedGame::getUsedCardDeck)
                .flatMap(cardList -> cardList.stream()
                        .filter(card -> card.getId().equals(cardId))
                        .findFirst());
    }

    default Optional<Card> findCardByIdInCardDeck(String gameId, Integer cardId) {
        return findById(gameId)
                .map(PlayedGame::getCardDeck)
                .flatMap(cardList -> cardList.stream()
                        .filter(card -> card.getId().equals(cardId))
                        .findFirst());
    }

    default Optional<Player> findPlayerInPlayers(String gameId, String playerLogin) {
        return findById(gameId)
                .map(PlayedGame::getPlayers)
                .flatMap(playerList -> playerList.stream()
                        .filter(player -> player.getLogin().equals(playerLogin))
                        .findFirst());
    }

    default Optional<Player> findPlayerByField(String gameId, Integer fieldID) {
        return findById(gameId)
                .map(PlayedGame::getPlayers)
                .flatMap(players -> players.stream()
                        .filter(player -> player.getCharacter().getPositionField().getId().equals(fieldID))
                        .findFirst());
    }

    default Optional<Player> findDifferentPlayerByField(String gameId, String playerId, Integer fieldID) {
        return findById(gameId)
                .map(PlayedGame::getPlayers)
                .flatMap(players -> players.stream()
                        .filter(player -> player.getCharacter() != null)
                        .filter(player -> player.getCharacter().getPositionField().getId().equals(fieldID))
                        .filter(player -> !player.getLogin().equals(playerId))
                        .findFirst());
    }

    default Optional<ItemCard> findCardInPlayers(String gameId, String playerLogin, Integer cardId) {
        return findById(gameId)
                .map(PlayedGame::getPlayers)
                .flatMap(playerList -> playerList.stream()
                        .filter(player -> player.getLogin().equals(playerLogin))
                        .findFirst())
                .map(Player::getCardsOnHand)
                .flatMap(cardList -> cardList.stream()
                        .filter(card -> card.getId().equals(cardId))
                        .findFirst());
    }

    default Optional<Character> findCharacterInCharacters(String gameId, Integer characterId) {
        return findById(gameId)
                .map(PlayedGame::getCharactersInGame)
                .flatMap(characterList -> characterList.stream()
                        .filter(character -> character.getId().equals(characterId))
                        .findFirst());
    }


    default PlayedBoard findBoard(String gameId) {
        return findById(gameId)
                .map(PlayedGame::getBoard)
                .get();
    }

    default List<Field> findFieldsOnBoard(String gameId) {
        return findById(gameId)
                .map(PlayedGame::getBoard)
                .map(PlayedBoard::getFieldsOnBoard)
                .get();
    }

    default Optional<Field> findFieldOnBoard(String gameId, Integer fieldId) {
        return findById(gameId)
                .map(PlayedGame::getBoard)
                .map(PlayedBoard::getFieldsOnBoard)
                .flatMap(fieldList -> fieldList.stream()
                        .filter(field -> field.getId().equals(fieldId))
                        .findFirst());
    }

}
