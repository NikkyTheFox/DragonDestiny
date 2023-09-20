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
import pl.edu.pg.eti.dragondestiny.engine.character.controller.GameCharacterController;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterListDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.Character;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.CharacterList;
import pl.edu.pg.eti.dragondestiny.engine.character.service.GameCharacterService;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.convertCharacterListToDTO;

class GameCharacterControllerTests {

    @InjectMocks
    private GameCharacterController gameCharacterController;
    @Mock
    private GameCharacterService gameCharacterService;
    @Mock
    private ModelMapper modelMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final List<Character> characterList = new ArrayList<>();

    private int gameId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameCharacterController).build();
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
        gameId = 1;
        Game game = new Game();
        game.setId(gameId);
        game.getCharacters().add(character1);
        game.getCharacters().add(character2);

        when(gameCharacterService.convertCharacterListToDTO(eq(modelMapper), any(CharacterList.class))).thenAnswer(invocation ->
                convertCharacterListToDTO(invocation.getArgument(1)));
        when(gameCharacterService.getGameCharacters(gameId)).thenReturn(Optional.of(new CharacterList(characterList)));
        when(gameCharacterService.getGameCharacter(eq(gameId), anyInt())).thenAnswer(invocation -> {
            int cardId = invocation.getArgument(1);
            return characterList.stream().filter(field -> field.getId().equals(cardId)).findFirst();
        });
        when(modelMapper.map(any(Character.class), eq(CharacterDTO.class))).thenAnswer(invocation -> {
            Character field = invocation.getArgument(0);
            CharacterListDTO fieldListDTO = convertCharacterListToDTO(new CharacterList(new ArrayList<>(Collections.singletonList(field))));
            return fieldListDTO.getCharacterList().get(0);
        });
    }

    @Test
    void testGetGameCharactersOK() throws Exception {
        // Arrange
        CharacterListDTO characterListDTO = convertCharacterListToDTO(new CharacterList(characterList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/characters", gameId)
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
    void testGetGameCharactersNotFound() throws Exception {
        // Arrange
        int gameId = 9123;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/characters", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetGameCharacterOK() throws Exception {
        // Arrange
        int characterId = 1;
        CharacterListDTO characterListDTO = convertCharacterListToDTO(new CharacterList(characterList));
        CharacterDTO characterToFind = characterListDTO.getCharacterList().stream().filter(fieldDTO -> fieldDTO.getId().equals(characterId)).findFirst().get();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/characters/{id}", gameId, characterId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        CharacterDTO characterFound = objectMapper.readValue(responseContent, CharacterDTO.class);
        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(characterToFind, characterFound);
    }

    @Test
    void testGetGameCharacterNotFound() throws Exception {
        // Arrange
        int characterId = 1311;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/characters/{id}", gameId, characterId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
