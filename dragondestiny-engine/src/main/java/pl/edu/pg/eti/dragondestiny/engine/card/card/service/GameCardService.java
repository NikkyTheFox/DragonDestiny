package pl.edu.pg.eti.dragondestiny.engine.card.card.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.CardList;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.service.EnemyCardService;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.service.ItemCardService;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.engine.game.service.GameService;

import java.util.List;
import java.util.Optional;

@Service
public class GameCardService {

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
     * @param cardService Service for data retrieval and manipulation.
     * @param enemyCardService Service for data retrieval and manipulation.
     * @param itemCardService Service for data retrieval and manipulation.
     * @param gameService Service for data retrieval and manipulation.
     */
    @Autowired
    public GameCardService(CardService cardService, EnemyCardService enemyCardService, ItemCardService itemCardService, GameService gameService) {
        this.cardService = cardService;
        this.enemyCardService = enemyCardService;
        this.itemCardService = itemCardService;
        this.gameService = gameService;
    }

    /**
     * Get all cards from the given game.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of cards.
     */
    public Optional<CardList> getGameCards(Integer gameId){
        Optional<Game> game = gameService.findGame(gameId);
        if(game.isEmpty()){
            return Optional.empty();
        }
        List<Card> cardList = cardService.findCards(game.get());
        if(cardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new CardList(cardList));
    }

    /**
     * Retrieves a specified card from the game data.
     *
     * @param gameId An identifier of a game.
     * @param cardId An identifier of a card.
     * @return A retrieved card.
     */
    public Optional<Card> getGameCard(Integer gameId, Integer cardId){
        Optional<Game> game = gameService.findGame(gameId);
        if(game.isEmpty()){
            return Optional.empty();
        }
        return cardService.findCard(game.get(), cardId);
    }

    /**
     * Retrieves all enemy cards from the specified game.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of enemy cards.
     */
    public Optional<EnemyCardList> getGameEnemyCards(Integer gameId){
        Optional<Game> game = gameService.findGame(gameId);
        if(game.isEmpty()){
            return Optional.empty();
        }
        List<EnemyCard> enemyCardList = enemyCardService.findEnemyCards(game.get());
        if(enemyCardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new EnemyCardList(enemyCardList));
    }

    /**
     * Retrieves all item cards from the specified game.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of item cards.
     */
    public Optional<ItemCardList> getGameItemCards(Integer gameId){
        Optional<Game> game = gameService.findGame(gameId);
        if(game.isEmpty()){
            return Optional.empty();
        }
        List<ItemCard> itemCardList = itemCardService.findItemCards(game.get());
        if(itemCardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new ItemCardList(itemCardList));
    }

    /**
     * Converts CardList into CardListDTO.
     *
     * @param modelMapper Mapper allowing conversion.
     * @param cardList A structure containing list of cards.
     * @return A DTO.
     */
    public CardListDTO convertCardListToDTO(ModelMapper modelMapper, CardList cardList){
        return cardService.convertCardListToDTO(modelMapper, cardList);
    }

    /**
     * Converts EnemyCardList into EnemyCardListDTO.
     *
     * @param modelMapper Mapper allowing conversion.
     * @param enemyCardList A structure containing list of enemy cards.
     * @return A DTO.
     */
    public EnemyCardListDTO convertEnemyCardListToDTO(ModelMapper modelMapper, EnemyCardList enemyCardList){
        return enemyCardService.convertEnemyCardListToDTO(modelMapper, enemyCardList);
    }

    /**
     * Converts ItemCardList into ItemCardListDTO.
     *
     * @param modelMapper Mapper allowing conversion.
     * @param itemCardList A structure containing list of item cards.
     * @return A DTO.
     */
    public ItemCardListDTO convertItemCardListToDTO(ModelMapper modelMapper, ItemCardList itemCardList){
        return itemCardService.convertItemCardListToDTO(modelMapper, itemCardList);
    }
}
