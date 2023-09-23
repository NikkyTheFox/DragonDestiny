package pl.edu.pg.eti.dragondestiny.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import pl.edu.pg.eti.dragondestiny.user.game.dto.GameDTO;
import pl.edu.pg.eti.dragondestiny.user.game.dto.GameListDTO;
import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.user.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.user.game.repository.GameRepository;
import pl.edu.pg.eti.dragondestiny.user.game.service.GameService;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GameServiceTests {

    private final List<Game> gameList = new ArrayList<>();
    /**
     * GameService is object being tested. All mocks will be automatically injected as dependencies.
     */
    @InjectMocks
    private GameService gameService;
    /**
     * GameRepository mock for GameService.
     */
    @Mock
    private GameRepository gameRepositoryMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        User user1 = new User("hero", "pass", "Harry", null);
        User user2 = new User("amazon", "pass", "Ron", null);
        User user3 = new User("disney", "pass", "Ginny", null);

        Game game1 = new Game("game1", new ArrayList<>(Arrays.asList(user1, user2)));
        Game game2 = new Game("game2", new ArrayList<>(List.of(user2)));
        Game game3 = new Game("game3", new ArrayList<>(Arrays.asList(user1, user2, user3)));
        gameList.add(game1);
        gameList.add(game2);
        gameList.add(game3);
        user1.setPlayedGames(new ArrayList<>(Arrays.asList(game1, game3)));
        user2.setPlayedGames(new ArrayList<>(Arrays.asList(game1, game2, game3)));
        user3.setPlayedGames(new ArrayList<>(List.of(game3)));

        when(gameRepositoryMock.findAll()).thenReturn(gameList);

        when(gameRepositoryMock.findById("game1")).thenReturn(Optional.of(game1));
        when(gameRepositoryMock.findById("game2")).thenReturn(Optional.of(game2));
        when(gameRepositoryMock.findById("game3")).thenReturn(Optional.of(game3));

        when(gameRepositoryMock.findAllByUserList(user1)).thenReturn(user1.getPlayedGames());
        when(gameRepositoryMock.findAllByUserList(user2)).thenReturn(user2.getPlayedGames());
        when(gameRepositoryMock.findAllByUserList(user3)).thenReturn(user3.getPlayedGames());
    }

    @Test
    public void testGetGameExisting() {
        // Arrange
        String gameId = "game1";
        Optional<Game> gameToFind = gameList.stream().filter(game -> game.getId().equals(gameId)).findFirst();
        String gameId2 = "notExistingGame";

        // Act
        Optional<Game> gameFound = gameService.getGame(gameId);
        Optional<Game> gameFound2 = gameService.getGame(gameId2);

        // Assert
        assertTrue(gameFound.isPresent());
        assertEquals(gameFound.get(), gameToFind.get());
        assertTrue(gameFound2.isEmpty());
    }

    @Test
    public void testGetGameNonExisting() {
        // Arrange
        String gameId = "notExistingGame";

        // Act
        Optional<Game> gameFound = gameService.getGame(gameId);

        // Assert
        assertTrue(gameFound.isEmpty());
    }

    @Test
    public void testGetGamesByUserExisting() {
        // Arrange
        String login = "amazon";
        Optional<User> userFound = gameList.stream()
                .flatMap(game -> game.getUserList().stream())
                .filter(user -> user.getLogin().equals(login))
                .findAny();

        List<Game> gameListToFind = gameList.stream()
                .filter(game -> game.getUserList().stream().anyMatch(
                        user -> user.getLogin().equals(login))).toList();
        // Act
        List<Game> gameListFound = gameService.getGames(userFound.get());
        // Assert
        assertEquals(gameListToFind.size(), gameListFound.size());
        for (int d = 0; d < gameListToFind.size(); d++) {
            assertEquals(gameListFound.get(d), gameListToFind.get(d));
        }
    }

    @Test
    public void testGetGamesByUserNonExisting() {
        // Arrange
        String login = "nonExistingUser";
        User userToFind = null;
        // Act
        List<Game> gameListFound = gameService.getGames(userToFind);
        // Assert
        assertTrue(gameListFound.isEmpty());
    }

    @Test
    public void testGetGames() {
        // Act
        List<Game> gameListFound = gameService.getGames();
        // Assert
        assertEquals(gameListFound.size(), gameList.size());
        for (int d = 0; d < gameListFound.size(); d++) {
            assertEquals(gameListFound.get(d), gameList.get(d));
        }
    }

    @Test
    public void testConvertGameListToDTO() {
        // Arrange
        ModelMapper modelMapper = new ModelMapper();
        GameList gameListToConvert = new GameList(gameList);

        // Act
        GameListDTO gameListDTO = gameService.convertGameListToDTO(modelMapper, gameListToConvert);

        // Assert
        for (int d = 0; d < gameListDTO.getGameList().size(); d++) {
            assertSame(gameListDTO.getGameList().get(d).getClass(), GameDTO.class);
        }
    }

}
