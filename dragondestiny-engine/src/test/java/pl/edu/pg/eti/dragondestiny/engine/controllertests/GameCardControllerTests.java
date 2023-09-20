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
import pl.edu.pg.eti.dragondestiny.engine.card.card.controller.GameCardController;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.CardList;
import pl.edu.pg.eti.dragondestiny.engine.card.card.service.GameCardService;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCardList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.*;

class GameCardControllerTests {

    @InjectMocks
    private GameCardController gameCardController;
    @Mock
    private GameCardService gameCardService;
    @Mock
    private ModelMapper modelMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final List<Card> cardList = new ArrayList<>();
    private final List<EnemyCard> enemyCardList = new ArrayList<>();
    private final List<ItemCard> itemCardList = new ArrayList<>();

    private int gameId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameCardController).build();
        this.objectMapper = new ObjectMapper();
        gameId = 1;
        EnemyCard enemyCard = new EnemyCard();
        enemyCard.setId(1);
        enemyCard.setInitialHealth(10);
        enemyCard.setInitialStrength(10);
        enemyCard.setName("Gubernator");
        enemyCardList.add(enemyCard);
        ItemCard itemCard = new ItemCard();
        itemCard.setId(1);
        itemCard.setHealth(10);
        itemCard.setStrength(10);
        itemCard.setName("Stick");
        itemCardList.add(itemCard);
        cardList.add(enemyCard);
        cardList.add(itemCard);

        when(gameCardService.convertCardListToDTO(eq(modelMapper), any(CardList.class))).thenAnswer(invocation ->
                convertCardListToDTO(invocation.getArgument(1)));
        when(gameCardService.convertEnemyCardListToDTO(eq(modelMapper), any(EnemyCardList.class))).thenAnswer(invocation ->
                convertEnemyCardListToDTO(invocation.getArgument(1)));
        when(gameCardService.convertItemCardListToDTO(eq(modelMapper), any(ItemCardList.class))).thenAnswer(invocation ->
                convertItemCardListToDTO(invocation.getArgument(1)));
        when(gameCardService.getGameCards(gameId)).thenReturn(Optional.of(new CardList(cardList)));
        when(gameCardService.getGameEnemyCards(gameId)).thenReturn(Optional.of(new EnemyCardList(enemyCardList)));
        when(gameCardService.getGameItemCards(gameId)).thenReturn(Optional.of(new ItemCardList(itemCardList)));
        when(gameCardService.getGameCard(eq(gameId), anyInt())).thenAnswer(invocation -> {
            int cardId = invocation.getArgument(1);
            return cardList.stream().filter(field -> field.getId().equals(cardId)).findFirst();
        });
        when(modelMapper.map(any(Card.class), eq(CardDTO.class))).thenAnswer(invocation -> {
            Card field = invocation.getArgument(0);
            CardListDTO fieldListDTO = convertCardListToDTO(new CardList(new ArrayList<>(Collections.singletonList(field))));
            return fieldListDTO.getCardList().get(0);
        });
    }

    @Test
    void testGetGameCardsOK() throws Exception {
        // Arrange
        CardListDTO cardListDTO = convertCardListToDTO(new CardList(cardList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/cards", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        CardListDTO cardListFound = objectMapper.readValue(responseContent, CardListDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(cardListDTO.getCardList().size(), cardListFound.getCardList().size());
        for (int d = 0; d < cardListDTO.getCardList().size(); d++) {
            assertEquals(cardListDTO.getCardList().get(d), cardListFound.getCardList().get(d));
        }
    }

    @Test
    void testGetGameCardsNotFound() throws Exception {
        // Arrange
        int gameId = 1231;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/cards", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetGameCardOK() throws Exception {
        // Arrange
        int cardId = 1;
        CardListDTO cardListDTO = convertCardListToDTO(new CardList(cardList));
        CardDTO cardToFind = cardListDTO.getCardList().stream().filter(fieldDTO -> fieldDTO.getId().equals(cardId)).findFirst().get();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/cards/{id}", gameId, cardId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        CardDTO cardFound = objectMapper.readValue(responseContent, CardDTO.class);
        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(cardToFind, cardFound);
    }

    @Test
    void testGetGameCardNotFound() throws Exception {
        // Arrange
        int cardId = 1311;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/cards/{id}", gameId, cardId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetEnemyCardsOK() throws Exception {
        // Arrange
        EnemyCardListDTO cardListDTO = convertEnemyCardListToDTO(new EnemyCardList(enemyCardList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/cards/enemy", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        EnemyCardListDTO cardListFound = objectMapper.readValue(responseContent, EnemyCardListDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(cardListDTO.getEnemyCardList().size(), cardListFound.getEnemyCardList().size());
        for (int d = 0; d < cardListDTO.getEnemyCardList().size(); d++) {
            assertEquals(cardListDTO.getEnemyCardList().get(d), cardListFound.getEnemyCardList().get(d));
        }
    }

    @Test
    void testGetEnemyCardsNotFound() throws Exception {
        // Arrange
        int gameId = 5191;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/cards/enemy", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetItemCardsOK() throws Exception {
        // Arrange
        ItemCardListDTO cardListDTO = convertItemCardListToDTO(new ItemCardList(itemCardList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/cards/item", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        ItemCardListDTO cardListFound = objectMapper.readValue(responseContent, ItemCardListDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(cardListDTO.getItemCardList().size(), cardListFound.getItemCardList().size());
        for (int d = 0; d < cardListDTO.getItemCardList().size(); d++) {
            assertEquals(cardListDTO.getItemCardList().get(d), cardListFound.getItemCardList().get(d));
        }
    }

    @Test
    void testGetItemCardsNotFound() throws Exception {
        // Arrange
        int gameId = 91491;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/games/{gameId}/cards/item", gameId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
