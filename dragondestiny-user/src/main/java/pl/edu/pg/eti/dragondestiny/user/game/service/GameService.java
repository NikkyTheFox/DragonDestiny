package pl.edu.pg.eti.dragondestiny.user.game.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.user.game.dto.GameDTO;
import pl.edu.pg.eti.dragondestiny.user.game.dto.GameListDTO;
import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.user.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.user.game.repository.GameRepository;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;

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

    @Autowired
    public GameService(GameRepository gameRepository) {

        this.gameRepository = gameRepository;
    }

    /**
     * Returns game by id
     *
     * @param gameId An identifier of a game to be retrieved.
     * @return A retrieved game.
     */
    public Optional<Game> getGame(String gameId) {

        return gameRepository.findById(gameId);
    }

    /**
     * Returns all games found by user.
     *
     * @param user A user whose game list is to be retrieved.
     * @return A list of retrieved games.
     */
    public List<Game> getGames(User user) {

        return gameRepository.findAllByUserList(user);
    }

    /**
     * Retrieves all games.
     *
     * @return A structure containing list of games.
     */
    public List<Game> getGames() {

        return gameRepository.findAll();
    }

    /**
     * Adds new game to the database.
     *
     * @param game A game to be saved in the database.
     */
    public void save(Game game) {

        gameRepository.save(game);
    }

    /**
     * Delete game from repository.
     *
     * @param game A game to be deleted from database.
     */
    public void delete(Game game) {

        gameRepository.delete(game);
    }

    /**
     * Converts GameList into GameListDTO.
     *
     * @param modelMapper Mapper allowing conversion.
     * @param gameList    A structure containing list of games.
     * @return A DTO.
     */
    public GameListDTO convertGameListToDTO(ModelMapper modelMapper, GameList gameList) {
        List<GameDTO> gameDTOList = new ArrayList<>();
        gameList.getGameList().forEach(game -> {
            GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
            gameDTOList.add(gameDTO);
        });
        return new GameListDTO(gameDTOList);
    }

}
