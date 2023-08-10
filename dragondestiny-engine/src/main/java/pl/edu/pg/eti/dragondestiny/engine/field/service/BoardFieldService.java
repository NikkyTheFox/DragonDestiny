package pl.edu.pg.eti.dragondestiny.engine.field.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.board.service.BoardService;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;

import java.util.List;
import java.util.Optional;


/**
 * Board Field Service to communication with fields on board data saved in database.
 */
@Service
public class BoardFieldService {

    /**
     * BoardService used to communicate with service layer that will communicate with database repository.
     */
    private final BoardService boardService;

    /**
     * FieldService used to communicate with service layer that will communicate with database repository.
     */
    private final FieldService fieldService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param boardService Service for data retrieval and manipulation.
     * @param fieldService Service for data retrieval and manipulation.
     */
    @Autowired
    public BoardFieldService(BoardService boardService, FieldService fieldService){
        this.boardService = boardService;
        this.fieldService = fieldService;
    }

    /**
     * Retrieves al fields from specified board.
     *
     * @param boardId A board identifier.
     * @return A structure containing list of fields.
     */
    public Optional<FieldList> getFields(Integer boardId){
        Optional<Board> board = boardService.findBoard(boardId);
        if(board.isEmpty()){
            return Optional.empty();
        }
        List<Field> fieldList = fieldService.findFields(board.get());
        if(fieldList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new FieldList(fieldList));
    }

    /**
     * Retrieves specified field from the board.
     *
     * @param boardId An identifier of a board.
     * @param fieldId An identifier of a field.
     * @return A retrieved field.
     */
    public Optional<Field> getField(Integer boardId, Integer fieldId){
        Optional<Board> board = boardService.findBoard(boardId);
        if(board.isEmpty()){
            return Optional.empty();
        }
        return fieldService.findField(board.get(), fieldId);
    }

    /**
     * Retrieves optional enemy that may stand on a specified field.
     *
     * @param boardId An identifier of a board.
     * @param fieldId An identifier of a field.
     * @return An enemy card.
     */
    public Optional<EnemyCard> getFieldEnemy(Integer boardId, Integer fieldId){
        Optional<Board> board = boardService.findBoard(boardId);
        if(board.isEmpty()){
            return Optional.empty();
        }
        Optional<Field> field = fieldService.findField(board.get(), fieldId);
        if(field.isEmpty()){
            return Optional.empty();
        }
        return fieldService.getFieldEnemy(field.get().getId());
    }

    /**
     * Converts FieldList into FieldListDTO
     *
     * @param modelMapper Mapper allowing conversion.
     * @param fieldList A structure containing list of fields.
     * @return A DTO.
     */
    public FieldListDTO convertFieldListToDTO(ModelMapper modelMapper, FieldList fieldList){
        return fieldService.convertFieldListToDTO(modelMapper, fieldList);
    }
}
