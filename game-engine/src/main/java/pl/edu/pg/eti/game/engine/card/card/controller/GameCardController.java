package pl.edu.pg.eti.game.engine.card.card.controller;

import pl.edu.pg.eti.game.engine.card.card.dto.CardDTO;
import pl.edu.pg.eti.game.engine.card.card.dto.CardListDTO;
import pl.edu.pg.eti.game.engine.card.card.entity.Card;
import pl.edu.pg.eti.game.engine.card.card.service.CardService;
import pl.edu.pg.eti.game.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.game.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.game.engine.card.enemycard.service.EnemyCardService;
import pl.edu.pg.eti.game.engine.card.itemcard.dto.ItemCardDTO;
import pl.edu.pg.eti.game.engine.card.itemcard.dto.ItemCardListDTO;
import pl.edu.pg.eti.game.engine.card.itemcard.service.ItemCardService;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import pl.edu.pg.eti.game.engine.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * CardService used to communicate with service layer that will communicate with database repository.
     */
    private final CardService cardService;

    /**
     * EnemyCardService used to communicate with service layer that will communicate with database repository.
     */
    private final EnemyCardService enemyCardService;

    /**
     * ItemCardRepository used to communicate with service layer that will communicate with database repository.
     */
    private final ItemCardService itemCardService;

    /**
     * GameService used to communicate with service layer that will communicate with database repository.
     */
    private final GameService gameService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param cardService
     * @param enemyCardService
     * @param itemCardService
     * @param gameService
     * @param modelMapper
     */
    @Autowired
    public GameCardController(CardService cardService, EnemyCardService enemyCardService, ItemCardService itemCardService, GameService gameService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.enemyCardService = enemyCardService;
        this.itemCardService = itemCardService;
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all cards added to game of gameId ID.
     *
     * @param gameId - game identifier
     * @return list of cards in game
     */
    @GetMapping
    public ResponseEntity<CardListDTO> getCards(@PathVariable("gameId") Integer gameId) {
        // find game
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find cards in game
        List<CardDTO> cardDTOList = cardService.findCards(game.get()).stream()
                .map(card -> modelMapper.map(card, CardDTO.class))
                .collect(Collectors.toList());
        CardListDTO cardListDTO = new CardListDTO(cardDTOList);
        return ResponseEntity.ok().body(cardListDTO);
    }

    /**
     * Retrieve card by its ID that is added to game of gameId ID.
     *
     * @param id - identifier of card
     * @param gameId - game identifier
     * @return ResponseEntity containing EnemyCardDTO or ItemCardDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCard(@PathVariable(name = "id") Integer id, @PathVariable("gameId") Integer gameId) {
        // find game
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find card in game
        Optional<Card> card = cardService.findCard(game.get(), id);
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(modelMapper.map(card.get(), CardDTO.class));
    }

    /**
     * Retrieve all cards of type EnemyCard added to game of gameId ID.
     *
     * @param gameId - game identifier
     * @return list of EnemyCardDTOs
     */
    @GetMapping("/enemy")
    public ResponseEntity<EnemyCardListDTO> getEnemyCards(@PathVariable("gameId") Integer gameId) {
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        List<EnemyCardDTO> cardDTOList = enemyCardService.findEnemyCards(game.get()).stream()
                .map(card -> modelMapper.map(card, EnemyCardDTO.class))
                .collect(Collectors.toList());
        EnemyCardListDTO cardListDTO = new EnemyCardListDTO(cardDTOList);
        return ResponseEntity.ok().body(cardListDTO);
    }

    /**
     * Retrieve all card of type ItemCard added to game of gameId ID.
     *
     * @param gameId - game identifier
     * @return list of ItemCardDTOs
     */
    @GetMapping("/item")
    public ResponseEntity<ItemCardListDTO> getItemCards(@PathVariable("gameId") Integer gameId) {
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        List<ItemCardDTO> cardDTOList = itemCardService.findItemCards(game.get()).stream()
                .map(card -> modelMapper.map(card, ItemCardDTO.class))
                .collect(Collectors.toList());
        ItemCardListDTO cardListDTO = new ItemCardListDTO(cardDTOList);
        return ResponseEntity.ok().body(cardListDTO);
    }

}