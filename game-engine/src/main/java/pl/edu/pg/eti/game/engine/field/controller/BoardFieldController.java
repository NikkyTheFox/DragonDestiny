package pl.edu.pg.eti.game.engine.field.controller;

import pl.edu.pg.eti.game.engine.board.entity.Board;
import pl.edu.pg.eti.game.engine.board.service.BoardService;
import pl.edu.pg.eti.game.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.game.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.game.engine.field.entity.Field;
import pl.edu.pg.eti.game.engine.field.service.FieldService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents REST controller, allows to handle requests to get cards in certain board.
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
    private final FieldService fieldService;

    /**
     * BoardService used to communicate with service layer that will communicate with database repository.
     */
    private final BoardService boardService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param boardService
     * @param fieldService
     * @param modelMapper
     */
    @Autowired
    public BoardFieldController(BoardService boardService, FieldService fieldService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.fieldService = fieldService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all fields added to board of boardId ID.
     *
     * @param boardId - board identifier
     * @return list of fields on the board
     */
    @GetMapping
    public ResponseEntity<FieldListDTO> getFields(@PathVariable("boardId") Integer boardId) {
        // find board
        Optional<Board> board = boardService.findBoard(boardId);
        if (board.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // find fields
        List<FieldDTO> fieldDTOList = fieldService.findFields(board.get()).stream()
                .map(field -> modelMapper.map(field, FieldDTO.class))
                .collect(Collectors.toList());
        FieldListDTO fieldListDTO = new FieldListDTO(fieldDTOList);
        return ResponseEntity.ok().body(fieldListDTO);
    }

    /**
     * Retrieve field by its ID that is added to board of boardId ID.
     *
     * @param id - identifier of field
     * @param boardId - board identifier
     * @return ResponseEntity containing FieldDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable("boardId") Integer boardId, @PathVariable("id") Integer id) {
        // find board
        Optional<Board> board = boardService.findBoard(boardId);
        if (board.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // find field
        Optional<Field> field = fieldService.findField(board.get(), id);
        if (field.isEmpty())
            return ResponseEntity.notFound().build();
        FieldDTO fieldResponse = modelMapper.map(field.get(), FieldDTO.class);
        return ResponseEntity.ok().body(fieldResponse);
    }

}
