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
import pl.edu.pg.eti.dragondestiny.engine.board.controller.BoardController;
import pl.edu.pg.eti.dragondestiny.engine.board.dto.BoardDTO;
import pl.edu.pg.eti.dragondestiny.engine.board.dto.BoardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.Board;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.BoardList;
import pl.edu.pg.eti.dragondestiny.engine.board.service.BoardService;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertBoardListToDTO;

class BoardControllerTests {

    @InjectMocks
    private BoardController boardController;
    @Mock
    private BoardService boardService;
    @Mock
    private ModelMapper modelMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final List<Board> boardList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
        this.objectMapper = new ObjectMapper();
        Field field1 = new Field();
        field1.setId(1);
        Field field2 = new Field();
        EnemyCard enemyCard = new EnemyCard();
        enemyCard.setId(1);
        enemyCard.setInitialHealth(10);
        enemyCard.setInitialStrength(10);
        enemyCard.setName("Gubernator");
        field2.setId(2);
        field2.setEnemy(enemyCard);
        Board board1 = new Board();
        board1.setId(1);
        board1.setXsize(5);
        board1.setYsize(5);
        board1.getFields().add(field1);
        board1.getFields().add(field2);
        Board board2 = new Board();
        board2.setId(2);
        board2.setXsize(2);
        board2.setYsize(2);
        boardList.add(board1);
        boardList.add(board2);

        when(boardService.convertBoardListToDTO(eq(modelMapper), any(BoardList.class))).thenAnswer(invocation ->
                convertBoardListToDTO(invocation.getArgument(1)));
        when(boardService.getBoards()).thenReturn(Optional.of(new BoardList(boardList)));
        when(boardService.getBoard(anyInt())).thenAnswer(invocation -> {
            int boardId = invocation.getArgument(0);
            return boardList.stream().filter(field -> field.getId().equals(boardId)).findFirst();
        });
        when(modelMapper.map(any(Board.class), eq(BoardDTO.class))).thenAnswer(invocation -> {
            Board board = invocation.getArgument(0);
            BoardListDTO boardListDTO = convertBoardListToDTO(new BoardList(new ArrayList<>(Collections.singletonList(board))));
            return boardListDTO.getBoardList().get(0);
        });
    }

    @Test
    void testGetBoardsOK() throws Exception {
        // Arrange
        BoardListDTO boardListDTO = convertBoardListToDTO(new BoardList(boardList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        BoardListDTO boardListFound = objectMapper.readValue(responseContent, BoardListDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(boardListDTO.getBoardList().size(), boardListFound.getBoardList().size());
        for (int d = 0; d < boardListFound.getBoardList().size(); d++) {
            assertEquals(boardListDTO.getBoardList().get(d), boardListFound.getBoardList().get(d));
        }
    }

    @Test
    void testGetBoardsNotFound() throws Exception {
        // Arrange
        when(boardService.getBoards()).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetBoardOK() throws Exception {
        // Arrange
        int boardId = 1;
        BoardListDTO boardListDTO = convertBoardListToDTO(new BoardList(boardList));
        BoardDTO boardToFind = boardListDTO.getBoardList().stream().filter(fieldDTO -> fieldDTO.getId().equals(boardId)).findFirst().get();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards/{id}", boardId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        BoardDTO boardFound = objectMapper.readValue(responseContent, BoardDTO.class);
        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(boardToFind, boardFound);
    }

    @Test
    void testGetBoardNotFound() throws Exception {
        // Arrange
        int boardId = 1311;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards/{id}", boardId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
