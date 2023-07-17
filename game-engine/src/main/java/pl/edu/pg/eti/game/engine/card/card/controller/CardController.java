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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Autowired constructor - beans are injected automatically.
     *
     * @param cardService
     * @param enemyCardService
     * @param itemCardService
     * @param modelMapper
     */
    @Autowired
    public CardController(CardService cardService, EnemyCardService enemyCardService, ItemCardService itemCardService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.enemyCardService = enemyCardService;
        this.itemCardService = itemCardService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all cards.
     *
     * @return list of CardDTOs
     */
    @GetMapping()
    public ResponseEntity<CardListDTO> getCards() {
        List<CardDTO> cardDTOList = cardService.findCards().stream()
                .map(card -> modelMapper.map(card, CardDTO.class))
                .collect(Collectors.toList());

        CardListDTO cardListDTO = new CardListDTO(cardDTOList);
        return ResponseEntity.ok().body(cardListDTO);
    }

    /**
     * Retrieve card by ID.
     *
     * @param id - identifier of card
     * @return ResponseEntity containing EnemyCardDTO or ItemCardDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCard(@PathVariable(name = "id") Integer id) {
        Optional<Card> card = cardService.findCard(id);
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(modelMapper.map(card.get(), CardDTO.class));
    }

    /**
     * Retrieve all cards of type EnemyCard.
     *
     * @return list of enemy cards
     */
    @GetMapping("/enemy")
    public ResponseEntity<EnemyCardListDTO> getEnemyCards() {
        List<EnemyCardDTO> enemyCardList = enemyCardService.findEnemyCards().stream()
                .map(card -> modelMapper.map(card, EnemyCardDTO.class))
                .collect(Collectors.toList());
        EnemyCardListDTO cardListDTO = new EnemyCardListDTO(enemyCardList);
        return ResponseEntity.ok().body(cardListDTO);
    }

    /**
     * Retrieve all cards of type ItemCard.
     *
     * @return list of item cards
     */
    @GetMapping("/item")
    public ResponseEntity<ItemCardListDTO> getItemCards() {
        List<ItemCardDTO> itemCardList = itemCardService.findItemCards().stream()
                .map(card -> modelMapper.map(card, ItemCardDTO.class))
                .collect(Collectors.toList());
        ItemCardListDTO cardListDTO = new ItemCardListDTO(itemCardList);
        return ResponseEntity.ok().body(cardListDTO);
    }
}
