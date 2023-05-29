package com.example.game_engine.game.controller;

import com.example.game_engine.board.dto.BoardDTO;
import com.example.game_engine.board.entity.Board;
import com.example.game_engine.board.service.BoardService;
import com.example.game_engine.game.dto.GameDTO;
import com.example.game_engine.game.entity.Game;
import com.example.game_engine.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents REST controller, allows to handle requests to get games (versions of games).
 * Requests go through /api/games.
 */
@RestController
@RequestMapping("/api/games")
public class GameController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * GameService used to communicate with service layer that will communicate with database repository.
     */
    private final GameService gameService;

    /**
     * BoardService used to communicate with service layer that will communicate with database repository.
     */
    private final BoardService boardService;

    /**
     * Autowired constructor - beans are injected automatically.
     * @param gameService
     * @param boardService
     * @param modelMapper
     */
    @Autowired
    GameController(GameService gameService, BoardService boardService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all games.
     * @return list of GameDTOs
     */
    @GetMapping()
    public List<GameDTO> getAllGames() {
        return gameService.findAll().stream()
                .map(game -> modelMapper.map(game, GameDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve game by ID.
     * @param gameId - identifier of card
     * @return ResponseEntity containing EnemyCardDTO or ItemCardDTO
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable(name = "gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
        GameDTO gameResponse = modelMapper.map(game, GameDTO.class);
        return ResponseEntity.ok().body(gameResponse);
    }

    /**
     * Retrieve board added to game of ID gameId.
     * @param gameId - identifier of game
     * @return ResponseEntity containing BoardDTO
     */
    @GetMapping("/{gameId}/boards")
    public ResponseEntity<BoardDTO> getGameBoard(@PathVariable(name = "gameId") Integer gameId) {
        Game game = gameService.findById(gameId);
        Board board = this.boardService.findById(game.getBoard().getId());
        BoardDTO boardResponse = modelMapper.map(board, BoardDTO.class);
        return ResponseEntity.ok().body(boardResponse);
    }

    /**
     * Retrieve cartain board with ID boardId that is added to game of ID gameId.
     * @param gameId - identifier of game
     * @param boardId - identifier of board in game
     * @return ResponseEntity containing BoardDTO
     */
    @GetMapping( "/{gameId}/boards/{boardId}")
    public ResponseEntity<BoardDTO> getGameBoard(@PathVariable(name = "gameId") Integer gameId, @PathVariable(name = "boardId") Integer boardId) {
        Game game = gameService.findById(gameId);
        Board board = this.boardService.findById(game.getBoard().getId());
        if (Objects.equals(boardId, board.getId()))
        {
            BoardDTO boardResponse = modelMapper.map(board, BoardDTO.class);
            return ResponseEntity.ok().body(boardResponse);
        }
        return ResponseEntity.notFound().build();
    }

}
