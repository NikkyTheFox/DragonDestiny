package pl.edu.pg.eti.game.engine.board.controller;

import pl.edu.pg.eti.game.engine.board.dto.BoardDTO;
import pl.edu.pg.eti.game.engine.board.dto.BoardListDTO;
import pl.edu.pg.eti.game.engine.board.entity.Board;
import pl.edu.pg.eti.game.engine.board.service.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
     *
     * @param boardService
     * @param modelMapper
     */
    @Autowired
    public BoardController(BoardService boardService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieve all boards.
     *
     * @return list of BoardDTOs
     */
    @GetMapping()
    public ResponseEntity<BoardListDTO> getBoards() {
        List<BoardDTO> boardDTOList = boardService.findBoards().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        BoardListDTO boardListDTO = new BoardListDTO(boardDTOList);
        return ResponseEntity.ok().body(boardListDTO);
    }

    /**
     * Retrieve board by its ID.
     *
     * @param id - identifier of board
     * @return ResponseEntity containing BoardDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable(name = "id") Integer id) {
        Optional<Board> board = boardService.findBoard(id);
        if (board.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BoardDTO boardResponse = modelMapper.map(board.get(), BoardDTO.class);
        return ResponseEntity.ok().body(boardResponse);
    }

}
