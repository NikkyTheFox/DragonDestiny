package pl.edu.pg.eti.dragondestiny.engine.field.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterListDTO;
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
     * @param boardId An identifier of board.
     * @return A structure containing list of fields.
     */
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FieldListDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Board not found", content = @Content)})
    public ResponseEntity<FieldListDTO> getFields(@PathVariable("boardId") Integer boardId) {
        Optional<FieldList> fieldList = boardFieldService.getFields(boardId);
        return fieldList.map(list -> ResponseEntity.ok().body(boardFieldService.convertFieldListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves field specified by ID from given board.
     *
     * @param boardId An identifier of board.
     * @param fieldId An identifier of field.
     * @return A retrieved field.
     */
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FieldDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Field on board not found", content = @Content)})
    public ResponseEntity<FieldDTO> getField(@PathVariable("boardId") Integer boardId, @PathVariable("id") Integer fieldId) {
        Optional<Field> field = boardFieldService.getField(boardId, fieldId);
        return field.map(value -> ResponseEntity.ok().body(modelMapper.map(value, FieldDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves enemy from the field given by ID and board's ID.
     *
     * @param boardId An identifier of a board.
     * @param fieldId An identifier of a field.
     * @return An enemy card from the field.
     */
    @GetMapping("/{id}/enemy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EnemyCardDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Enemy on field on board not found", content = @Content)})
    public ResponseEntity<EnemyCardDTO> getFieldEnemy(@PathVariable("boardId") Integer boardId, @PathVariable(name = "id") Integer fieldId) {
        Optional<EnemyCard> enemyCard = boardFieldService.getFieldEnemy(boardId, fieldId);
        return enemyCard.map(card -> ResponseEntity.ok().body(modelMapper.map(card, EnemyCardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
