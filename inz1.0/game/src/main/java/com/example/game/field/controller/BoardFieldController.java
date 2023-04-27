package com.example.game.field.controller;

import com.example.game.board.dto.BoardDTO;
import com.example.game.board.entity.Board;
import com.example.game.board.service.BoardService;
import com.example.game.card.card.dto.CardDTO;
import com.example.game.field.dto.FieldDTO;
import com.example.game.field.entity.Field;
import com.example.game.field.service.FieldService;
import com.example.game.game.entity.Game;
import com.example.game.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Allows to get all fields of certain board.
 * Does not allow any other options - just get as board will not be changed during the game.
 */
@RestController
@RequestMapping(value = {"/api/boards/{boardid}/fields", "/api/games/{gameid}/board/{boardid}/fields"})
public class BoardFieldController {

    private ModelMapper modelMapper;
    private FieldService fieldService;
    private BoardService boardService;

    private GameService gameService;

    @Autowired
    public BoardFieldController(GameService gameService, BoardService boardService, FieldService fieldService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.boardService = boardService;
        this.fieldService = fieldService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Field> getFieldsByBoard(@PathVariable("boardid") Integer boardid)
    {
        Board board = boardService.findById(boardid);
        return fieldService.findAllByBoardId(board.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getFieldById(@PathVariable("boardid") Integer boardid, @PathVariable("id") Integer id)
    {
        Board board = boardService.findById(boardid);
        Field field = fieldService.findFieldByBoardIdAndId(boardid, id);
        FieldDTO fieldResponse = modelMapper.map(field, FieldDTO.class);
        return ResponseEntity.ok().body(fieldResponse);
    }

}
