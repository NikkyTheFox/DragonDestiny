package pl.edu.pg.eti.dragondestiny.engine.board.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.engine.board.dto.BoardDTO;
import pl.edu.pg.eti.dragondestiny.engine.board.dto.BoardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.BoardList;
import pl.edu.pg.eti.dragondestiny.engine.board.repository.BoardRepository;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Board Service to communication with boards' data saved in database.
 */
@Service
public class BoardService {

    /**
     * JPA repository communicating with database.
     */
    private final BoardRepository boardRepository;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param boardRepository Repository with methods for data manipulation in the database.
     */
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * Returns board by ID.
     *
     * @param id The ID of a board o be retrieved.
     * @return A retrieved board.
     */
    public Optional<Board> getBoard(Integer id) {
        return boardRepository.findById(id);
    }

    /**
     * Returns board from game.
     *
     * @param game A specified game for which a board is to be retrieved.
     * @return A board from game.
     */
    public Optional<Board> getBoardByGame(Game game) {

        return boardRepository.findByGames(game);
    }

    /**
     * Retrieves all boards from the database.
     *
     * @return A structure containing list of boards.
     */
    public Optional<BoardList> getBoards() {
        List<Board> boardList = boardRepository.findAll();
        if (boardList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new BoardList(boardList));
    }

    /**
     * Converts BoardList to BoardListDTO.
     *
     * @param modelMapper Mapper allowing to transform from object to DTO.
     * @param boardList   A structure containing list of boards.
     * @return A DTO.
     */
    public BoardListDTO convertBoardListToDTO(ModelMapper modelMapper, BoardList boardList) {
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardList.getBoardList().forEach(board -> {
            BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
            boardDTOList.add(boardDTO);
        });
        return new BoardListDTO(boardDTOList);
    }

}
