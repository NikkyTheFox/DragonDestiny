package pl.edu.pg.eti.dragondestiny.engine.board.controller;

import pl.edu.pg.eti.dragondestiny.engine.board.dto.BoardDTO;
import pl.edu.pg.eti.dragondestiny.engine.board.dto.BoardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.BoardList;
import pl.edu.pg.eti.dragondestiny.engine.board.service.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
     * @param boardService Service allowing manipulation of database objects.
     * @param modelMapper Mapper to transform objects to DTOs.
     */
    @Autowired
    public BoardController(BoardService boardService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves all boards.
     *
     * @return A structure containing list of boards.
     */
    @GetMapping()
    public ResponseEntity<BoardListDTO> getBoards() {
        Optional<BoardList> boardList = boardService.getBoards();
        return boardList.map(list -> ResponseEntity.ok().body(boardService.convertBoardListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves board by its ID.
     *
     * @param id An identifier of board.
     * @return A retrieved board.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable(name = "id") Integer id) {
        Optional<Board> board = boardService.findBoard(id);
        return board.map(value -> ResponseEntity.ok().body(modelMapper.map(value, BoardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
