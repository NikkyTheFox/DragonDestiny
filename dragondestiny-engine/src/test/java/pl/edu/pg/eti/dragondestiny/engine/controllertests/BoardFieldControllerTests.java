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
import pl.edu.pg.eti.dragondestiny.engine.field.controller.BoardFieldController;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;
import pl.edu.pg.eti.dragondestiny.engine.field.service.BoardFieldService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertEnemyCardListToDTO;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertFieldListToDTO;

class BoardFieldControllerTests {

    @InjectMocks
    private BoardFieldController boardFieldController;
    @Mock
    private BoardFieldService boardFieldService;
    @Mock
    private ModelMapper modelMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Board board;
    private int boardId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(boardFieldController).build();
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
        boardId = 1;
        board = new Board();
        board.setId(boardId);
        board.setXsize(5);
        board.setYsize(5);
        board.getFields().add(field1);
        board.getFields().add(field2);

        when(boardFieldService.convertFieldListToDTO(eq(modelMapper), any(FieldList.class))).thenAnswer(invocation ->
                convertFieldListToDTO(invocation.getArgument(1)));
        when(boardFieldService.getFields(boardId)).thenReturn(Optional.of(new FieldList(board.getFields())));
        when(boardFieldService.getFieldEnemy(eq(boardId), anyInt())).thenAnswer(invocation -> {
            int fieldId = invocation.getArgument(1);
            Optional<Field> f = board.getFields().stream().filter(field -> field.getId().equals(fieldId)).findFirst();
            if (f.isEmpty() || f.get().getEnemy() == null)
                return Optional.empty();
            return Optional.of(f.get().getEnemy());
        });
        when(boardFieldService.getField(eq(boardId), anyInt())).thenAnswer(invocation -> {
            int fieldId = invocation.getArgument(1);
            return board.getFields().stream().filter(field -> field.getId().equals(fieldId)).findFirst();
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
        FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(board.getFields()));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards/{boardId}/fields", boardId)
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
        int boardId = 121;
        when(boardFieldService.getFields(boardId)).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards/{boardId}/fields", boardId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetFieldOK() throws Exception {
        // Arrange
        int fieldId = 1;
        FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(board.getFields()));
        FieldDTO fieldToFind = fieldListDTO.getFieldList().stream().filter(fieldDTO -> fieldDTO.getId().equals(fieldId)).findFirst().get();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards/{boardId}/fields/{id}", boardId, fieldId)
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
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards/{boardId}/fields/{id}", boardId, fieldId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetFieldEnemyOK() throws Exception {
        // Arrange
        int fieldId = 2;
        FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(board.getFields()));
        EnemyCardDTO enemyCardToFind = fieldListDTO.getFieldList().stream().filter(fieldDTO -> fieldDTO.getId().equals(fieldId)).findFirst().get().getEnemy();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards/{boardId}/fields/{id}/enemy", boardId, fieldId)
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
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/boards/{boardId}/fields/{id}/enemy", boardId, fieldId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
