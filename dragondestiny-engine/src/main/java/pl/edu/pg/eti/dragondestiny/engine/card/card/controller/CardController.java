package pl.edu.pg.eti.dragondestiny.engine.card.card.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.CardList;
import pl.edu.pg.eti.dragondestiny.engine.card.card.service.CardService;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.service.EnemyCardService;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.service.ItemCardService;

import java.util.Optional;

/**
 * Represents REST controller, allows to handle requests to get cards.
 * Requests go through /api/cards - they represent all cards, not only those assigned to games.
 */

@RestController
@RequestMapping(value = {"/api/cards"})
public class CardController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * EnemyCardService used to communicate with service layer that will communicate with database repository.
     */
    private final EnemyCardService enemyCardService;

    /**
     * ItemCardRepository used to communicate with service layer that will communicate with database repository.
     */
    private final ItemCardService itemCardService;

    /**
     * CardService used to communicate with service layer that will communicate with database repository.
     */
    private final CardService cardService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param cardService      Service to communicate with the database.
     * @param enemyCardService Service to communicate with the database.
     * @param itemCardService  Service to communicate with the database.
     * @param modelMapper      Mapper for transforming objects to DTOs.
     */
    @Autowired
    public CardController(CardService cardService, EnemyCardService enemyCardService, ItemCardService itemCardService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.enemyCardService = enemyCardService;
        this.itemCardService = itemCardService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves all cards.
     *
     * @return A structure containing a list of cards.
     */
    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    public ResponseEntity<CardListDTO> getCards() {
        Optional<CardList> cardList = cardService.getCards();
        return cardList.map(list -> ResponseEntity.ok().body(cardService.convertCardListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve card by ID.
     *
     * @param cardId An identifier of card.
     * @return A retrieved card.
     */
    @GetMapping("/{cardId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CardDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Card not found", content = @Content)})
    public ResponseEntity<CardDTO> getCard(@PathVariable(name = "cardId") Integer cardId) {
        Optional<Card> card = cardService.getCard(cardId);
        return card.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve all cards of type EnemyCard.
     *
     * @return A structure containing a list of enemy cards.
     */
    @GetMapping("/enemy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = EnemyCardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    public ResponseEntity<EnemyCardListDTO> getEnemyCards() {
        Optional<EnemyCardList> enemyCardList = enemyCardService.getEnemyCards();
        return enemyCardList.map(cardList -> ResponseEntity.ok().body(enemyCardService.convertEnemyCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve all cards of type ItemCard.
     *
     * @return A structure containing a list of item cards.
     */
    @GetMapping("/item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ItemCardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    public ResponseEntity<ItemCardListDTO> getItemCards() {
        Optional<ItemCardList> itemCardList = itemCardService.getItemCards();
        return itemCardList.map(cardList -> ResponseEntity.ok().body(itemCardService.convertItemCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
