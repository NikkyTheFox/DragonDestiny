package com.example.game.board.service;

import com.example.game.board.repository.BoardRepository;
import com.example.game.board.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository)
    {
        this.boardRepository = boardRepository;
    }

    public Board findById(Integer id) {
        Optional<Board> board = boardRepository.findById(id);
        if (board.isPresent()) { // found
            return board.get();
        } else throw new RuntimeException("No board found");
    }
    public List<Board> findAll() {return boardRepository.findAll();}
    @Transactional
    public Board save(Board board) {
        return boardRepository.save(board);
    }
    @Transactional
    public void deleteById(Integer id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("No board found"));
        boardRepository.deleteById(id);
    }
    @Transactional
    public Board update(Integer id, Board boardRequest) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("No board found"));
        board.setXSize(boardRequest.getXSize());
        board.setYSize(boardRequest.getYSize());
        board.setFieldsInBoard(boardRequest.getFieldsInBoard());
        return boardRepository.save(board);
    }

}
