package pl.edu.pg.eti.dragondestiny.engine.game.service;

import org.modelmapper.ModelMapper;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.board.service.BoardService;
import pl.edu.pg.eti.dragondestiny.engine.game.dto.GameDTO;
import pl.edu.pg.eti.dragondestiny.engine.game.dto.GameListDTO;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.engine.game.repository.GameRepository;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Game Service to communication with games' data saved in database.
 */
@Service
public class GameService {

    /**
     * JPA repository communicating with database.
     */
    private final GameRepository gameRepository;

    /**
     * BoardService used to communicate with service layer that will communicate with database repository.
     */
    private final BoardService boardService;

    /**
     * Autowired constructor - beans are injected automatically.
     *
     * @param gameRepository Repository for data retrieval.
     * @param boardService Service for data retrieval and manipulation.
     */
    @Autowired
    public GameService(GameRepository gameRepository, BoardService boardService) {
        this.gameRepository = gameRepository;
        this.boardService = boardService;

    }

    /**
     * Returns game by ID.
     *
     * @param gameId An identifier of a game to be retrieved.
     * @return A retrieved game.
     */
    public Optional<Game> findGame(Integer gameId) {

        return gameRepository.findById(gameId);
    }

    /**
     * Returns all games found.
     *
     * @return A list of games.
     */
    public List<Game> findGames() {

        return gameRepository.findAll();
    }

    /**
     * Retrieves all games.
     *
     * @return A structure containing list of games.
     */
    public Optional<GameList> getGames(){
        List<Game> gameList = findGames();
        if(gameList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new GameList(gameList));
    }

    /**
     * Retrieves games' board.
     *
     * @param gameId An identifier of a game.
     * @return A board.
     */
    public Optional<Board> getGameBoard(Integer gameId){
        Optional<Game> game = findGame(gameId);
        if(game.isEmpty()){
            return Optional.empty();
        }
        return boardService.findBoard(game.get());

    }

    /**
     * Converts GameList into GameListDTO.
     *
     * @param modelMapper Mapper allowing conversion.
     * @param gameList A structure containing list of games.
     * @return A DTO.
     */
    public GameListDTO convertGameListToDTO(ModelMapper modelMapper, GameList gameList){
        List<GameDTO> gameDTOList = new ArrayList<>();
        gameList.getGameList().forEach(game -> {
            GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
            gameDTOList.add(gameDTO);
        });
        return new GameListDTO(gameDTOList);
    }
}
