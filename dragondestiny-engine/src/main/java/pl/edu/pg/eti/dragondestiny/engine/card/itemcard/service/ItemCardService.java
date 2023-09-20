package pl.edu.pg.eti.dragondestiny.engine.card.itemcard.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.repository.ItemCardRepository;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Item Card Service to communication with item cards' data saved in database.
 */
@Service
public class ItemCardService {

    /**
     * JPA repository communicating with database.
     */
    private final ItemCardRepository itemCardRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param itemCardRepository Repository with methods for retrieval of data from database.
     */
    @Autowired
    public ItemCardService(ItemCardRepository itemCardRepository) {

        this.itemCardRepository = itemCardRepository;
    }

    /**
     * Returns all item cards in particular game.
     *
     * @param game A game to find cards from.
     * @return A list of item cards.
     */
    public List<ItemCard> getItemCardsByGame(Game game) {

        return itemCardRepository.findAllByGames(game);
    }

    /**
     * Retrieves all item cards from the database.
     *
     * @return A structure containing list of item cards.
     */
    public Optional<ItemCardList> getItemCards() {
        List<ItemCard> itemCardList = itemCardRepository.findAll();
        if (itemCardList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new ItemCardList(itemCardList));
    }

    /**
     * Converts ItemCardList into ItemCardListDTO.
     *
     * @param modelMapper  Mapper allowing conversion.
     * @param itemCardList A structure containing list of item cards.
     * @return A DTO.
     */
    public ItemCardListDTO convertItemCardListToDTO(ModelMapper modelMapper, ItemCardList itemCardList) {
        List<ItemCardDTO> itemCardDTOList = new ArrayList<>();
        itemCardList.getItemCardList().forEach(itemCard -> {
            ItemCardDTO itemCardDTO = modelMapper.map(itemCard, ItemCardDTO.class);
            itemCardDTOList.add(itemCardDTO);
        });
        return new ItemCardListDTO(itemCardDTOList);
    }

}
