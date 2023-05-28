package com.example.game_engine.board.controller;

import com.example.game_engine.board.dto.BoardDTO;
import com.example.game_engine.board.entity.Board;
import com.example.game_engine.board.service.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents REST controller, allows to handle requests to get boards.
 * Requests go through /api/boards - they represent all boards, not only those assigned to games.
 */
@RestController
@RequestMapping(value = {"/api/boards"})
public class BoardController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * BoardService used to communicate with service layer that will communicate with database repository.
     */
    private final BoardService boardService;

    /**
     * Autowired constructor - beans are injected automatically.
     * @param boardService
     * @param modelMapper
     */
    @Autowired
    BoardController(BoardService boardService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all boards.
     * @return list of BoardDTOs
     */
    @GetMapping()
    public List<BoardDTO> getAllBoards() {
        return boardService.findAll().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve board by its ID.
     * @param id - identifier of board
     * @return ResponseEntity containing BoardDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable(name = "id") Integer id) {
        Board board = boardService.findById(id);
        // convert board entity to DTO
        BoardDTO boardResponse = modelMapper.map(board, BoardDTO.class);
        return ResponseEntity.ok().body(boardResponse);
    }
}
