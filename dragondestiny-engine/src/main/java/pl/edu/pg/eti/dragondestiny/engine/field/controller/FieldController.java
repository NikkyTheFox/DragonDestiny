package pl.edu.pg.eti.dragondestiny.engine.field.controller;

import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;
import pl.edu.pg.eti.dragondestiny.engine.field.service.FieldService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Represents REST controller, allows to handle requests to get fields.
 * Requests go through /api/fields - they represent all fields, not only those assigned to games or boards.
 */
@RestController
@RequestMapping("/api/fields")
public class FieldController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * FieldService used to communicate with service layer that will communicate with database repository.
     */
    private final FieldService fieldService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param fieldService Service for data retrieval and manipulation.
     * @param modelMapper Mapper for conversion from object to DTO.
     */
    @Autowired
    public FieldController(FieldService fieldService, ModelMapper modelMapper) {
        this.fieldService = fieldService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves all fields.
     *
     * @return A structure containing a list of fields.
     */
    @GetMapping()
    public ResponseEntity<FieldListDTO> getFields() {
        Optional<FieldList> fieldList = fieldService.getFields();
        return fieldList.map(list -> ResponseEntity.ok().body(fieldService.convertFieldListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves field by ID.
     *
     * @param fieldId An identifier of field to be retrieved.
     * @return A retrieved field.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable(name = "id") Integer fieldId) {
        Optional<Field> field = fieldService.findField(fieldId);
        return field.map(value -> ResponseEntity.ok().body(modelMapper.map(value, FieldDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves enemy on the field.
     *
     * @param fieldId An identifier of a field to be checked.
     * @return An enemy card from a field.
     */
    @GetMapping("/{id}/enemy")
    public ResponseEntity<EnemyCardDTO> getFieldEnemy(@PathVariable(name = "id") Integer fieldId) {
        Optional<EnemyCard> enemyCard = fieldService.getFieldEnemy(fieldId);
        return enemyCard.map(card -> ResponseEntity.ok().body(modelMapper.map(card, EnemyCardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
