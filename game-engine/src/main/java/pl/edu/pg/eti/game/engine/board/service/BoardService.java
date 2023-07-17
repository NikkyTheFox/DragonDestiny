package pl.edu.pg.eti.game.engine.board.service;

import pl.edu.pg.eti.game.engine.board.repository.BoardRepository;
import pl.edu.pg.eti.game.engine.board.entity.Board;
import pl.edu.pg.eti.game.engine.game.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param boardRepository
     */
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * Returns board by ID.
     *
     * @param id
     * @return board found
     */
    public Optional<Board> findBoard(Integer id) {
        return boardRepository.findById(id);
    }

    /**
     * Returns all boards found.
     *
     * @return list of boards
     */
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    /**
     * Returns board from game.
     *
     * @param game - game to get board from
     * @return board from game if exists
     */
    public Optional<Board> findBoard(Game game) {
        return boardRepository.findByGames(game);
    }

}
