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
import pl.edu.pg.eti.dragondestiny.engine.character.controller.CharacterController;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterListDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.Character;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.CharacterList;
import pl.edu.pg.eti.dragondestiny.engine.character.service.CharacterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertCharacterListToDTO;

class CharacterControllerTests {

    @InjectMocks
    private CharacterController characterController;
    @Mock
    private CharacterService characterService;
    @Mock
    private ModelMapper modelMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final List<Character> characterList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(characterController).build();
        this.objectMapper = new ObjectMapper();
        Character character1 = new Character();
        character1.setId(1);
        character1.setInitialHealth(10);
        character1.setInitialStrength(10);
        character1.setName("Rubeus");
        Character character2 = new Character();
        character2.setId(1);
        character2.setInitialHealth(10);
        character2.setInitialStrength(10);
        character2.setName("Albus");
        characterList.add(character1);
        characterList.add(character2);

        when(characterService.convertCharacterListToDTO(eq(modelMapper), any(CharacterList.class))).thenAnswer(invocation ->
                convertCharacterListToDTO(invocation.getArgument(1)));
        when(characterService.getCharacters()).thenReturn(Optional.of(new CharacterList(characterList)));
        when(characterService.getCharacterById(anyInt())).thenAnswer(invocation -> {
            int cardId = invocation.getArgument(0);
            return characterList.stream().filter(field -> field.getId().equals(cardId)).findFirst();
        });
        when(modelMapper.map(any(Character.class), eq(CharacterDTO.class))).thenAnswer(invocation -> {
            Character field = invocation.getArgument(0);
            CharacterListDTO fieldListDTO = convertCharacterListToDTO(new CharacterList(new ArrayList<>(Collections.singletonList(field))));
            return fieldListDTO.getCharacterList().get(0);
        });
    }

    @Test
    void testGetCharactersOK() throws Exception {
        // Arrange
        CharacterListDTO characterListDTO = convertCharacterListToDTO(new CharacterList(characterList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/characters")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        CharacterListDTO characterListFound = objectMapper.readValue(responseContent, CharacterListDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(characterListDTO.getCharacterList().size(), characterListFound.getCharacterList().size());
        for (int d = 0; d < characterListDTO.getCharacterList().size(); d++) {
            assertEquals(characterListDTO.getCharacterList().get(d), characterListFound.getCharacterList().get(d));
        }
    }

    @Test
    void testGetCharactersNotFound() throws Exception {
        // Arrange
        when(characterService.getCharacters()).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/characters")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetCharacterOK() throws Exception {
        // Arrange
        int characterId = 1;
        CharacterListDTO characterListDTO = convertCharacterListToDTO(new CharacterList(characterList));
        CharacterDTO characterToFind = characterListDTO.getCharacterList().stream().filter(fieldDTO -> fieldDTO.getId().equals(characterId)).findFirst().get();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/characters/{id}", characterId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        CharacterDTO characterFound = objectMapper.readValue(responseContent, CharacterDTO.class);
        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(characterToFind, characterFound);
    }

    @Test
    void testGetCharacterNotFound() throws Exception {
        // Arrange
        int characterId = 1311;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/characters/{id}", characterId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
