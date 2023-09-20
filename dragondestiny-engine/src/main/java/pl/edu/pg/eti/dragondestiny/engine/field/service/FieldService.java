package pl.edu.pg.eti.dragondestiny.engine.field.service;

import org.modelmapper.ModelMapper;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;
import pl.edu.pg.eti.dragondestiny.engine.field.repository.FieldRepository;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Field Service to communication with fields' data saved in database.
 */
@Service
public class FieldService {

    /**
     * JPA repository communicating with database.
     */
    private final FieldRepository fieldRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param fieldRepository Repository for data retrieval.
     */
    @Autowired
    public FieldService(FieldRepository fieldRepository) {

        this.fieldRepository = fieldRepository;
    }

    /**
     * Returns field by ID.
     *
     * @param id An identifier of a field to be retrieved.
     * @return A retrieved field.
     */
    public Optional<Field> getField(Integer id) {

        return fieldRepository.findById(id);
    }

    /**
     * Returns all fields on particular board.
     *
     * @param board A board to get all fields from.
     * @return A list of fields on board.
     */
    public List<Field> getFieldsByBoard(Board board) {

        return fieldRepository.findAllByBoard(board);
    }

    /**
     * Returns field of ID fieldId found on board of boardId.
     *
     * @param board A board to get field from.
     * @param fieldId An identifier of a field.
     * @return A retrieved field.
     */
    public Optional<Field> getField(Board board, Integer fieldId) {
        return fieldRepository.findByBoardAndId(board, fieldId);
    }

    /**
     * Retrieves all fields from the database.
     *
     * @return A structure containing list of fields.
     */
    public Optional<FieldList> getFields() {
        List<Field> fieldList = fieldRepository.findAll();
        if (fieldList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new FieldList(fieldList));
    }

    /**
     * Retrieves optional enemy that may stand on a specified field.
     *
     * @param fieldId An identifier of a field.
     * @return An enemy card.
     */
    public Optional<EnemyCard> getFieldEnemy(Integer fieldId){
        Optional<Field> field = getField(fieldId);
        if(field.isEmpty() || field.get().getEnemy() == null){
            return Optional.empty();
        }
        return Optional.of(field.get().getEnemy());
    }

    /**
     * Converts FieldList into FieldListDTO
     *
     * @param modelMapper Mapper allowing conversion.
     * @param fieldList A structure containing list of fields.
     * @return A DTO.
     */
    public FieldListDTO convertFieldListToDTO(ModelMapper modelMapper, FieldList fieldList){
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        fieldList.getFieldList().forEach(field -> {
            FieldDTO fieldDTO = modelMapper.map(field, FieldDTO.class);
            fieldDTOList.add(fieldDTO);
        });
        return new FieldListDTO(fieldDTOList);
    }
}
