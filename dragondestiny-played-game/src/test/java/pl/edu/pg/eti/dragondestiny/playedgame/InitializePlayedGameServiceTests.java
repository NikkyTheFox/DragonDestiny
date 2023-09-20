package pl.edu.pg.eti.dragondestiny.playedgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardType;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.Character;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.CharacterList;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldList;
import pl.edu.pg.eti.dragondestiny.playedgame.initialization.DTO.GameEngineGameDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.initialization.repository.InitializingPlayedGameRepository;
import pl.edu.pg.eti.dragondestiny.playedgame.initialization.service.InitializingPlayedGameService;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGame;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.repository.PlayedGameRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

public class InitializePlayedGameServiceTests {

    /**
     * InitializingPlayedGameService is object being tested. All mocks will be automatically injected as dependencies.
     */
    @InjectMocks
    private InitializingPlayedGameService initializeService;

    /**
     * PlayedGameRepository mock for InitializingPlayedGameService.
     */
    @Mock
    private PlayedGameRepository playedGameRepository;

    /**
     * InitializingPlayedGameRepository mock for InitializingPlayedGameService.
     */
    @Mock
    private InitializingPlayedGameRepository initializingPlayedGameRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        when(initializingPlayedGameRepository.getGameById(anyInt())).thenAnswer(invocation -> {
            int gameId = invocation.getArgument(0);
            return createMockGameEngineGameDTO(gameId);
        });
        when(initializingPlayedGameRepository.getGameEnemyCards(anyInt())).thenReturn(createMockEnemyCardList());
        when(initializingPlayedGameRepository.getGameItemCards(anyInt())).thenReturn(createMockItemCardList());
        when(initializingPlayedGameRepository.getGameCharacters(anyInt())).thenReturn(createMockCharacterList());
        when(initializingPlayedGameRepository.getGamePlayedBoard(anyInt())).thenReturn(createMockPlayedBoard());
        when(initializingPlayedGameRepository.getGameFieldList(anyInt())).thenReturn(createMockFieldList());

        when(playedGameRepository.save(any(PlayedGame.class))).thenAnswer(invocation -> {
            PlayedGame playedGame = invocation.getArgument(0);
            return playedGame;
        });
    }

    @Test
    void testInitializeGameExists() {
        // Arrange
        Integer gameEngineGameId = 123;

        // Act
        Optional<PlayedGame> result = initializeService.initialize(gameEngineGameId);

        // Assert
        assertTrue(result.isPresent());

        EnemyCardList enemyCardFound = new EnemyCardList (result.get().getCardDeck().stream()
                .filter(card -> card.getCardType().equals(CardType.ENEMY_CARD)).map(card -> (EnemyCard) card).collect(Collectors.toList()));
        assertEquals(createMockEnemyCardList().getEnemyCardList().size(), enemyCardFound.getEnemyCardList().size());
        for (int d = 0; d < enemyCardFound.getEnemyCardList().size(); d++) {
            assertEquals(createMockEnemyCardList().getEnemyCardList().get(d).getId(), enemyCardFound.getEnemyCardList().get(d).getId());
        }

        ItemCardList itemCardListFound = new ItemCardList (result.get().getCardDeck().stream()
                .filter(card -> card.getCardType().equals(CardType.ITEM_CARD)).map(card -> (ItemCard) card).collect(Collectors.toList()));
        assertEquals(createMockItemCardList().getItemCardList().size(), itemCardListFound.getItemCardList().size());
        for (int d = 0; d < itemCardListFound.getItemCardList().size(); d++) {
            assertEquals(createMockItemCardList().getItemCardList().get(d).getId(), itemCardListFound.getItemCardList().get(d).getId());
        }

        CharacterList characterListFound = new CharacterList(result.get().getCharactersInGame());
        assertEquals(createMockCharacterList().getCharacterList().size(), characterListFound.getCharacterList().size());
        for (int d = 0; d < characterListFound.getCharacterList().size(); d++) {
            assertEquals(createMockCharacterList().getCharacterList().get(d).getId(), characterListFound.getCharacterList().get(d).getId());
        }
        assertEquals(createMockPlayedBoard().getId(), result.get().getBoard().getId());

        FieldList fieldListFound = new FieldList(result.get().getBoard().getFieldsOnBoard());
        assertEquals(createMockFieldList().getFieldList().size(), fieldListFound.getFieldList().size());
        for (int d = 0; d < fieldListFound.getFieldList().size(); d++) {
            assertEquals(createMockFieldList().getFieldList().get(d).getId(), fieldListFound.getFieldList().get(d).getId());
        }
    }

    @Test
    void testInitializeGameNotExists() {
        // Arrange
        Integer gameEngineGameId = 2;

        // Act
        Optional<PlayedGame> result = initializeService.initialize(gameEngineGameId);

        // Assert
        assertTrue(result.isEmpty());
    }

    private GameEngineGameDTO createMockGameEngineGameDTO(int gameId) {
        if (gameId == 123)
        {
            GameEngineGameDTO gameEngineGameDTO = new GameEngineGameDTO();
            gameEngineGameDTO.setId(123);

            return gameEngineGameDTO;
        }
        return null;
    }

    private EnemyCardList createMockEnemyCardList() {
        EnemyCardList list = new EnemyCardList();
        EnemyCard card1 = new EnemyCard();
        card1.setId(1);
        card1.setCardType(CardType.ENEMY_CARD);
        EnemyCard card2 = new EnemyCard();
        card2.setId(2);
        card2.setCardType(CardType.ENEMY_CARD);
        list.setEnemyCardList(new ArrayList<>(Arrays.asList(card1, card2)));

        return list;
    }

    private ItemCardList createMockItemCardList() {
        ItemCardList list = new ItemCardList();
        ItemCard card1 = new ItemCard();
        card1.setId(1);
        card1.setCardType(CardType.ITEM_CARD);
        ItemCard card2 = new ItemCard();
        card2.setId(2);
        card2.setCardType(CardType.ITEM_CARD);
        list.setItemCardList(new ArrayList<>(Arrays.asList(card1, card2)));

        return list;
    }

    private CharacterList createMockCharacterList() {
        CharacterList list = new CharacterList();
        Character card1 = new Character();
        card1.setId(1);
        card1.setInitialHealth(11);
        card1.setInitialStrength(43);
        Character card2 = new Character();
        card2.setId(2);
        card2.setInitialHealth(2);
        card2.setInitialStrength(154);
        list.setCharacterList(new ArrayList<>(Arrays.asList(card1, card2)));

        return list;
    }

    private PlayedBoard createMockPlayedBoard() {
        PlayedBoard board = new PlayedBoard();
        board.setId(100);

        return board;
    }

    private FieldList createMockFieldList() {
        FieldList list = new FieldList();
        Field field1 = new Field();
        field1.setId(1);
        Field field2 = new Field();
        field2.setId(2);
        list.setFieldList(new ArrayList<>(Arrays.asList(field1, field2)));

        return list;
    }
}
