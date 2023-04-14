package com.example.game.board.controller;

import com.example.game.board.dto.BoardDTO;
import com.example.game.board.entity.Board;
import com.example.game.board.service.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private ModelMapper modelMapper;
    private BoardService boardService;
    @Autowired
    BoardController(BoardService boardService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<BoardDTO> getAllBoards() {
        return boardService.findAll().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable(name = "id") Integer id) {
        Board board = boardService.findById(id);
        // convert board entity to DTO
        BoardDTO boardResponse = modelMapper.map(board, BoardDTO.class);
        return ResponseEntity.ok().body(boardResponse);
    }

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO){
        // convert DTO to entity
        Board boardRequest = modelMapper.map(boardDTO, Board.class);
        Board board = boardService.save(boardRequest);
        // convert entity to DTO
        BoardDTO boardResponse = modelMapper.map(board, BoardDTO.class);
        return ResponseEntity.ok().body(boardResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable(name = "id") Integer id, @RequestBody BoardDTO boardDTO) {
        // convert DTO to entity
        Board boardRequest = modelMapper.map(boardDTO, Board.class);
        Board board = boardService.update(id, boardRequest);
        // convert entity to DTO
        BoardDTO boardResponse = modelMapper.map(board, BoardDTO.class);
        return ResponseEntity.ok().body(boardResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable(name = "id") Integer id) {
        boardService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
