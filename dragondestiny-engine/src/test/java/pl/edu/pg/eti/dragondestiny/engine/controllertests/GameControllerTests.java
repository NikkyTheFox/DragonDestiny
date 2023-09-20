package pl.edu.pg.eti.dragondestiny.engine.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;
import pl.edu.pg.eti.dragondestiny.engine.game.controller.GameController;
import pl.edu.pg.eti.dragondestiny.engine.game.dto.GameDTO;
import pl.edu.pg.eti.dragondestiny.engine.game.dto.GameListDTO;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.engine.game.service.GameService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertGameListToDTO;

class GameControllerTests {

    @InjectMocks
    private GameController gameController;
    @Mock
    private GameService gameService;
    @Mock
    private ModelMapper modelMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final List<Game> gameList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        this.objectMapper = new ObjectMapper();
        Board board1 = new Board();
        board1.setId(1);
        board1.setXsize(5);
        board1.setYsize(5);
        Card card1 = new Card();
        card1.setId(1);
        card1.setName("Super Cool Card");
        Card card2 = new Card();
        card2.setId(2);
        card2.setName("Magic Stick");
        Game game1 = new Game();
        game1.setId(1);
        game1.setBoard(board1);
        game1.setCardDeck(new ArrayList<>(Arrays.asList(card1, card2)));
        Game game2 = new Game();
        game2.setId(2);
        gameList.add(game1);
        gameList.add(game2);

        when(gameService.convertGameListToDTO(eq(modelMapper), any(GameList.class))).thenAnswer(invocation ->
                convertGameListToDTO(invocation.getArgument(1)));
        when(gameService.getGames()).thenReturn(Optional.of(new GameList(gameList)));
        when(gameService.getGame(anyInt())).thenAnswer(invocation -> {
            int gameId = invocation.getArgument(0);
            return gameList.stream().filter(field -> field.getId().equals(gameId)).findFirst();
        });
        when(modelMapper.map(any(Game.class), eq(GameDTO.class))).thenAnswer(invocation -> {
            Game game = invocation.getArgument(0);
            GameListDTO gameListDTO = convertGameListToDTO(new GameList(new ArrayList<>(Collections.singletonList(game))));
            return gameListDTO.getGameList().get(0);
        });
    }

    @Test
    void testGetGamesOK() throws Exception {
        // Arrange
        GameListDTO gameListDTO = convertGameListToDTO(new GameList(gameList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        GameListDTO gameListFound = objectMapper.readValue(responseContent, GameListDTO.class);
        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(gameListDTO.getGameList().size(), gameListFound.getGameList().size());
        for (int d = 0; d < gameListFound.getGameList().size(); d++) {
            assertEquals(gameListDTO.getGameList().get(d), gameListFound.getGameList().get(d));
        }
    }

    @Test
    void testGetBoardsNotFound() throws Exception {
        // Arrange
        when(gameService.getGames()).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetBoardOK() throws Exception {
        // Arrange
        int gameId = 1;
        GameListDTO gameListDTO = convertGameListToDTO(new GameList(gameList));
        GameDTO gameToFind = gameListDTO.getGameList().stream().filter(fieldDTO -> fieldDTO.getId().equals(gameId)).findFirst().get();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{id}", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        GameDTO gameFound = objectMapper.readValue(responseContent, GameDTO.class);
        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(gameToFind, gameFound);
    }

    @Test
    void testGetBoardNotFound() throws Exception {
        // Arrange
        int gameId = 1311;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{id}", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
