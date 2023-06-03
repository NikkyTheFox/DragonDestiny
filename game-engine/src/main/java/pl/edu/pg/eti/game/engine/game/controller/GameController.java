package pl.edu.pg.eti.game.engine.game.controller;

import pl.edu.pg.eti.game.engine.board.dto.BoardDTO;
import pl.edu.pg.eti.game.engine.board.entity.Board;
import pl.edu.pg.eti.game.engine.board.service.BoardService;
import pl.edu.pg.eti.game.engine.game.dto.GameDTO;
import pl.edu.pg.eti.game.engine.game.dto.GameListDTO;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import pl.edu.pg.eti.game.engine.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public GameController(GameService gameService, BoardService boardService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all games.
     * @return list of GameDTOs
     */
    @GetMapping()
    public ResponseEntity<GameListDTO> getGames() {
        List<GameDTO> gameDTOS = gameService.findGames().stream()
                .map(game -> modelMapper.map(game, GameDTO.class))
                .collect(Collectors.toList());
        GameListDTO gameListDTO = new GameListDTO(gameDTOS);
        return ResponseEntity.ok().body(gameListDTO);
    }

    /**
     * Retrieve game by ID.
     * @param gameId - identifier of card
     * @return ResponseEntity containing EnemyCardDTO or ItemCardDTO
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<GameDTO> getGame(@PathVariable(name = "gameId") Integer gameId) {
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        GameDTO gameResponse = modelMapper.map(game.get(), GameDTO.class);
        return ResponseEntity.ok().body(gameResponse);
    }

    /**
     * Retrieve board added to game of ID gameId.
     * @param gameId - identifier of game
     * @return ResponseEntity containing BoardDTO
     */
    @GetMapping("/{gameId}/board")
    public ResponseEntity<BoardDTO> getGameBoard(@PathVariable(name = "gameId") Integer gameId) {
        // find game
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find board
        Optional<Board> board = this.boardService.findBoard(game.get().getBoard().getId());
        if (board.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BoardDTO boardResponse = modelMapper.map(board.get(), BoardDTO.class);
        return ResponseEntity.ok().body(boardResponse);
    }

}
