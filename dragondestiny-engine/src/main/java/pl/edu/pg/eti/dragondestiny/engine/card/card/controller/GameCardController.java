package pl.edu.pg.eti.dragondestiny.engine.card.card.controller;

import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.CardList;
import pl.edu.pg.eti.dragondestiny.engine.card.card.service.GameCardService;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCardList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Represents REST controller, allows to handle requests to get cards in game.
 * Requests go through /api/games/{gameId}/cards - represent cards found in game of id gameId.
 */

@RestController
@RequestMapping(value = {"/api/games/{gameId}/cards"})
public class GameCardController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * GameCardService used to communicate with service layer that will communicate with database repository.
     */
    private final GameCardService gameCardService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param modelMapper Mapper allowing conversion from objects to DTOs.
     * @param gameCardService Service for data retrieval and manipulation.
     */
    @Autowired
    public GameCardController(ModelMapper modelMapper, GameCardService gameCardService) {
        this.modelMapper = modelMapper;
        this.gameCardService = gameCardService;
    }

    /**
     * Retrieves all cards added to game of specified ID.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of cards.
     */
    @GetMapping
    public ResponseEntity<CardListDTO> getCards(@PathVariable("gameId") Integer gameId) {
        Optional<CardList> cardList = gameCardService.getGameCards(gameId);
        return cardList.map(list -> ResponseEntity.ok().body(gameCardService.convertCardListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves card by its ID that is added to game of specified ID.
     *
     * @param gameId An identifier of a game.
     * @param cardId An identifier of a card.
     * @return Retrieved card.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCard(@PathVariable("gameId") Integer gameId, @PathVariable(name = "id") Integer cardId) {
        Optional<Card> card = gameCardService.getGameCard(gameId, cardId);
        return card.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all cards of type EnemyCard added to game of specified ID.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of enemy cards.
     */
    @GetMapping("/enemy")
    public ResponseEntity<EnemyCardListDTO> getEnemyCards(@PathVariable("gameId") Integer gameId) {
        Optional<EnemyCardList> enemyCardList = gameCardService.getGameEnemyCards(gameId);
        return enemyCardList.map(cardList -> ResponseEntity.ok().body(gameCardService.convertEnemyCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all card of type ItemCard added to game of specified ID.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of item cards.
     */
    @GetMapping("/item")
    public ResponseEntity<ItemCardListDTO> getItemCards(@PathVariable("gameId") Integer gameId) {
        Optional<ItemCardList> itemCardList = gameCardService.getGameItemCards(gameId);
        return itemCardList.map(cardList -> ResponseEntity.ok().body(gameCardService.convertItemCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}