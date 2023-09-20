package pl.edu.pg.eti.dragondestiny.engine.field.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;
import pl.edu.pg.eti.dragondestiny.engine.field.service.GameFieldService;
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
@RequestMapping(value = {"/api/games/{gameId}/board/fields"})
public class GameFieldController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * GameFieldService used to communicate with service layer that will communicate with database repository.
     */
    private final GameFieldService gameFieldService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param modelMapper Mapper allowing conversion from objects to DTOs.
     * @param gameFieldService Service for data retrieval and manipulation.
     */
    @Autowired
    public GameFieldController(ModelMapper modelMapper, GameFieldService gameFieldService) {
        this.modelMapper = modelMapper;
        this.gameFieldService = gameFieldService;
    }

    /**
     * Retrieves all fields added to board of the game specified by ID.
     *
     * @param gameId An identifier of a game.
     * @return A structure containing list of fields.
     */
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FieldListDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content)})
    public ResponseEntity<FieldListDTO> getFields(@PathVariable("gameId") Integer gameId) {
        Optional<FieldList> fieldList = gameFieldService.getGameFields(gameId);
        return fieldList.map(list -> ResponseEntity.ok().body(gameFieldService.convertFieldListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves field by its ID from the board that belongs to the game specified by ID.
     *
     * @param gameId An identifier of a game.
     * @param fieldId An identifier of a field.
     * @return A retrieved field.
//     */
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FieldDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Field on board in game not found", content = @Content)})
    public ResponseEntity<FieldDTO> getField(@PathVariable("gameId") Integer gameId, @PathVariable("id") Integer fieldId) {
        Optional<Field> field = gameFieldService.getGameField(gameId, fieldId);
        return field.map(value -> ResponseEntity.ok().body(modelMapper.map(value, FieldDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves enemy from the field specified by ID and by game's ID.
     *
     * @param gameId An identifier of a game.
     * @param fieldId An identifier of a field.
     * @return A retrieved enemy card.
     */
    @GetMapping("/{id}/enemy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EnemyCardDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Enemy on field on board in game not found", content = @Content)})
    public ResponseEntity<EnemyCardDTO> getFieldEnemy(@PathVariable("gameId") Integer gameId, @PathVariable(name = "id") Integer fieldId) {
        Optional<EnemyCard> enemyCard = gameFieldService.getGameFieldEnemy(gameId, fieldId);
        return enemyCard.map(card -> ResponseEntity.ok().body(modelMapper.map(card, EnemyCardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
