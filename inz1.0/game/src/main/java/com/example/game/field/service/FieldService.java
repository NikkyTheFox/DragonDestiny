package com.example.game.field.service;

import com.example.game.card.card.entity.Card;
import com.example.game.field.entity.Field;
import com.example.game.field.repository.FieldRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FieldService {

    private FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository)
    {
        this.fieldRepository = fieldRepository;
    }

    public Field findById(Integer id) {
        Optional<Field> field = fieldRepository.findById(id);
        if (field.isPresent()) { // found
            return field.get();
        } else throw new RuntimeException("No field found");
    }
    public List<Field> findAll() {return fieldRepository.findAll();}

    public List<Field> findAllByBoardId(Integer id) {return fieldRepository.findFieldsByBoardId(id);}

    public Field findFieldByBoardIdAndId(Integer boardId, Integer id) {return fieldRepository.findFieldByBoardIdAndId(boardId, id);}

    @Transactional
    public Field save(Field field) {return fieldRepository.save(field);}
    @Transactional
    public void deleteById(Integer id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new RuntimeException("No field found"));
        fieldRepository.deleteById(id);
    }
    @Transactional
    public Field update(Integer id, Field fieldRequest) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new RuntimeException("No field found"));
        field.setFieldType(fieldRequest.getFieldType());
        field.setXPosition(fieldRequest.getXPosition());
        field.setYPosition(fieldRequest.getYPosition());
        field.setBoardId(fieldRequest.getBoardId());
        return fieldRepository.save(field);
    }

}
