package com.example.game.field.controller;

import com.example.game.board.dto.BoardDTO;
import com.example.game.board.entity.Board;
import com.example.game.board.service.BoardService;
import com.example.game.field.entity.Field;
import com.example.game.field.service.FieldService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/boards/{id}/fields")
public class BoardFieldController {

    private ModelMapper modelMapper;
    private FieldService fieldService;
    private BoardService boardService;

    @Autowired
    public BoardFieldController(BoardService boardService, FieldService fieldService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.fieldService = fieldService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Field> getFields(@PathVariable("id") Integer id) {
        Board board = boardService.findById(id);
        return fieldService.findAllByBoardId(board.getId());
    }

}
