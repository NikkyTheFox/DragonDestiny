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
import pl.edu.pg.eti.dragondestiny.engine.card.card.controller.CardController;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.Card;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.CardList;
import pl.edu.pg.eti.dragondestiny.engine.card.card.service.CardService;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.service.EnemyCardService;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.service.ItemCardService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static pl.edu.pg.eti.dragondestiny.engine.helpers.ControllerTestsHelper.*;

class CardControllerTests {

    @InjectMocks
    private CardController cardController;
    @Mock
    private EnemyCardService enemyCardService;
    @Mock
    private ItemCardService itemCardService;
    @Mock
    private CardService cardService;
    @Mock
    private ModelMapper modelMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final List<Card> cardList = new ArrayList<>();
    private final List<EnemyCard> enemyCardList = new ArrayList<>();
    private final List<ItemCard> itemCardList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
        this.objectMapper = new ObjectMapper();
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

        when(cardService.convertCardListToDTO(eq(modelMapper), any(CardList.class))).thenAnswer(invocation ->
                convertCardListToDTO(invocation.getArgument(1)));
        when(enemyCardService.convertEnemyCardListToDTO(eq(modelMapper), any(EnemyCardList.class))).thenAnswer(invocation ->
                convertEnemyCardListToDTO(invocation.getArgument(1)));
        when(itemCardService.convertItemCardListToDTO(eq(modelMapper), any(ItemCardList.class))).thenAnswer(invocation ->
                convertItemCardListToDTO(invocation.getArgument(1)));
        when(cardService.getCards()).thenReturn(Optional.of(new CardList(cardList)));
        when(enemyCardService.getEnemyCards()).thenReturn(Optional.of(new EnemyCardList(enemyCardList)));
        when(itemCardService.getItemCards()).thenReturn(Optional.of(new ItemCardList(itemCardList)));
        when(cardService.getCard(anyInt())).thenAnswer(invocation -> {
            int cardId = invocation.getArgument(0);
            return cardList.stream().filter(field -> field.getId().equals(cardId)).findFirst();
        });
        when(modelMapper.map(any(Card.class), eq(CardDTO.class))).thenAnswer(invocation -> {
            Card field = invocation.getArgument(0);
            CardListDTO fieldListDTO = convertCardListToDTO(new CardList(new ArrayList<>(Collections.singletonList(field))));
            return fieldListDTO.getCardList().get(0);
        });
    }

    @Test
    void testGetCardsOK() throws Exception {
        // Arrange
        CardListDTO cardListDTO = convertCardListToDTO(new CardList(cardList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/cards")
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
    void testGetCardsNotFound() throws Exception {
        // Arrange
        when(cardService.getCards()).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/cards")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetCardOK() throws Exception {
        // Arrange
        int cardId = 1;
        CardListDTO cardListDTO = convertCardListToDTO(new CardList(cardList));
        CardDTO cardToFind = cardListDTO.getCardList().stream().filter(fieldDTO -> fieldDTO.getId().equals(cardId)).findFirst().get();
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/cards/{id}", cardId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        CardDTO cardFound = objectMapper.readValue(responseContent, CardDTO.class);
        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(cardToFind, cardFound);
    }

    @Test
    void testGetCardNotFound() throws Exception {
        // Arrange
        int cardId = 1311;
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/cards/{id}", cardId)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetEnemyCardsOK() throws Exception {
        // Arrange
        EnemyCardListDTO cardListDTO = convertEnemyCardListToDTO(new EnemyCardList(enemyCardList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/cards/enemy")
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
        when(enemyCardService.getEnemyCards()).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/cards/enemy")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetItemCardsOK() throws Exception {
        // Arrange
        ItemCardListDTO cardListDTO = convertItemCardListToDTO(new ItemCardList(itemCardList));
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/cards/item")
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
        when(itemCardService.getItemCards()).thenReturn(Optional.empty());
        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8085/api/cards/item")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
