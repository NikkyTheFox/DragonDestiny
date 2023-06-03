package pl.edu.pg.eti.game.engine.field.controller;

import pl.edu.pg.eti.game.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.game.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.game.engine.field.entity.Field;
import pl.edu.pg.eti.game.engine.field.service.FieldService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @param fieldService
     * @param modelMapper
     */
    @Autowired
    public FieldController(FieldService fieldService, ModelMapper modelMapper) {
        this.fieldService = fieldService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all fields.
     *
     * @return list of FieldDTOs
     */
    @GetMapping()
    public ResponseEntity<FieldListDTO> getFields() {
        List<FieldDTO> fieldDTOList = fieldService.findFields().stream()
                .map(field -> modelMapper.map(field, FieldDTO.class))
                .collect(Collectors.toList());
        FieldListDTO fieldListDTO = new FieldListDTO(fieldDTOList);
        return ResponseEntity.ok().body(fieldListDTO);
    }

    /**
     * Retrieve field by ID.
     *
     * @param id - identifier of field
     * @return ResponseEntity containing FieldDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable(name = "id") Integer id) {
        Optional<Field> field = fieldService.findField(id);
        if (field.isEmpty())
            return ResponseEntity.notFound().build();
        FieldDTO fieldResponse = modelMapper.map(field.get(), FieldDTO.class);
        return ResponseEntity.ok().body(fieldResponse);
    }

}
