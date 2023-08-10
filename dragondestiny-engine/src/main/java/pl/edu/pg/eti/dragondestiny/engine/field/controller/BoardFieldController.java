package pl.edu.pg.eti.dragondestiny.engine.field.controller;

import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;
import pl.edu.pg.eti.dragondestiny.engine.field.service.BoardFieldService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Represents REST controller, allows to handle requests to get fields in certain board.
 * The board can belong to certain game.
 */
@RestController
@RequestMapping(value = {"/api/boards/{boardId}/fields"})
public class BoardFieldController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * FieldService used to communicate with service layer that will communicate with database repository.
     */
    private final BoardFieldService boardFieldService;


    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param modelMapper Mapper for conversion from objects to DTOs.
     * @param boardFieldService Service for data retrieval and manipulation.
     */
    @Autowired
    public BoardFieldController(ModelMapper modelMapper, BoardFieldService boardFieldService) {
        this.modelMapper = modelMapper;
        this.boardFieldService = boardFieldService;
    }

    /**
     * Retrieves all fields from the specified board.
     *
     * @param boardId A board identifier.
     * @return A structure containing list of fields.
     */
    @GetMapping
    public ResponseEntity<FieldListDTO> getFields(@PathVariable("boardId") Integer boardId) {
        Optional<FieldList> fieldList = boardFieldService.getFields(boardId);
        return fieldList.map(list -> ResponseEntity.ok().body(boardFieldService.convertFieldListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves field specified by ID from given board.
     *
     * @param boardId A board identifier.
     * @param fieldId An identifier of field.
     * @return A retrieved field.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable("boardId") Integer boardId, @PathVariable("id") Integer fieldId) {
        Optional<Field> field = boardFieldService.getField(boardId, fieldId);
        return field.map(value -> ResponseEntity.ok().body(modelMapper.map(value, FieldDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves enemy from the field given by ID and board's ID.
     *
     * @param boardId An identifier of a board on which a field exists.
     * @param fieldId An identifier of a field to be checked.
     * @return An enemy card from the field.
     */
    @GetMapping("/{id}/enemy")
    public ResponseEntity<EnemyCardDTO> getFieldEnemy(@PathVariable("boardId") Integer boardId, @PathVariable(name = "id") Integer fieldId) {
        Optional<EnemyCard> enemyCard = boardFieldService.getFieldEnemy(boardId, fieldId);
        return enemyCard.map(card -> ResponseEntity.ok().body(modelMapper.map(card, EnemyCardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
