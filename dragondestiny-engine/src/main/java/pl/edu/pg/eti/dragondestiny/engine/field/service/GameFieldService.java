package pl.edu.pg.eti.dragondestiny.engine.field.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;
import pl.edu.pg.eti.dragondestiny.engine.game.service.GameService;

import java.util.List;
import java.util.Optional;

/**
 * Game Field Service to communication with game's fields data saved in database.
 */
@Service
public class GameFieldService {

    /**
     * FieldService used to communicate with service layer that will communicate with database repository.
     */
    private final FieldService fieldService;


    /**
     * GameService used to communicate with service layer that will communicate with database repository.
     */
    private final GameService gameService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param gameService  Service for data retrieval and manipulation.
     * @param fieldService Service for data retrieval and manipulation.
     */
    @Autowired
    public GameFieldService(GameService gameService, FieldService fieldService) {
        this.gameService = gameService;
        this.fieldService = fieldService;
    }

    /**
     * Retrieves list of fields in the game specified by ID.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of fields.
     */
    public Optional<FieldList> getGameFields(Integer gameId){
        Optional<Board> board = gameService.getGameBoard(gameId);
        if(board.isEmpty()){
            return Optional.empty();
        }
        List<Field> fieldList = fieldService.getFieldsByBoard(board.get());
        return Optional.of(new FieldList(fieldList));
    }

    /**
     * Retrieves a field specified by ID.
     *
     * @param gameId An identifier of a game.
     * @param fieldId An identifier of a field to be retrieved.
     * @return A retrieved field.
     */
    public Optional<Field> getGameField(Integer gameId, Integer fieldId){
        Optional<Board> board = gameService.getGameBoard(gameId);
        if(board.isEmpty()){
            return Optional.empty();
        }
        return fieldService.getField(board.get(), fieldId);
    }

    /**
     * Retrieves enemy card from the specified field.
     *
     * @param gameId An identifier of a game.
     * @param fieldId An identifier of a field to be checked.
     * @return A retrieved enemy card.
     */
    public Optional<EnemyCard> getGameFieldEnemy(Integer gameId, Integer fieldId){
        Optional<Field> field = getGameField(gameId, fieldId);
        if(field.isEmpty() || field.get().getEnemy() == null){
            return Optional.empty();
        }
        return Optional.of(field.get().getEnemy());
    }

    /**
     * Converts FieldList into FieldListDTO.
     *
     * @param modelMapper Mapper allowing conversion.
     * @param fieldList A structure containing list of fields.
     * @return A DTO.
     */
    public FieldListDTO convertFieldListToDTO(ModelMapper modelMapper, FieldList fieldList)           {
        return fieldService.convertFieldListToDTO(modelMapper, fieldList);
    }

}
