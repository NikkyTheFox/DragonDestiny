package com.example.game_engine.board.service;

import com.example.game_engine.board.repository.BoardRepository;
import com.example.game_engine.board.entity.Board;
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
     * @param boardRepository
     */
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * Returns board by ID. If no board found, throws exception.
     * @param id
     * @return board found
     */
    public Board findById(Integer id) {
        Optional<Board> board = boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else throw new RuntimeException("No board found");
    }

    /**
     * Returns all boards found.
     * @return list of boards
     */
    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}
