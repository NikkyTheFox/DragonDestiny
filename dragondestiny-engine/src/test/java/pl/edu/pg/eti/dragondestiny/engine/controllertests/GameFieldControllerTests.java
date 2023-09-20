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
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.engine.field.controller.GameFieldController;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;
import pl.edu.pg.eti.dragondestiny.engine.field.service.GameFieldService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertEnemyCardListToDTO;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertFieldListToDTO;

class GameFieldControllerTests {

    @InjectMocks
    private GameFieldController gameFieldController;

    @Mock
    private GameFieldService gameFieldService;

    @Mock
    private ModelMapper modelMapper;

    /**
     * Mvc Mock.
     */
    private MockMvc mockMvc;

    /**
     * Object Mapper.
     */
    private ObjectMapper objectMapper;

    private Board board1;

    private int gameId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameFieldController).build();
        this.objectMapper = new ObjectMapper();
        board1 = new Board();
        board1.setId(1);
        board1.setXsize(5);
        board1.setYsize(5);
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
        board1.getFields().add(field1);
        board1.getFields().add(field2);

        when(gameFieldService.convertFieldListToDTO(eq(modelMapper), any(FieldList.class))).thenAnswer(invocation ->
                convertFieldListToDTO(invocation.getArgument(1)));
        when(gameFieldService.getGameFields(gameId)).thenReturn(Optional.of(new FieldList(new ArrayList<>(Arrays.asList(field1, field2)))));
        when(gameFieldService.getGameFieldEnemy(eq(gameId), anyInt())).thenAnswer(invocation -> {
            int fieldId = invocation.getArgument(1);
            Optional<Field> f = board1.getFields().stream().filter(field -> field.getId().equals(fieldId)).findFirst();
            if (f.isEmpty() || f.get().getEnemy() == null)
                return Optional.empty();
            return Optional.of(f.get().getEnemy());
        });
        when(gameFieldService.getGameField(eq(gameId), anyInt())).thenAnswer(invocation -> {
            int fieldId = invocation.getArgument(1);
            return board1.getFields().stream().filter(field -> field.getId().equals(fieldId)).findFirst();
        });
        when(modelMapper.map(any(Field.class), eq(FieldDTO.class))).thenAnswer(invocation -> {
            Field field = invocation.getArgument(0);
            FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(new ArrayList<>(Collections.singletonList(field))));
            return fieldListDTO.getFieldList().get(0);
        });
        when(modelMapper.map(any(EnemyCard.class), eq(EnemyCardDTO.class))).thenAnswer(invocation -> {
            EnemyCard field = invocation.getArgument(0);
            EnemyCardListDTO fieldListDTO = convertEnemyCardListToDTO(new EnemyCardList(new ArrayList<>(Collections.singletonList(field))));
            return fieldListDTO.getEnemyCardList().get(0);
        });
    }

    @Test
    void testGetFieldsOK() throws Exception {
        // Arrange
        FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(board1.getFields()));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/board/fields", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        FieldListDTO fieldListFound = objectMapper.readValue(responseContent, FieldListDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(fieldListDTO.getFieldList().size(), fieldListFound.getFieldList().size());
        for (int d = 0; d < fieldListFound.getFieldList().size(); d++) {
            assertEquals(fieldListDTO.getFieldList().get(d), fieldListFound.getFieldList().get(d));
        }
    }

    @Test
    void testGetFieldsNotFound() throws Exception {
        // Arrange
        int gameId = 21312;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/board/fields", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetFieldOK() throws Exception {
        // Arrange
        int fieldId = 1;
        FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(board1.getFields()));
        FieldDTO fieldToFind = fieldListDTO.getFieldList().stream().filter(fieldDTO -> fieldDTO.getId().equals(fieldId)).findFirst().get();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/board/fields/{id}", gameId, fieldId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        FieldDTO fieldFound = objectMapper.readValue(responseContent, FieldDTO.class);
        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(fieldToFind, fieldFound);
    }

    @Test
    void testGetFieldNotFound() throws Exception {
        // Arrange
        int fieldId = 1311;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/board/fields/{id}", gameId, fieldId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetFieldEnemyOK() throws Exception {
        // Arrange
        int fieldId = 2;
        FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(board1.getFields()));
        EnemyCardDTO enemyCardToFind = fieldListDTO.getFieldList().stream().filter(fieldDTO -> fieldDTO.getId().equals(fieldId)).findFirst().get().getEnemy();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/board/fields/{id}/enemy", gameId, fieldId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        EnemyCardDTO enemyFound = objectMapper.readValue(responseContent, EnemyCardDTO.class);
        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(enemyCardToFind, enemyFound);
    }

    @Test
    void testGetFieldEnemyNotFound() throws Exception {
        // Arrange
        int fieldId = 1;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/board/fields/{id}/enemy", gameId, fieldId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
