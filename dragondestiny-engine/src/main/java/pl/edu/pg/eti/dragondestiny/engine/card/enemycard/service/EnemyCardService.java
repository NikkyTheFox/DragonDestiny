package pl.edu.pg.eti.dragondestiny.engine.card.enemycard.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.repository.EnemyCardRepository;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Enemy Card Service to communication with enemy cards' data saved in database.
 */
@Service
public class EnemyCardService {

    /**
     * JPA repository communicating with database.
     */
    private final EnemyCardRepository enemyCardRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param enemyCardRepository Repository with methods for retrieval of data from database.
     */
    @Autowired
    public EnemyCardService(EnemyCardRepository enemyCardRepository) {

        this.enemyCardRepository = enemyCardRepository;
    }

    /**
     * Returns all enemy cards in particular game.
     *
     * @param game A game to find cards from.
     * @return A list of enemy cards in game.
     */
    public List<EnemyCard> getEnemyCardsByGame(Game game) {

        return enemyCardRepository.findAllByGames(game);
    }

    /**
     * Retrieves all enemy cards from the database.
     *
     * @return A structure containing list of enemy cards.
     */
    public Optional<EnemyCardList> getEnemyCards() {
        List<EnemyCard> enemyCardList = enemyCardRepository.findAll();
        if (enemyCardList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new EnemyCardList(enemyCardList));
    }

    /**
     * Converts EnemyCardList into EnemyCardListDTO.
     *
     * @param modelMapper   Mapper allowing conversion.
     * @param enemyCardList A structure containing a list of enemy cards.
     * @return A DTO.
     */
    public EnemyCardListDTO convertEnemyCardListToDTO(ModelMapper modelMapper, EnemyCardList enemyCardList) {
        List<EnemyCardDTO> enemyCardDTOList = new ArrayList<>();
        enemyCardList.getEnemyCardList().forEach(enemyCard -> {
            EnemyCardDTO enemyCardDTO = modelMapper.map(enemyCard, EnemyCardDTO.class);
            enemyCardDTOList.add(enemyCardDTO);
        });
        return new EnemyCardListDTO(enemyCardDTOList);
    }

}
