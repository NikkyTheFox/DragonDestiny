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
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.engine.field.controller.FieldController;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.Field;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;
import pl.edu.pg.eti.dragondestiny.engine.field.service.FieldService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertEnemyCardListToDTO;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertFieldListToDTO;

class FieldControllerTests {

    @InjectMocks
    private FieldController fieldController;

    @Mock
    private FieldService fieldService;

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

    private final List<Field> fieldList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fieldController).build();
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
        fieldList.add(field1);
        fieldList.add(field2);

        when(fieldService.convertFieldListToDTO(eq(modelMapper), any(FieldList.class))).thenAnswer(invocation ->
                convertFieldListToDTO(invocation.getArgument(1)));
        when(fieldService.getFields()).thenReturn(Optional.of(new FieldList(fieldList)));
        when(fieldService.getFieldEnemy(anyInt())).thenAnswer(invocation -> {
            int fieldId = invocation.getArgument(0);
            Optional<Field> f = fieldList.stream().filter(field -> field.getId().equals(fieldId)).findFirst();
            if (f.isEmpty() || f.get().getEnemy() == null)
                return Optional.empty();
            return Optional.of(f.get().getEnemy());
        });
        when(fieldService.getField(anyInt())).thenAnswer(invocation -> {
            int fieldId = invocation.getArgument(0);
            return fieldList.stream().filter(field -> field.getId().equals(fieldId)).findFirst();
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
        FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(fieldList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/fields")
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
        when(fieldService.getFields()).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/fields")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetFieldOK() throws Exception {
        // Arrange
        int fieldId = 1;
        FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(fieldList));
        FieldDTO fieldToFind = fieldListDTO.getFieldList().stream().filter(fieldDTO -> fieldDTO.getId().equals(fieldId)).findFirst().get();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/fields/{id}", fieldId)
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
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/fields/{id}", fieldId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetFieldEnemyOK() throws Exception {
        // Arrange
        int fieldId = 2;
        FieldListDTO fieldListDTO = convertFieldListToDTO(new FieldList(fieldList));
        EnemyCardDTO enemyCardToFind = fieldListDTO.getFieldList().stream().filter(fieldDTO -> fieldDTO.getId().equals(fieldId)).findFirst().get().getEnemy();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/fields/{id}/enemy", fieldId)
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
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/fields/{id}/enemy", fieldId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
