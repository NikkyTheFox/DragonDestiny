package pl.edu.pg.eti.dragondestiny.playedgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardType;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.Character;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.CharacterList;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldList;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldType;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGame;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.repository.PlayedGameRepository;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.service.PlayedGameService;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.service.ServiceException;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.PlayerList;
import pl.edu.pg.eti.dragondestiny.playedgame.player.service.PlayerService;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.Round;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayedGameServiceTests {

    @InjectMocks
    PlayedGameService playedGameService;

    /**
     * PlayerService dependency.
     */
    @Mock
    private PlayerService playerServiceMock;

    /**
     * PlayedGameRepository dependency.
     */
    @Mock
    private PlayedGameRepository playedGameRepositoryMock;

    /**
     * Mvc Mock.
     */
    private MockMvc mockMvc;

    private PlayedGame playedGame;

    private String playedGameId;
    private String playerLogin;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        EnemyCard enemyCard1 = new EnemyCard();
        enemyCard1.setId(1);
        enemyCard1.setCardType(CardType.ENEMY_CARD);
        EnemyCard enemyCard2 = new EnemyCard();
        enemyCard2.setId(2);
        enemyCard2.setCardType(CardType.ENEMY_CARD);
        EnemyCard enemyCard3 = new EnemyCard();
        enemyCard3.setId(3);
        enemyCard3.setCardType(CardType.ENEMY_CARD);
        ItemCard itemCard1 = new ItemCard();
        itemCard1.setId(10);
        itemCard1.setCardType(CardType.ITEM_CARD);
        ItemCard itemCard2 = new ItemCard();
        itemCard2.setId(20);
        itemCard2.setCardType(CardType.ITEM_CARD);
        ItemCard itemCard3 = new ItemCard();
        itemCard3.setId(30);
        itemCard3.setCardType(CardType.ITEM_CARD);
        itemCard3.setHealth(10);
        itemCard3.setStrength(20);

        Field fieldBridge = new Field();
        fieldBridge.setId(16);
        fieldBridge.setType(FieldType.BRIDGE_FIELD);
        fieldBridge.setXPosition(1);
        fieldBridge.setYPosition(2);
        Field fieldBoss = new Field();
        fieldBoss.setId(17);
        fieldBoss.setType(FieldType.BOSS_FIELD);
        fieldBoss.setXPosition(0);
        fieldBoss.setYPosition(0);

        PlayedBoard board = new PlayedBoard();
        board.setId(1);
        for (int d = 0; d < 15; d++) {
            Field f = new Field();
            f.setId(d + 1);
            f.setType(FieldType.LOSE_ONE_ROUND);
            board.getFieldsOnBoard().add(f);
        }
        board.getFieldsOnBoard().get(0).setEnemy(enemyCard1);
        board.getFieldsOnBoard().add(fieldBridge);
        board.getFieldsOnBoard().add(fieldBoss);

        Character character1 = new Character();
        character1.setId(1);
        character1.setField(board.getFieldsOnBoard().get(0));
        Character character2 = new Character();
        character2.setId(2);
        character2.setField(board.getFieldsOnBoard().get(1));

        Player player = new Player();
        playerLogin = "luna";
        player.setLogin(playerLogin);
        player.setCardsOnHand(new ArrayList<>(List.of(itemCard3)));
        player.setCharacter(character1);
        player.setTrophies(new ArrayList<>(List.of(enemyCard2)));
        Player player2 = new Player();
        player2.setLogin("albus");
        player2.setCharacter(character1);

        Round activeRound = new Round();
        activeRound.setId(1);

        playedGame = new PlayedGame();
        playedGameId = "123";
        playedGame.setId(playedGameId);
        playedGame.setBoard(board);
        playedGame.setCardDeck(new ArrayList<>(Arrays.asList(enemyCard2, itemCard1, itemCard2)));
        playedGame.setUsedCardDeck(new ArrayList<>(List.of(enemyCard3)));
        playedGame.setCharactersInGame(new ArrayList<>(Arrays.asList(character1, character2)));
        playedGame.setPlayers(new ArrayList<>(List.of(player, player2)));
        playedGame.setActiveRound(activeRound);

        when(playedGameRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        when(playedGameRepositoryMock.findById(playedGameId)).thenReturn(Optional.of(playedGame));
        when(playedGameRepositoryMock.findAll()).thenReturn(new ArrayList<>(Collections.singletonList(playedGame)));
        when(playedGameRepositoryMock.findCardDeck(playedGameId)).thenReturn(playedGame.getCardDeck());
        when(playedGameRepositoryMock.findUsedCardDeck(playedGameId)).thenReturn(playedGame.getUsedCardDeck());
        when(playedGameRepositoryMock.findCardByIdInCardDeck(anyString(), anyInt())).thenReturn(new ArrayList<>());
        when(playedGameRepositoryMock.findCardByIdInCardDeck(playedGameId, 2)).thenReturn(new ArrayList<>(List.of(enemyCard2)));
        when(playedGameRepositoryMock.findCardByIdInCardDeck(playedGameId, 10)).thenReturn(new ArrayList<>(List.of(itemCard1)));
        when(playedGameRepositoryMock.findCardByIdInCardDeck(playedGameId, 20)).thenReturn(new ArrayList<>(List.of(itemCard2)));
        when(playedGameRepositoryMock.findCardByIdInUsedDeck(playedGameId, 3)).thenReturn(playedGame.getUsedCardDeck());
        when(playedGameRepositoryMock.findCardByIdInPlayerHand(playedGameId, playerLogin, 30)).thenReturn(player.getCardsOnHand());
        when(playedGameRepositoryMock.findCardsInPlayerHand(anyString(), anyString())).thenReturn(new ArrayList<>());
        when(playedGameRepositoryMock.findCardsInPlayerHand(playedGameId, playerLogin)).thenReturn(new ArrayList<>(List.of(itemCard3)));
        when(playedGameRepositoryMock.findPlayerByLogin(playedGameId, playerLogin)).thenReturn(new ArrayList<>(List.of(player)));
        when(playedGameRepositoryMock.findPlayers(playedGameId)).thenReturn(playedGame.getPlayers());
        when(playedGameRepositoryMock.findPlayersTrophies(playedGameId, playerLogin)).thenReturn(player.getTrophies());
        when(playedGameRepositoryMock.findCharacters(anyString())).thenReturn(new ArrayList<>());
        when(playedGameRepositoryMock.findCharacters(playedGameId)).thenReturn(playedGame.getCharactersInGame());
        when(playedGameRepositoryMock.findCharacterById(anyString(), anyInt())).thenReturn(new ArrayList<>());
        when(playedGameRepositoryMock.findCharacterById(playedGameId, 1)).thenReturn(new ArrayList<>(List.of(character1)));
        when(playedGameRepositoryMock.findCharacterById(playedGameId, 2)).thenReturn(new ArrayList<>(List.of(character2)));
        when(playedGameRepositoryMock.findEnemyOnField(eq(playedGameId), anyInt()))
                .thenAnswer(invocation -> {
                    int fieldId = invocation.getArgument(1);
                    if (fieldId == 1) {
                        return new ArrayList(Collections.singletonList(playedGame.getBoard().getFieldsOnBoard().stream().filter(f -> f.getId().equals(fieldId)).findFirst().get().getEnemy()));
                    }
                    return new ArrayList<>();
                });
        when(playedGameRepositoryMock.findFieldOnBoard(eq(playedGameId), anyInt())).thenAnswer(invocation -> {
            int fieldId = invocation.getArgument(1);
            List<Field> fieldList = playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(fieldId)).collect(Collectors.toList());
            if (fieldList.isEmpty()) {
                return new ArrayList<>();
            }
            return fieldList;
        });
        when(playedGameRepositoryMock.findBoard(playedGameId)).thenReturn(new ArrayList<>(Collections.singletonList(playedGame.getBoard())));
        when(playedGameRepositoryMock.findActiveRound(playedGameId)).thenReturn(new ArrayList<>(Collections.singletonList(playedGame.getActiveRound())));
        when(playedGameRepositoryMock.findFieldsOnBoard(playedGameId)).thenReturn(playedGame.getBoard().getFieldsOnBoard());
        when(playedGameRepositoryMock.findPlayersByField(playedGameId, 1)).thenReturn(new ArrayList<>(List.of(player, player2)));
        when(playedGameRepositoryMock.findHealthCardsInPlayerHand(playedGameId, playerLogin)).thenReturn(new ArrayList<>(player.getCardsOnHand().stream().filter(itemCard -> itemCard.getHealth() > 0).collect(Collectors.toList())));
        when(playedGameRepositoryMock.findStrengthCardsInPlayerHand(playedGameId, playerLogin)).thenReturn(new ArrayList<>(player.getCardsOnHand().stream().filter(itemCard -> itemCard.getStrength() > 0).collect(Collectors.toList())));
        when(playedGameRepositoryMock.save(any(PlayedGame.class))).thenAnswer(invocation -> invocation.getArgument(0));

    }

    @Test
    public void testGetPlayedGameFound() {
        // Act
        Optional<PlayedGame> playedGameFound = playedGameService.findPlayedGame(playedGameId);

        // Assert
        assertTrue(playedGameFound.isPresent());
        assertEquals(playedGame, playedGameFound.get());
    }

    @Test
    public void testGetPlayedGameNotFound() {
        // Arrange
        String playedGameId = "NotExisting";

        // Act
        Optional<PlayedGame> playedGameFound = playedGameService.findPlayedGame(playedGameId);

        // Assert
        assertTrue(playedGameFound.isEmpty());
    }

    @Test
    public void testGetAllPlayedGames() {
        // Arrange
        List<PlayedGame> playedGamesToFind = new ArrayList<>(Collections.singletonList(playedGame));

        // Act
        List<PlayedGame> playedGamesFound = playedGameService.findPlayedGames();

        // Assert
        assertEquals(playedGamesToFind.size(), playedGamesFound.size());
        for (int d = 0; d < playedGamesFound.size(); d++) {
            assertEquals(playedGamesToFind.get(d), playedGamesFound.get(d));
        }
    }

    @Test
    public void testDeletePlayedGameFound() {
        // Act
        Boolean response = playedGameService.delete(playedGameId);

        // Assert
        assertTrue(response);
    }

    @Test
    public void testDeletePlayedGameNotFound() {
        // Arrange
        String playedGameId = "NotExisting";

        // Act
        Boolean response = playedGameService.delete(playedGameId);

        // Assert
        assertFalse(response);
    }

    @Test
    public void testFindCardDeck() {
        CardList cardListToFind = new CardList(playedGame.getCardDeck());

        // Act
        Optional<CardList> cardListFound = playedGameService.findCardDeck(playedGameId);

        // Assert
        assertTrue(cardListFound.isPresent());
        assertEquals(cardListToFind.getCardList().size(), cardListFound.get().getCardList().size());
        for (int d = 0; d < cardListFound.get().getCardList().size(); d++) {
            assertEquals(cardListToFind.getCardList().get(d), cardListFound.get().getCardList().get(d));
        }
    }

    @Test
    public void testFindCardDeckNotExisting() {
        // Arrange
        String playedGameId = "NotExisting";

        // Act
        Optional<CardList> cardListFound = playedGameService.findCardDeck(playedGameId);

        // Assert
        assertTrue(cardListFound.isEmpty());
    }

    @Test
    public void testFindUsedCardDeck() {
        CardList cardListToFind = new CardList(playedGame.getUsedCardDeck());

        // Act
        Optional<CardList> cardListFound = playedGameService.findUsedCardDeck(playedGameId);

        // Assert
        assertTrue(cardListFound.isPresent());
        assertEquals(cardListToFind.getCardList().size(), cardListFound.get().getCardList().size());
        for (int d = 0; d < cardListFound.get().getCardList().size(); d++) {
            assertEquals(cardListToFind.getCardList().get(d), cardListFound.get().getCardList().get(d));
        }
    }

    @Test
    public void testFindUsedCardDeckNotExisting() {
        // Arrange
        String playedGameId = "NotExisting";

        // Act
        Optional<CardList> cardListFound = playedGameService.findUsedCardDeck(playedGameId);

        // Assert
        assertTrue(cardListFound.isEmpty());
    }

    @Test
    public void testFindCardInCardDeck() {
        int cardId = 2;
        Optional<Card> cardToFind = playedGame.getCardDeck().stream().filter(card -> card.getId().equals(cardId)).findFirst();

        // Act
        Optional<Card> cardFound = playedGameService.findCardInCardDeck(playedGameId, cardId);

        // Assert
        assertTrue(cardFound.isPresent());
        assertEquals(cardToFind.get(), cardFound.get());
    }

    @Test
    public void testFindCardInCardDeckNotFound() {
        int cardId = 213;

        // Act
        Optional<Card> cardFound = playedGameService.findCardInCardDeck(playedGameId, cardId);

        // Assert
        assertTrue(cardFound.isEmpty());
    }

    @Test
    public void testFindCardInUsedCardDeck() {
        int cardId = 3;
        Optional<Card> cardToFind = playedGame.getUsedCardDeck().stream().filter(card -> card.getId().equals(cardId)).findFirst();

        // Act
        Optional<Card> cardFound = playedGameService.findCardInUsedCardDeck(playedGameId, cardId);

        // Assert
        assertTrue(cardFound.isPresent());
        assertEquals(cardToFind.get(), cardFound.get());
    }

    @Test
    public void testFindCardInUsedCardDeckNotFound() {
        int cardId = 213;

        // Act
        Optional<Card> cardFound = playedGameService.findCardInUsedCardDeck(playedGameId, cardId);

        // Assert
        assertTrue(cardFound.isEmpty());
    }

    @Test
    public void testFindCardInPlayer() {
        // Arrange
        int cardId = 30;
        Optional<ItemCard> cardToFind = playedGame.getPlayers().stream().filter(card -> card.getLogin().equals(playerLogin)).findFirst().get().getCardsOnHand().stream().filter(itemCard -> itemCard.getId().equals(cardId)).findFirst();

        // Act
        Optional<ItemCard> cardFound = playedGameService.findCardInPlayerHand(playedGameId, playerLogin, cardId);

        // Assert
        assertTrue(cardFound.isPresent());
        assertEquals(cardToFind.get(), cardFound.get());
    }

    @Test
    public void testFindCardInPlayerNotFound() {
        // Arrange
        int cardId = 213;
        String login = "nonExisting";

        // Act
        Optional<ItemCard> cardFound = playedGameService.findCardInPlayerHand(playedGameId, playerLogin, cardId);

        // Assert
        assertTrue(cardFound.isEmpty());
    }

    @Test
    public void testFindPlayer() {
        // Arrange
        Optional<Player> playerToFind = playedGame.getPlayers().stream().filter(card -> card.getLogin().equals(playerLogin)).findFirst();

        // Act
        Optional<Player> playerFound = playedGameService.findPlayer(playedGameId, playerLogin);

        // Assert
        assertTrue(playerFound.isPresent());
        assertEquals(playerToFind.get(), playerFound.get());
    }

    @Test
    public void testFindPlayerNotFound() {
        // Arrange
        String playerLogin = "nonExisting";

        // Act
        Optional<Player> playerFound = playedGameService.findPlayer(playedGameId, playerLogin);

        // Assert
        assertTrue(playerFound.isEmpty());
    }

    @Test
    public void testFindPlayersCharacter() throws ServiceException {
        // Arrange
        Character characterToFind = playedGame.getPlayers().stream().filter(card -> card.getLogin().equals(playerLogin)).findFirst().get().getCharacter();

        // Act
        Optional<Character> characterFound = playedGameService.findPlayersCharacter(playedGameId, playerLogin);

        // Assert
        assertTrue(characterFound.isPresent());
        assertEquals(characterToFind, characterFound.get());
    }

    @Test
    public void testFindPlayersCharacterNotFound() throws ServiceException {
        // Arrange
        String playerLogin = "nonExisting";

        // Act
        Optional<Character> characterFound = playedGameService.findPlayersCharacter(playedGameId, playerLogin);

        // Assert
        assertTrue(characterFound.isEmpty());
    }

    @Test
    public void testFindPlayersHandCards() {
        // Arrange
        ItemCardList cardsToFind = new ItemCardList(playedGame.getPlayers().stream().filter(card -> card.getLogin().equals(playerLogin)).findFirst().get().getCardsOnHand());

        // Act
        Optional<ItemCardList> cardsFound = playedGameService.findPlayersHandCards(playedGameId, playerLogin);

        // Assert
        assertTrue(cardsFound.isPresent());
        assertEquals(cardsToFind.getItemCardList().size(), cardsFound.get().getItemCardList().size());
        for (int d = 0; d < cardsFound.get().getItemCardList().size(); d++) {
            assertEquals(cardsToFind.getItemCardList().get(d), cardsFound.get().getItemCardList().get(d));
        }
    }

    @Test
    public void testFindPlayersHandCardsNotFound() {
        // Arrange
        String playerLogin = "nonExisting";

        // Act
        Optional<ItemCardList> cardsFound = playedGameService.findPlayersHandCards(playedGameId, playerLogin);

        // Assert
        assertTrue(cardsFound.isEmpty());
    }

    @Test
    public void testFindPlayersTrophies() {
        // Arrange
        EnemyCardList cardsToFind = new EnemyCardList(playedGame.getPlayers().stream().filter(card -> card.getLogin().equals(playerLogin)).findFirst().get().getTrophies());

        // Act
        Optional<EnemyCardList> cardsFound = playedGameService.findPlayerTrophies(playedGameId, playerLogin);

        // Assert
        assertTrue(cardsFound.isPresent());
        assertEquals(cardsToFind.getEnemyCardList().size(), cardsFound.get().getEnemyCardList().size());
        for (int d = 0; d < cardsFound.get().getEnemyCardList().size(); d++) {
            assertEquals(cardsToFind.getEnemyCardList().get(d), cardsFound.get().getEnemyCardList().get(d));
        }
    }

    @Test
    public void testFindPlayersTrophiesNotFound() {
        // Arrange
        String playerLogin = "nonExisting";

        // Act
        Optional<EnemyCardList> cardsFound = playedGameService.findPlayerTrophies(playedGameId, playerLogin);

        // Assert
        assertTrue(cardsFound.isEmpty());
    }

    @Test
    public void testFindCharacters() {
        // Arrange
        CharacterList charactersToFind = new CharacterList(playedGame.getCharactersInGame());

        // Act
        Optional<CharacterList> charactersFound = playedGameService.findCharacters(playedGameId);

        // Assert
        assertTrue(charactersFound.isPresent());
        assertEquals(charactersToFind.getCharacterList().size(), charactersFound.get().getCharacterList().size());
        for (int d = 0; d < charactersFound.get().getCharacterList().size(); d++) {
            assertEquals(charactersToFind.getCharacterList().get(d), charactersFound.get().getCharacterList().get(d));
        }
    }

    @Test
    public void testFindCharactersNotFound() {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<CharacterList> charactersFound = playedGameService.findCharacters(playedGameId);
        // Assert
        assertTrue(charactersFound.isEmpty());
    }

    @Test
    public void testFindCharacter() {
        // Arrange
        int characterId = 1;
        Optional<Character> characterToFind = playedGame.getCharactersInGame().stream().filter(character -> character.getId().equals(characterId)).findFirst();
        // Act
        Optional<Character> characterFound = playedGameService.findCharacter(playedGameId, characterId);
        // Assert
        assertTrue(characterFound.isPresent());
        assertEquals(characterToFind, characterFound);
    }

    @Test
    public void testFindCharacterNotFound() {
        // Arrange
        int characterId = 231;
        // Act
        Optional<Character> characterFound = playedGameService.findCharacter(playedGameId, characterId);
        // Assert
        assertTrue(characterFound.isEmpty());
    }

    @Test
    public void testFindCharactersInUse() {
        // Arrange
        CharacterList charactersToFind = new CharacterList(playedGame.getPlayers().stream().map(Player::getCharacter).collect(Collectors.toList()));
        // Act
        Optional<CharacterList> charactersFound = playedGameService.findCharactersInUse(playedGameId);
        // Assert
        assertTrue(charactersFound.isPresent());
        assertEquals(charactersToFind.getCharacterList().size(), charactersFound.get().getCharacterList().size());
        for (int d = 0; d < charactersFound.get().getCharacterList().size(); d++) {
            assertEquals(charactersToFind.getCharacterList().get(d), charactersFound.get().getCharacterList().get(d));
        }
    }

    @Test
    public void testFindCharactersInUseNotFound() {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<CharacterList> charactersFound = playedGameService.findCharactersInUse(playedGameId);
        // Assert
        assertTrue(charactersFound.isEmpty());
    }

    @Test
    public void testFindCharactersNotInUse() throws ServiceException {
        // Arrange
        List<Character> charactersInGame = playedGame.getCharactersInGame();
        List<Character> charactersInPlayers = playedGame.getPlayers().stream().map(Player::getCharacter).collect(Collectors.toList());
        charactersInGame.removeAll(charactersInPlayers);
        CharacterList charactersToFind = new CharacterList(charactersInGame);
        // Act
        Optional<CharacterList> charactersFound = playedGameService.findCharactersNotInUse(playedGameId);
        // Assert
        assertTrue(charactersFound.isPresent());
        assertEquals(charactersToFind.getCharacterList().size(), charactersFound.get().getCharacterList().size());
        for (int d = 0; d < charactersFound.get().getCharacterList().size(); d++) {
            assertEquals(charactersToFind.getCharacterList().get(d), charactersFound.get().getCharacterList().get(d));
        }
    }

    @Test
    public void testFindCharactersNotInUseNotFound() throws ServiceException {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<CharacterList> charactersFound = playedGameService.findCharactersNotInUse(playedGameId);
        // Assert
        assertTrue(charactersFound.isEmpty());
    }

    @Test
    public void testFindEnemyCardsOnField() {
        // Arrange
        int fieldId = 1;
        EnemyCard enemyCardListToFind = playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(fieldId)).findFirst().map(Field::getEnemy).get();
        // Act
        Optional<EnemyCardList> enemyCardListFound = playedGameService.findEnemyCardsOnField(playedGameId, fieldId);
        // Assert
        assertTrue(enemyCardListFound.isPresent());
        assertEquals(enemyCardListToFind, enemyCardListFound.get().getEnemyCardList().get(0));
    }

    @Test
    public void testFindEnemyCardsOnFieldNotFound() {
        // Arrange
        int fieldId = 2;
        // Act
        Optional<EnemyCardList> enemyCardListFound = playedGameService.findEnemyCardsOnField(playedGameId, fieldId);
        // Assert
        assertTrue(enemyCardListFound.isPresent());
        assertTrue(enemyCardListFound.get().getEnemyCardList().isEmpty());
    }

    @Test
    public void testFindEnemyCardsOnPlayersField() throws ServiceException {
        // Arrange
        int fieldId = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getPositionField().getId();
        EnemyCard enemyCardListToFind = playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(fieldId)).findFirst().map(Field::getEnemy).get();
        // Act
        Optional<EnemyCardList> enemyCardListFound = playedGameService.findEnemyCardOnPlayersField(playedGameId, playerLogin);
        // Assert
        assertTrue(enemyCardListFound.isPresent());
        assertEquals(enemyCardListToFind, enemyCardListFound.get().getEnemyCardList().get(0));
    }

    @Test
    public void testFindEnemyCardsOnPlayersFieldNotFound() throws ServiceException {
        // Arrange
        String playerLogin = "nonExisting";
        // Act
        Optional<EnemyCardList> enemyCardListFound = playedGameService.findEnemyCardOnPlayersField(playedGameId, playerLogin);
        // Assert
        assertTrue(enemyCardListFound.isEmpty());
    }

    @Test
    public void testFindField() {
        // Arrange
        int fieldId = 1;
        Optional<Field> fieldToFind = playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(fieldId)).findFirst();
        // Act
        Optional<Field> fieldFound = playedGameService.findField(playedGameId, fieldId);
        // Assert
        assertTrue(fieldFound.isPresent());
        assertEquals(fieldToFind.get(), fieldFound.get());
    }

    @Test
    public void testFindFieldNotFound() {
        // Arrange
        int fieldId = 1321;
        // Act
        Optional<Field> fieldFound = playedGameService.findField(playedGameId, fieldId);
        // Assert
        assertTrue(fieldFound.isEmpty());
    }

    @Test
    public void testFindBoard() {
        // Arrange
        PlayedBoard boardToFind = playedGame.getBoard();
        // Act
        Optional<PlayedBoard> boardFound = playedGameService.findBoard(playedGameId);
        // Assert
        assertTrue(boardFound.isPresent());
        assertEquals(boardToFind, boardFound.get());
    }

    @Test
    public void testFindBoardNotFound() {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<PlayedBoard> boardFound = playedGameService.findBoard(playedGameId);
        // Assert
        assertTrue(boardFound.isEmpty());
    }

    @Test
    public void testFindActiveRound() throws ServiceException {
        // Arrange
        Round roundToFind = playedGame.getActiveRound();
        // Act
        Optional<Round> roundFound = playedGameService.findActiveRound(playedGameId);
        // Assert
        assertTrue(roundFound.isPresent());
        assertEquals(roundToFind, roundFound.get());
    }

    @Test
    public void testFindActiveRoundNotFound() throws ServiceException {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<Round> roundFound = playedGameService.findActiveRound(playedGameId);
        // Assert
        assertTrue(roundFound.isEmpty());
    }

    @Test
    public void testFindFields() {
        // Arrange
        FieldList fieldListToFind = new FieldList(playedGame.getBoard().getFieldsOnBoard());
        // Act
        Optional<FieldList> fieldListFound = playedGameService.findFields(playedGameId);
        // Assert
        assertTrue(fieldListFound.isPresent());
        assertEquals(fieldListToFind.getFieldList().size(), fieldListFound.get().getFieldList().size());
        for (int d = 0; d < fieldListToFind.getFieldList().size(); d++) {
            assertEquals(fieldListToFind.getFieldList().get(d), fieldListFound.get().getFieldList().get(d));
        }
    }

    @Test
    public void testFindFieldsNotFound() {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<FieldList> fieldListFound = playedGameService.findFields(playedGameId);
        // Assert
        assertTrue(fieldListFound.isEmpty());
    }

    @Test
    public void testFindPlayers() {
        // Arrange
        PlayerList playerListToFind = new PlayerList(playedGame.getPlayers());
        // Act
        Optional<PlayerList> playerListFound = playedGameService.findPlayers(playedGameId);
        // Assert
        assertTrue(playerListFound.isPresent());
        assertEquals(playerListToFind.getPlayerList().size(), playerListFound.get().getPlayerList().size());
        for (int d = 0; d < playerListToFind.getPlayerList().size(); d++) {
            assertEquals(playerListToFind.getPlayerList().get(d), playerListFound.get().getPlayerList().get(d));
        }
    }

    @Test
    public void testFindPlayersNotFound() {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<PlayerList> playerListFound = playedGameService.findPlayers(playedGameId);
        // Assert
        assertTrue(playerListFound.isEmpty());
    }

    @Test
    public void testFindPlayersByField() {
        // Arrange
        int fieldId = 1;
        PlayerList playerListToFind = new PlayerList(playedGame.getPlayers().stream().filter(player -> player.getPositionField().getId().equals(fieldId)).collect(Collectors.toList()));
        // Act
        Optional<PlayerList> playerListFound = playedGameService.findPlayersByField(playedGameId, fieldId);
        // Assert
        assertTrue(playerListFound.isPresent());
        assertEquals(playerListToFind.getPlayerList().size(), playerListFound.get().getPlayerList().size());
        for (int d = 0; d < playerListToFind.getPlayerList().size(); d++) {
            assertEquals(playerListToFind.getPlayerList().get(d), playerListFound.get().getPlayerList().get(d));
        }
    }

    @Test
    public void testFindPlayersByFieldNotFound() {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<PlayerList> playerListFound = playedGameService.findPlayers(playedGameId);
        // Assert
        assertTrue(playerListFound.isEmpty());
    }

    @Test
    public void testFindDifferentPlayersByField() throws ServiceException {
        // Arrange
        int fieldId = 1;
        Player player2 = new Player();
        player2.setLogin("hagrid");
        player2.setCharacter(new Character());
        player2.setPositionField(playedGame.getPlayers().get(0).getPositionField());
        playedGame.addPlayerToGame(player2);
        PlayerList playerListToFind = new PlayerList(playedGame.getPlayers().stream().filter(player -> player.getPositionField().getId().equals(fieldId) && !player.getLogin().equals(playerLogin)).collect(Collectors.toList()));
        when(playedGameRepositoryMock.findDifferentPlayersByField(playedGameId, playerLogin, fieldId)).thenReturn(playerListToFind.getPlayerList());
        // Act
        Optional<PlayerList> playerListFound = playedGameService.findDifferentPlayersByField(playedGameId, playerLogin);
        // Assert
        assertTrue(playerListFound.isPresent());
        assertEquals(playerListToFind.getPlayerList().size(), playerListFound.get().getPlayerList().size());
        for (int d = 0; d < playerListToFind.getPlayerList().size(); d++) {
            assertEquals(playerListToFind.getPlayerList().get(d), playerListFound.get().getPlayerList().get(d));
        }
    }

    @Test
    public void testFindDifferentPlayersByFieldNotFound() throws ServiceException {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<PlayerList> playerListFound = playedGameService.findDifferentPlayersByField(playedGameId, playerLogin);
        // Assert
        assertTrue(playerListFound.isEmpty());
    }

    @Test
    public void testFindHealthCardsInPlayer() {
        // Arrange
        ItemCardList cardListToFind = new ItemCardList(playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getCardsOnHand().stream().filter(itemCard -> itemCard.getHealth() > 0).collect(Collectors.toList()));
        // Act
        Optional<ItemCardList> cardListFound = playedGameService.findHealthCardsInPlayer(playedGameId, playerLogin);
        // Assert
        assertTrue(cardListFound.isPresent());
        assertEquals(cardListToFind.getItemCardList().size(), cardListFound.get().getItemCardList().size());
        for (int d = 0; d < cardListToFind.getItemCardList().size(); d++) {
            assertEquals(cardListToFind.getItemCardList().get(d), cardListFound.get().getItemCardList().get(d));
        }
    }

    @Test
    public void testFindHealthCardsInPlayerNotFound() {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<ItemCardList> cardListFound = playedGameService.findHealthCardsInPlayer(playedGameId, playerLogin);
        // Assert
        assertTrue(cardListFound.isEmpty());
    }

    @Test
    public void testFindStrengthCardsInPlayer() {
        // Arrange
        ItemCardList cardListToFind = new ItemCardList(playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getCardsOnHand().stream().filter(itemCard -> itemCard.getStrength() > 0).collect(Collectors.toList()));
        // Act
        Optional<ItemCardList> cardListFound = playedGameService.findStrengthCardsInPlayer(playedGameId, playerLogin);
        // Assert
        assertTrue(cardListFound.isPresent());
        assertEquals(cardListToFind.getItemCardList().size(), cardListFound.get().getItemCardList().size());
        for (int d = 0; d < cardListToFind.getItemCardList().size(); d++) {
            assertEquals(cardListToFind.getItemCardList().get(d), cardListFound.get().getItemCardList().get(d));
        }
    }

    @Test
    public void testFindStrengthCardsInPlayerNotFound() {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<ItemCardList> cardListFound = playedGameService.findStrengthCardsInPlayer(playedGameId, playerLogin);
        // Assert
        assertTrue(cardListFound.isEmpty());
    }

    @Test
    public void testStartGame() throws ServiceException {
        // Act
        Optional<PlayedGame> playedGameStarted = playedGameService.startGame(playedGameId, true);
        // Assert
        assertTrue(playedGameStarted.isPresent());
        assertEquals(playedGameStarted.get().getActiveRound().getId(), 1);
        assertNotNull(playedGameStarted.get().getActiveRound().getActivePlayer());
        assertTrue(playedGameStarted.get().getIsStarted());
        assertEquals(playedGame.getPlayers().size(), playedGameStarted.get().getPlayers().size());
    }

    @Test
    public void testStartGameNotFound() throws ServiceException {
        // Arrange
        String playedGameId = "nonExisting";
        // Act
        Optional<PlayedGame> playedGameStarted = playedGameService.startGame(playedGameId, true);
        // Assert
        assertTrue(playedGameStarted.isEmpty());
    }

    @Test
    public void testNextRound() throws ServiceException {
        // Arrange
        playedGame.setIsStarted(false);
        Optional<PlayedGame> playedGame = playedGameService.startGame(playedGameId, true);
        List<Player> playerList = playedGame.get().getPlayers();
        int roundId = playedGame.get().getActiveRound().getId();
        int activePlayerId = playerList.indexOf(playedGame.get().getActiveRound().getActivePlayer());
        int roundsSize = playedGame.get().getRounds().size();
        Player nextPlayer = playerList.get(activePlayerId + 1);
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.nextRound(playedGameId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertEquals(roundId + 1, playedGameUpdated.get().getActiveRound().getId());
        assertEquals(nextPlayer, playedGameUpdated.get().getActiveRound().getActivePlayer());
        assertEquals(roundsSize + 1, playedGameUpdated.get().getRounds().size());
    }

    @Test
    public void testNextRoundBlockedPlayer() throws ServiceException {
        // Arrange
        playedGame.setIsStarted(false);
        Optional<PlayedGame> playedGame = playedGameService.startGame(playedGameId, true);
        int blockedTurns = 1;
        int blockedTurnsExpected = blockedTurns - 1;
        playedGame.get().getPlayers().get(1).setBlockedTurns(blockedTurns);
        List<Player> playerList = playedGame.get().getPlayers();
        int roundIdExpected = playedGame.get().getActiveRound().getId() + 2;
        int activePlayerId = playerList.indexOf(playedGame.get().getActiveRound().getActivePlayer());
        int roundsSizeExpected = playedGame.get().getRounds().size() + 1;
        Player nextPlayer = playerList.get(activePlayerId);
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.nextRound(playedGameId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertEquals(roundIdExpected, playedGameUpdated.get().getActiveRound().getId());
        assertEquals(nextPlayer, playedGameUpdated.get().getActiveRound().getActivePlayer());
        assertEquals(blockedTurnsExpected, nextPlayer.getBlockedTurns());
        assertEquals(roundsSizeExpected, playedGameUpdated.get().getRounds().size());
    }

    @Test
    public void testNextRoundBlockedPlayers() throws ServiceException {
        // Arrange
        playedGame.setIsStarted(false);
        Optional<PlayedGame> playedGame = playedGameService.startGame(playedGameId, true);
        int blockedTurns0 = 1;
        int blockedTurns1 = 1;
        playedGame.get().getPlayers().get(0).setBlockedTurns(blockedTurns0);
        playedGame.get().getPlayers().get(1).setBlockedTurns(blockedTurns1);
        List<Player> playerList = playedGame.get().getPlayers();
        int roundIdExpected = playedGame.get().getActiveRound().getId() + 3;
        int activePlayerId = playerList.indexOf(playedGame.get().getActiveRound().getActivePlayer());
        Player nextPlayer = playerList.get(activePlayerId + 1);
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.nextRound(playedGameId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertEquals(roundIdExpected, playedGameUpdated.get().getActiveRound().getId());
        assertEquals(nextPlayer, playedGameUpdated.get().getActiveRound().getActivePlayer());
    }

    @Test
    public void testNextRoundNotFound() throws ServiceException {
        // Arrange
        playedGame.setIsStarted(false);
        // Act and Assert
        assertThrows(ServiceException.class, () -> playedGameService.nextRound(playedGameId));
    }

    @Test
    public void testAddPlayer() throws ServiceException {
        // Arrange
        Player newPlayer = new Player();
        String login = "minerva";
        newPlayer.setLogin(login);
        newPlayer.setCharacter(new Character());
        List<Player> playerListBefore = new ArrayList<>(playedGame.getPlayers());
        when(playerServiceMock.findByLogin(login)).thenReturn(Optional.of(newPlayer));
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.addPlayer(playedGameId, login);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertEquals(playerListBefore.size() + 1, playedGameUpdated.get().getPlayers().size());
    }

    @Test
    public void testAddPlayerNotFound() throws ServiceException {
        // Arrange
        Player newPlayer = new Player();
        String login = "minerva";
        newPlayer.setLogin(login);
        newPlayer.setCharacter(new Character());
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.addPlayer(playedGameId, login);
        // Assert
        assertTrue(playedGameUpdated.isEmpty());
    }

    @Test
    public void testAssignCharacterToPlayer() throws ServiceException {
        // Arrange
        int characterId = 2;
        Optional<Character> characterToAdd = playedGame.getCharactersInGame().stream().filter(character -> character.getId().equals(characterId)).findFirst();
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.assignCharacterToPlayer(playedGameId, playerLogin, characterId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertEquals(characterToAdd.get(), playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getCharacter());
        assertEquals(characterToAdd.get().getField(), playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getPositionField());
    }

    @Test
    public void testAssignCharacterToPlayerNotFound() throws ServiceException {
        // Arrange
        int characterId = 241;
        // Act & Assert
        assertThrows(ServiceException.class, () -> playedGameService.assignCharacterToPlayer(playedGameId, playerLogin, characterId));
    }

    @Test
    public void testSetFightRoll() {
        // Arrange
        int fightRoll = 2;
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.setPlayerFightRoll(playedGameId, playerLogin, fightRoll);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertEquals(fightRoll, playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getFightRoll());
    }

    @Test
    public void testSetFightRollNotFound() {
        // Arrange
        String playerLogin = "nonExisting";
        int fightRoll = 2;
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.setPlayerFightRoll(playedGameId, playerLogin, fightRoll);
        // Assert
        assertTrue(playedGameUpdated.isEmpty());
    }

    @Test
    public void testMoveCardFromCardDeckToUsed() throws ServiceException {
        // Arrange
        int cardId = 20;
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.moveCardFromCardDeckToUsedCardDeck(playedGameId, cardId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertTrue(playedGameUpdated.get().getUsedCardDeck().stream().filter(card -> card.getId().equals(cardId)).findFirst().isPresent());
        assertTrue(playedGameUpdated.get().getCardDeck().stream().filter(card -> card.getId().equals(cardId)).findFirst().isEmpty());
    }

    @Test
    public void testMoveCardFromCardDeckToUsedNotFound() throws ServiceException {
        // Arrange
        int cardId = 142124;
        // Act & Assert
        assertThrows(ServiceException.class, () -> playedGameService.moveCardFromCardDeckToUsedCardDeck(playedGameId, cardId));
    }

    @Test
    public void testMoveCardFromCardDeckToPlayer() throws Exception {
        // Arrange
        int cardId = 20;
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.moveCardToPlayer(playedGameId, playerLogin, cardId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertTrue(playedGameUpdated.get().getCardDeck().stream().filter(card -> card.getId().equals(cardId)).findFirst().isEmpty());
        assertTrue(playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getCardsOnHand().stream().filter(itemCard -> itemCard.getId().equals(cardId)).findFirst().isPresent());
    }

    @Test
    public void testMoveCardFromCardDeckToPlayerTooManyCards() throws ServiceException {
        // Arrange
        int cardId = 20;
        Optional<Player> playerToAdd = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst();
        playerToAdd.get().getCardsOnHand().add(new ItemCard());
        playerToAdd.get().getCardsOnHand().add(new ItemCard());
        playerToAdd.get().getCardsOnHand().add(new ItemCard());
        playerToAdd.get().getCardsOnHand().add(new ItemCard());
        // Act and Assert
        assertThrows(ServiceException.class, () -> playedGameService.moveCardToPlayer(playedGameId, playerLogin, cardId));
    }

    @Test
    public void testMoveCardFromCardDeckToPlayerNotFound() throws Exception {
        // Arrange
        int cardId = 142124;
        // Act & Assert
        assertThrows(ServiceException.class, () -> playedGameService.moveCardToPlayer(playedGameId, playerLogin, cardId));
    }

    @Test
    public void testMoveCardFromCardDeckToTrophies() throws ServiceException {
        // Arrange
        int cardId = 2;
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.moveCardToPlayerTrophies(playedGameId, playerLogin, cardId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertTrue(playedGameUpdated.get().getCardDeck().stream().filter(card -> card.getId().equals(cardId)).findFirst().isEmpty());
        assertTrue(playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getTrophies().stream().filter(card -> card.getId().equals(cardId)).findFirst().isPresent());
    }

    @Test
    public void testMoveCardFromCardDeckToTrophiesUpdateTrophies() throws ServiceException {
        // Arrange
        int cardId = 2;
        Optional<Player> playerToAdd = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst();
        EnemyCard enemyCard = new EnemyCard();
        enemyCard.setId(12);
        playerToAdd.get().getTrophies().add(enemyCard);
        playerToAdd.get().getTrophies().add(enemyCard);
        playerToAdd.get().getTrophies().add(enemyCard);
        playerToAdd.get().getTrophies().add(enemyCard);
        int expectedStrength = playerToAdd.get().getStrength() + PlayedGameProperties.trophiesPointIncrease;
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.moveCardToPlayerTrophies(playedGameId, playerLogin, cardId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertTrue(playedGameUpdated.get().getCardDeck().stream().filter(card -> card.getId().equals(cardId)).findFirst().isEmpty());
        assertTrue(playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getTrophies().stream().filter(card -> card.getId().equals(cardId)).findFirst().isPresent());
        assertEquals(expectedStrength, playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getStrength());
        assertEquals(1, playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getTrophies().size());
    }

    @Test
    public void testMoveCardFromCardDeckToTrophiesNotFound() throws ServiceException {
        // Arrange
        int cardId = 142124;
        // Act & Assert
        assertThrows(ServiceException.class, () -> playedGameService.moveCardToPlayerTrophies(playedGameId, playerLogin, cardId));
    }

    @Test
    public void testMoveCardFromPlayerToUsedDeck() throws ServiceException {
        // Arrange
        int cardId = 30;
        Optional<Player> playerToAdd = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst();
        Card cardToMove = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getCardsOnHand().stream().filter(card -> card.getId().equals(cardId)).findFirst().get();
        playerToAdd.get().getCardsOnHand().add((ItemCard) cardToMove);
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.moveCardFromPlayerToUsedCardDeck(playedGameId, playerLogin, cardId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertTrue(playedGameUpdated.get().getUsedCardDeck().stream().filter(card -> card.getId().equals(cardId)).findFirst().isPresent());
        assertTrue(playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getTrophies().stream().filter(card -> card.getId().equals(cardId)).findFirst().isEmpty());
    }

    @Test
    public void testMoveCardFromPlayerToUsedDeckNotFound() throws ServiceException {
        // Arrange
        int cardId = 142124;
        // Act & Assert
        assertThrows(ServiceException.class, () -> playedGameService.moveCardFromPlayerToUsedCardDeck(playedGameId, playerLogin, cardId));
    }

    @Test
    public void testChangePosition() throws ServiceException {
        // Arrange
        int fieldId = 2;
        Optional<Player> playerToAdd = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst();
        Optional<Field> fieldToMove = playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(fieldId)).findFirst();
        // Act
        Optional<PlayedGame> playedGameUpdated = playedGameService.changePosition(playedGameId, playerLogin, fieldId);
        // Assert
        assertTrue(playedGameUpdated.isPresent());
        assertEquals(playedGameUpdated.get().getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst().get().getPositionField().getId(), fieldToMove.get().getId());
    }

    @Test
    public void testChangePositionFieldNotFound() throws ServiceException {
        // Arrange
        int fieldId = 231;
        // Act & Assert
        assertThrows(ServiceException.class, () -> playedGameService.changePosition(playedGameId, playerLogin, fieldId));
    }

    @Test
    public void testCheckPossibleNewPositions() throws ServiceException {
        // Arrange
        int roll = 3;
        Optional<Player> playerToCheck = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst();
        List<Field> fieldsToMoveTo = new ArrayList<>();
        fieldsToMoveTo.add(playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(4)).findFirst().get());
        fieldsToMoveTo.add(playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(14)).findFirst().get());
        // Act
        Optional<FieldList> fieldsToMoveToFound = playedGameService.checkPossibleNewPositions(playedGameId, playerLogin, roll);
        // Assert
        assertTrue(fieldsToMoveToFound.isPresent());
        assertEquals(fieldsToMoveTo.size(), fieldsToMoveToFound.get().getFieldList().size());
        for (int d = 0; d < fieldsToMoveTo.size(); d++) {
            assertEquals(fieldsToMoveTo.get(d), fieldsToMoveToFound.get().getFieldList().get(d));
        }
    }

    @Test
    public void testCheckPossibleNewPositionsBridgeFieldBlocked() throws ServiceException {
        // Arrange
        int roll = 3;
        Optional<Player> playerToCheck = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst();
        Optional<Field> fieldPos = playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(16)).findFirst();
        playerToCheck.get().setPositionField(fieldPos.get());
        List<Field> fieldsToMoveTo = new ArrayList<>();
        fieldsToMoveTo.add(playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(3)).findFirst().get());
        fieldsToMoveTo.add(playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(13)).findFirst().get());
        // Act
        Optional<FieldList> fieldsToMoveToFound = playedGameService.checkPossibleNewPositions(playedGameId, playerLogin, roll);
        // Assert
        assertTrue(fieldsToMoveToFound.isPresent());
        assertEquals(fieldsToMoveTo.size(), fieldsToMoveToFound.get().getFieldList().size());
        for (int d = 0; d < fieldsToMoveTo.size(); d++) {
            assertEquals(fieldsToMoveTo.get(d), fieldsToMoveToFound.get().getFieldList().get(d));
        }
    }

    @Test
    public void testCheckPossibleNewPositionsBridgeFieldOK() throws ServiceException {
        // Arrange
        int roll = 3;
        Optional<Player> playerToCheck = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst();
        Optional<Field> fieldPos = playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(16)).findFirst();
        playerToCheck.get().setPositionField(fieldPos.get());
        playerToCheck.get().setBridgeGuardianDefeated(true);
        List<Field> fieldsToMoveTo = new ArrayList<>();
        fieldsToMoveTo.add(playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(3)).findFirst().get());
        fieldsToMoveTo.add(playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(13)).findFirst().get());
        fieldsToMoveTo.add(playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(17)).findFirst().get());
        // Act
        Optional<FieldList> fieldsToMoveToFound = playedGameService.checkPossibleNewPositions(playedGameId, playerLogin, roll);
        // Assert
        assertTrue(fieldsToMoveToFound.isPresent());
        assertEquals(fieldsToMoveTo.size(), fieldsToMoveToFound.get().getFieldList().size());
        for (int d = 0; d < fieldsToMoveTo.size(); d++) {
            assertEquals(fieldsToMoveTo.get(d), fieldsToMoveToFound.get().getFieldList().get(d));
        }
    }

    @Test
    public void testCheckPossibleNewPositionsBossField() throws ServiceException {
        // Arrange
        int roll = 3;
        Optional<Player> playerToCheck = playedGame.getPlayers().stream().filter(player -> player.getLogin().equals(playerLogin)).findFirst();
        Optional<Field> fieldPos = playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(17)).findFirst();
        playerToCheck.get().setPositionField(fieldPos.get());
        List<Field> fieldsToMoveTo = new ArrayList<>();
        fieldsToMoveTo.add(playedGame.getBoard().getFieldsOnBoard().stream().filter(field -> field.getId().equals(16)).findFirst().get());
        // Act
        Optional<FieldList> fieldsToMoveToFound = playedGameService.checkPossibleNewPositions(playedGameId, playerLogin, roll);
        // Assert
        assertTrue(fieldsToMoveToFound.isPresent());
        assertEquals(fieldsToMoveTo.size(), fieldsToMoveToFound.get().getFieldList().size());
        for (int d = 0; d < fieldsToMoveTo.size(); d++) {
            assertEquals(fieldsToMoveTo.get(d), fieldsToMoveToFound.get().getFieldList().get(d));
        }
    }

}