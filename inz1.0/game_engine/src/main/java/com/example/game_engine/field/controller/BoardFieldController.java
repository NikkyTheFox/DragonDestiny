package com.example.game_engine.field.controller;

import com.example.game_engine.board.entity.Board;
import com.example.game_engine.board.service.BoardService;
import com.example.game_engine.field.dto.FieldDTO;
import com.example.game_engine.field.entity.Field;
import com.example.game_engine.field.service.FieldService;
import com.example.game_engine.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Represents REST controller, allows to handle requests to get cards in certain board.
 * The board can belong to certain game.
 */
@RestController
@RequestMapping(value = {"/api/boards/{boardId}/fields", "/api/games/{gameId}/boards/{boardId}/fields"})
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
     * GameService used to communicate with service layer that will communicate with database repository.
     */
    private final GameService gameService;

    /**
     * Autowired constructor - beans are injected automatically.
     * @param gameService
     * @param boardService
     * @param fieldService
     * @param modelMapper
     */
    @Autowired
    public BoardFieldController(GameService gameService, BoardService boardService, FieldService fieldService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.boardService = boardService;
        this.fieldService = fieldService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all fields added to board of boardId ID.
     * @param boardId - game identifier
     * @return list of fields on the board
     */
    @GetMapping
    public List<Field> getFieldsByBoard(@PathVariable("boardId") Integer boardId)
    {
        Board board = boardService.findById(boardId);
        return fieldService.findAllByBoardId(board.getId());
    }

    /**
     * Retrieve field by its ID that is added to board of boardId ID.
     * @param id - identifier of field
     * @param boardId - game identifier
     * @return ResponseEntity containing FieldDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getFieldById(@PathVariable("boardId") Integer boardId, @PathVariable("id") Integer id)
    {
        Board board = boardService.findById(boardId);
        Field field = fieldService.findFieldByBoardIdAndId(board.getId(), id);
        FieldDTO fieldResponse = modelMapper.map(field, FieldDTO.class);
        return ResponseEntity.ok().body(fieldResponse);
    }

}
