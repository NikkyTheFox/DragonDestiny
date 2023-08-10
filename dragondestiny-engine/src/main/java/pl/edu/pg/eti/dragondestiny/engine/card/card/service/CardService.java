package pl.edu.pg.eti.dragondestiny.engine.card.card.service;

import org.modelmapper.ModelMapper;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.CardList;
import pl.edu.pg.eti.dragondestiny.engine.card.card.repository.CardRepository;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Card Service to communication with cards' data saved in database.
 */
@Service
public class CardService {

    /**
     * JPA repository communicating with database.
     */
    private final CardRepository cardRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param cardRepository Repository with methods to manipulate data in database.
     */
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;

    }

    /**
     * Returns card by ID.
     *
     * @param id The ID of a card to be retrieved.
     * @return A retrieved card.
     */
    public Optional<Card> findCard(Integer id) {

        return cardRepository.findById(id);
    }

    /**
     * Returns all cards found.
     *
     * @return A list of cards.
     */
    public List<Card> findCards() {

        return cardRepository.findAll();
    }

    /**
     * Returns all cards in particular game.
     *
     * @param game A game to find cards from.
     * @return A list of cards.
     */
    public List<Card> findCards(Game game) {

        return cardRepository.findAllByGames(game);
    }

    /**
     * Returns card of ID cardId found in particular game.
     *
     * @param game A game to find card from.
     * @param cardId The ID of card to be retrieved.
     * @return A retrieved card.
     */
    public Optional<Card> findCard(Game game, Integer cardId) {

        return cardRepository.findByGamesAndId(game, cardId);
    }

    /**
     * Retrieves all cards from the database.
     *
     * @return A structure containing list of cards.
     */
    public Optional<CardList> getCards(){
        List<Card> cardList = findCards();
        if(cardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new CardList(cardList));
    }

    /**
     * Concerts CardList into CardListDTO
     *
     * @param modelMapper A mapper allowing conversion.
     * @param cardList A structure containing list of cards.
     * @return A DTO.
     */
    public CardListDTO convertCardListToDTO(ModelMapper modelMapper, CardList cardList){
        List<CardDTO> cardDTOList = new ArrayList<>();
        cardList.getCardList().forEach(card -> {
            CardDTO cardDTO = modelMapper.map(card, CardDTO.class);
            cardDTOList.add(cardDTO);
        });
        return new CardListDTO(cardDTOList);
    }

}
