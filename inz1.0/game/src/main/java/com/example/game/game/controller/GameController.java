package com.example.game.game.controller;


import com.example.game.board.dto.BoardDTO;
import com.example.game.board.entity.Board;
import com.example.game.board.service.BoardService;
import com.example.game.game.dto.GameDTO;
import com.example.game.game.entity.Game;
import com.example.game.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private ModelMapper modelMapper;
    private GameService gameService;
    private BoardService boardService;
    @Autowired
    GameController(GameService gameService, BoardService boardService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<GameDTO> getAllGames() {
        return gameService.findAll().stream()
                .map(board -> modelMapper.map(board, GameDTO.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/{gameid}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable(name = "gameid") Integer gameid) {
        Game game = gameService.findById(gameid);
        // convert board entity to DTO
        GameDTO gameResponse = modelMapper.map(game, GameDTO.class);
        return ResponseEntity.ok().body(gameResponse);
    }
    @GetMapping("/{gameid}/board")
    public ResponseEntity<BoardDTO> getGameBoard(@PathVariable(name = "gameid") Integer gameid) {
        Game game = gameService.findById(gameid);
        Board board = this.boardService.findById(game.getBoardId());
        BoardDTO boardResponse = modelMapper.map(board, BoardDTO.class);
        return ResponseEntity.ok().body(boardResponse);
    }

    @GetMapping( "/{gameid}/board/{id}")
    public ResponseEntity<BoardDTO> getGameBoard(@PathVariable(name = "gameid") Integer gameid, @PathVariable(name = "id") Integer id) {
        Game game = gameService.findById(gameid);
        Board board = this.boardService.findById(game.getBoardId());
        if (id == board.getId())
        {
            BoardDTO boardResponse = modelMapper.map(board, BoardDTO.class);
            return ResponseEntity.ok().body(boardResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO gameDTO){
        // convert DTO to entity
        Game gameRequest = modelMapper.map(gameDTO, Game.class);
        Game game = gameService.save(gameRequest);
        // convert entity to DTO
        GameDTO gameResponse = modelMapper.map(game, GameDTO.class);
        return ResponseEntity.ok().body(gameResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable(name = "id") Integer id, @RequestBody GameDTO gameDTO) {
        // convert DTO to entity
        Game gameRequest = modelMapper.map(gameDTO, Game.class);
        Game game = gameService.update(id, gameRequest);
        // convert entity to DTO
        GameDTO gameResponse = modelMapper.map(game, GameDTO.class);
        return ResponseEntity.ok().body(gameResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable(name = "id") Integer id) {
        gameService.deleteById(id);
        return ResponseEntity.accepted().build();
    }


}
