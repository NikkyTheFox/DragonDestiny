package pl.edu.pg.eti.dragondestiny.engine.game.controller;

import pl.edu.pg.eti.dragondestiny.engine.board.dto.BoardDTO;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.game.dto.GameDTO;
import pl.edu.pg.eti.dragondestiny.engine.game.dto.GameListDTO;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.engine.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Represents REST controller, allows to handle requests to get games (game scenarios).
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
     * Autowired constructor - beans are injected automatically.
     *
     * @param gameService Service for data retrieval and manipulation.
     * @param modelMapper Mapper for conversion from objects to DTOs.
     */
    @Autowired
    public GameController(GameService gameService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all games.
     *
     * @return A structure containing list of games.
     */
    @GetMapping()
    public ResponseEntity<GameListDTO> getGames() {
        Optional<GameList> gameList = gameService.getGames();
        return gameList.map(list -> ResponseEntity.ok().body(gameService.convertGameListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve game by ID.
     *
     * @param gameId An identifier of a game.
     * @return A retrieved game.
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<GameDTO> getGame(@PathVariable(name = "gameId") Integer gameId) {
        Optional<Game> game = gameService.findGame(gameId);
        return game.map(value -> ResponseEntity.ok().body(modelMapper.map(value, GameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve board added to game of ID gameId.
     *
     * @param gameId An identifier of a game.
     * @return A retrieved board.
     */
    @GetMapping("/{gameId}/board")
    public ResponseEntity<BoardDTO> getGameBoard(@PathVariable(name = "gameId") Integer gameId) {
        Optional<Board> board = gameService.getGameBoard(gameId);
        return board.map(value -> ResponseEntity.ok().body(modelMapper.map(value, BoardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
