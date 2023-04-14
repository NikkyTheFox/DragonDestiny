package com.example.game.field.controller;


import com.example.game.board.dto.BoardDTO;
import com.example.game.board.entity.Board;
import com.example.game.board.service.BoardService;
import com.example.game.field.dto.FieldDTO;
import com.example.game.field.entity.Field;
import com.example.game.field.entity.FieldTypesEnum;
import com.example.game.field.service.FieldService;
import jakarta.persistence.Enumerated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fields")
public class FieldController {

    private ModelMapper modelMapper;
    private FieldService fieldService;
    @Autowired
    FieldController(FieldService fieldService, ModelMapper modelMapper) {
        this.fieldService = fieldService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<FieldDTO> getAllFields() {
        return fieldService.findAll().stream()
                .map(field -> modelMapper.map(field, FieldDTO.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getFieldById(@PathVariable(name = "id") Integer id) {
        Field field = fieldService.findById(id);
        // convert board entity to DTO
        FieldDTO fieldResponse = modelMapper.map(field, FieldDTO.class);
        return ResponseEntity.ok().body(fieldResponse);
    }

    @PostMapping
    public ResponseEntity<FieldDTO> createField(@RequestBody FieldDTO fieldDTO){
        // convert DTO to entity
        Field fieldRequest = modelMapper.map(fieldDTO, Field.class);
        Field field = fieldService.save(fieldRequest);
        // convert entity to DTO
        FieldDTO fieldResponse = modelMapper.map(field, FieldDTO.class);
        return ResponseEntity.ok().body(fieldResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldDTO> updateField(@PathVariable(name = "id") Integer id, @RequestBody FieldDTO fieldDTO) {
        // convert DTO to entity
        Field fieldRequest = modelMapper.map(fieldDTO, Field.class);
        Field field = fieldService.update(id, fieldRequest);
        // convert entity to DTO
        FieldDTO fieldResponse = modelMapper.map(field, FieldDTO.class);
        return ResponseEntity.ok().body(fieldResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable(name = "id") Integer id) {
        fieldService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
