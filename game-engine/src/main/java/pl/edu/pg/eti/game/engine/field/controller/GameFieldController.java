package pl.edu.pg.eti.game.engine.field.controller;

import pl.edu.pg.eti.game.engine.board.entity.Board;
import pl.edu.pg.eti.game.engine.board.service.BoardService;
import pl.edu.pg.eti.game.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.game.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.game.engine.field.entity.Field;
import pl.edu.pg.eti.game.engine.field.service.FieldService;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import pl.edu.pg.eti.game.engine.game.service.GameService;
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

@RestController
@RequestMapping(value = {"/api/games/{gameId}/board/fields"})
public class GameFieldController {

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
     *
     * @param gameService
     * @param boardService
     * @param fieldService
     * @param modelMapper
     */
    @Autowired
    public GameFieldController(GameService gameService, BoardService boardService, FieldService fieldService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.boardService = boardService;
        this.fieldService = fieldService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all fields added to board of boardId ID.
     *
     * @param gameId - game identifier
     * @return list of fields on the board
     */
    @GetMapping
    public ResponseEntity<FieldListDTO> getFields(@PathVariable("gameId") Integer gameId) {
        // find game
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // find board
        Optional<Board> board = boardService.findBoard(game.get());
        if (board.isEmpty())
            return ResponseEntity.notFound().build();
        // find fields
        List<FieldDTO> fieldDTOList = fieldService.findFields(board.get()).stream()
                .map(field -> modelMapper.map(field, FieldDTO.class))
                .collect(Collectors.toList());
        FieldListDTO fieldListDTO = new FieldListDTO(fieldDTOList);
        return ResponseEntity.ok().body(fieldListDTO);
    }

    /**
     * Retrieve field by its ID that is added to board of belonging to game of gameId.
     *
     * @param id - identifier of field
     * @param gameId - game identifier
     * @return ResponseEntity containing FieldDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable("gameId") Integer gameId, @PathVariable("id") Integer id) {
        // find game
        Optional<Game> game = gameService.findGame(gameId);
        if (game.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // find board
        Optional<Board> board = boardService.findBoard(game.get());
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
