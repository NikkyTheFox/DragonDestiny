package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.playedgame.PlayedGameProperties;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardType;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.DTO.ItemCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.DTO.ItemCardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.character.DTO.CharacterDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.character.DTO.CharacterListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.Character;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.CharacterList;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.*;
import pl.edu.pg.eti.dragondestiny.playedgame.fightresult.object.FightResult;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO.PlayedGameDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO.PlayedGameListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGame;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGameList;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.repository.PlayedGameRepository;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.PlayerList;
import pl.edu.pg.eti.dragondestiny.playedgame.player.service.PlayerService;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.IllegalGameStateException;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.Round;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.RoundState;

import java.util.*;
import java.util.stream.IntStream;

import static pl.edu.pg.eti.dragondestiny.playedgame.round.object.IllegalGameStateException.*;

/**
 * Played Game Service to manipulate played games' data retrieved from the database.
 */
@Service
public class PlayedGameService {

    /**
     * MongoDB repository communicating with database.
     */
    private final PlayedGameRepository playedGameRepository;

    /**
     * Player service to get players from database.
     */
    private final PlayerService playerService;

    /**
     * A constructor for PlayedGameService with PlayedGameRepository and PlayerService instances.
     *
     * @param playedGameRepository A repository for retrieval data from database.
     * @param playerService        A service to retrieve players' data.
     */
    @Autowired
    public PlayedGameService(PlayedGameRepository playedGameRepository, PlayerService playerService) {
        this.playedGameRepository = playedGameRepository;
        this.playerService = playerService;
    }

    /**
     * Returns played game by ID. If no game found, throws exception.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A retrieved played game.
     */
    public Optional<PlayedGame> findPlayedGame(String playedGameId) {

        return playedGameRepository.findById(playedGameId);
    }

    /**
     * Retrieves all played games.
     *
     * @return A list of all played games in the database.
     */
    public List<PlayedGame> findPlayedGames() {

        return playedGameRepository.findAll();
    }

    /**
     * Deletes played game from database by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A Boolean(True) as a confirmation of deletion.
     */
    public Boolean delete(String playedGameId) {
        Optional<PlayedGame> playedGame = playedGameRepository.findById(playedGameId);
        if (playedGame.isEmpty()) {
            return false;
        }
        playedGameRepository.deleteById(playedGameId);
        playerService.deleteGame(playedGameId);
        return true;
    }

    /**
     * Retrieves deck of cards.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing a list of cards.
     */

    public Optional<CardList> findCardDeck(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        List<Card> cardList = playedGameRepository.findCardDeck(playedGameId);
        if (cardList.isEmpty()) {
            return Optional.of(new CardList());
        }
        return Optional.of(new CardList(cardList));
    }

    /**
     * Retrieves deck of used cards.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing a list of cards.
     */
    public Optional<CardList> findUsedCardDeck(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        List<Card> cardList = playedGameRepository.findUsedCardDeck(playedGameId);
        if (cardList.isEmpty()) {
            return Optional.of(new CardList());
        }
        return Optional.of(new CardList(cardList));
    }

    /**
     * Retrieves card in the card deck by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param cardId       An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    public Optional<Card> findCardInCardDeck(String playedGameId, Integer cardId) {

        return playedGameRepository.findCardByIdInCardDeck(playedGameId, cardId).stream().findFirst();
    }

    /**
     * Retrieves card in used card deck by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param cardId       An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    public Optional<Card> findCardInUsedCardDeck(String playedGameId, Integer cardId) {

        return playedGameRepository.findCardByIdInUsedDeck(playedGameId, cardId).stream().findFirst();
    }

    /**
     * Find a given card in given player's hand by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose hand will be searched.
     * @param cardId       An identifier of a card to be retrieved in player's hand.
     * @return A retrieved card.
     */
    public Optional<ItemCard> findCardInPlayerHand(String playedGameId, String playerLogin, Integer cardId) {

        return playedGameRepository.findCardByIdInPlayerHand(playedGameId, playerLogin, cardId).stream().findFirst();
    }

    /**
     * Retrieves player by Login.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player to be retrieved.
     * @return A retrieved player.
     */
    public Optional<Player> findPlayer(String playedGameId, String playerLogin) {

        return playedGameRepository.findPlayerByLogin(playedGameId, playerLogin).stream().findFirst();
    }

    /**
     * Retrieves player's character by player's login.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose character is to be retrieved.
     * @return A retrieved player's character.
     */
    public Optional<Character> findPlayersCharacter(String playedGameId, String playerLogin) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if (game.isEmpty() || player.isEmpty()) {
            return Optional.empty();
        }
        if (player.get().getCharacter() == null) {
            throw new NoSuchElementException(PlayerHasNoCharacterAssignedMessage);
        }
        return player.map(Player::getCharacter);
    }

    /**
     * Retrieves all cards in player's hand by player login.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose hand cards are to be retrieved.
     * @return A structure containing a list of item cards.
     */
    public Optional<ItemCardList> findPlayersHandCards(String playedGameId, String playerLogin) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        List<Player> players = playedGameRepository.findPlayerByLogin(playedGameId, playerLogin);
        if (game.isEmpty() || players.isEmpty()) {
            return Optional.empty();
        }
        List<ItemCard> itemCardList = playedGameRepository.findCardsInPlayerHand(playedGameId, playerLogin);
        if (itemCardList.isEmpty()) {
            return Optional.of(new ItemCardList());
        }
        return Optional.of(new ItemCardList(itemCardList));
    }

    /**
     * Retrieves trophies (beaten enemy card) of a player by player's login.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose trophies are to be retrieved.
     * @return A structure containing a list of trophies (enemy cards).
     */
    public Optional<EnemyCardList> findPlayerTrophies(String playedGameId, String playerLogin) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        List<Player> players = playedGameRepository.findPlayerByLogin(playedGameId, playerLogin);
        if (game.isEmpty() || players.isEmpty()) {
            return Optional.empty();
        }
        List<EnemyCard> enemyCardList = playedGameRepository.findPlayersTrophies(playedGameId, playerLogin);
        if (enemyCardList.isEmpty()) {
            return Optional.of(new EnemyCardList());
        }
        return Optional.of(new EnemyCardList(enemyCardList));
    }

    /**
     * Retrieves all characters that can be used in the game.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing list of characters.
     */
    public Optional<CharacterList> findCharacters(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        List<Character> characterList = playedGameRepository.findCharacters(playedGameId);
        if (characterList.isEmpty()) {
            return Optional.of(new CharacterList());
        }
        return Optional.of(new CharacterList(characterList));
    }

    /**
     * Retrieves a given character by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param characterId  An identifier of a character to be retrieved.
     * @return A retrieved character.
     */
    public Optional<Character> findCharacter(String playedGameId, Integer characterId) {

        return playedGameRepository.findCharacterById(playedGameId, characterId).stream().findFirst();
    }

    /**
     * Retrieves ID of boss field from game properties.
     *
     * @param playedGameId
     * @return
     */
    public Optional<Integer> findBossField(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(PlayedGameProperties.bossFieldID);
    }

    /**
     * Retrieves ID of bridge field from game properties.
     *
     * @param playedGameId
     * @return
     */
    public Optional<Integer> findBridgeField(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(PlayedGameProperties.guardianFieldID);
    }

    /**
     * Retrieves characters that are already assigned to players.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing list of characters.
     */
    public Optional<CharacterList> findCharactersInUse(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        List<Player> playerList = playedGameRepository.findPlayers(playedGameId);
        if (playerList.isEmpty()) {
            return Optional.of(new CharacterList());
        }
        List<Character> characterList = new ArrayList<>();
        playerList.forEach(player -> {
            if (player.getCharacter() != null) {
                characterList.add(player.getCharacter());
            }
        });
        return Optional.of(new CharacterList(characterList));
    }

    /**
     * Retrieves a list of characters from which no character is assigned to a player.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing list of characters.
     */
    public Optional<CharacterList> findCharactersNotInUse(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        List<Character> characterList = playedGameRepository.findCharacters(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        if (characterList.isEmpty()) {
            throw new NoSuchElementException(CharactersNotFoundMessage);
        }
        List<Player> playerList = playedGameRepository.findPlayers(playedGameId);
        if (playerList.isEmpty()) {
            return Optional.of(new CharacterList(characterList));
        }
        List<Character> filteredList = characterList.stream()
                .filter(character -> playerList.stream()
                        .filter(player -> player.getCharacter() != null)
                        .noneMatch(player ->
                                player.getCharacter().getId().equals(character.getId()))
                ).toList();
        return Optional.of(new CharacterList(filteredList));
    }

    /**
     * Retrieves a list of enemies on a given field.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param fieldId      An identifier of a field which is to be checked.
     * @return A structure containing a list of enemy cards.
     */
    public Optional<EnemyCardList> findEnemyCardsOnField(String playedGameId, Integer fieldId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        List<Field> fields = playedGameRepository.findFieldOnBoard(playedGameId, fieldId);
        if (game.isEmpty() || fields.isEmpty()) {
            return Optional.empty();
        }
        List<EnemyCard> enemyCardList = playedGameRepository.findEnemyOnField(playedGameId, fieldId);
        if (enemyCardList.isEmpty()) {
            return Optional.of(new EnemyCardList());
        }
        return Optional.of(new EnemyCardList(enemyCardList));
    }

    /**
     * Retrieve a list of enemies on a player's position field. Sets first enemy as the one the player will fight.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose position field is to be checked.
     * @return A structure containing a list of enemy cards.
     */
    public Optional<EnemyCardList> findEnemyCardOnPlayersField(String playedGameId, String playerLogin) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_ENEMIES_TO_FIGHT);

        List<EnemyCard> enemyCardList = playedGameRepository.findEnemyOnField(playedGameId, player.getCharacter().getField().getId());
        if (enemyCardList.isEmpty()) {
            return Optional.of(new EnemyCardList());
        }

        activeRound.setEnemyFought(enemyCardList.get(0));
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        playedGameRepository.save(playedGame);
        return Optional.of(new EnemyCardList(new ArrayList<>(Collections.singletonList(enemyCardList.get(0)))));
    }

    /**
     * Retrieves field by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param fieldId      An identifier of a field to be retrieved.
     * @return A retrieved field.
     */
    public Optional<Field> findField(String playedGameId, Integer fieldId) {

        return playedGameRepository.findFieldOnBoard(playedGameId, fieldId).stream().findFirst();
    }

    /**
     * Retrieves board from played game.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A retrieved board.
     */
    public Optional<PlayedBoard> findBoard(String playedGameId) {

        return playedGameRepository.findBoard(playedGameId).stream().findFirst();
    }

    /**
     * Retrieves current round from played game.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return Currently ongoing round.
     */
    public Optional<Round> findActiveRound(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        if (game.get().getActiveRound() == null) {
            throw new NoSuchElementException(GameNotStartedMessage);
        }
        return playedGameRepository.findActiveRound(playedGameId).stream().findFirst();
    }

    /**
     * Retrieves all fields that are on board in a given game.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing a list of fields.
     */
    public Optional<FieldList> findFields(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        List<Field> fieldList = playedGameRepository.findFieldsOnBoard(playedGameId);
        if (fieldList.isEmpty()) {
            return Optional.of(new FieldList());
        }
        return Optional.of(new FieldList(fieldList));
    }

    /**
     * Retrieve all players from a game given by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing a list of players.
     */
    public Optional<PlayerList> findPlayers(String playedGameId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        List<Player> playerList = playedGameRepository.findPlayers(playedGameId);
        if (playerList.isEmpty()) {
            return Optional.of(new PlayerList());
        }
        return Optional.of(new PlayerList(playerList));
    }

    /**
     * Retrieves all players on a field given by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param fieldId      An identifier of a field to be checked.
     * @return A structure containing a list of players.
     */
    public Optional<PlayerList> findPlayersByField(String playedGameId, Integer fieldId) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        if (game.isEmpty()) {
            return Optional.empty();
        }
        List<Player> playerList = playedGameRepository.findPlayersByField(playedGameId, fieldId);
        if (playerList.isEmpty()) {
            return Optional.of(new PlayerList());
        }
        return Optional.of(new PlayerList(playerList));
    }

    /**
     * Retrieves ot
     * her players from player's position field.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose position field is to be checked.
     * @return A structure containing a list of players.
     */
    public Optional<PlayerList> findDifferentPlayersByField(String playedGameId, String playerLogin) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_PLAYER_TO_FIGHT);

        List<Player> playerList = playedGameRepository.findDifferentPlayersByField(playedGameId, playerLogin, player.getCharacter().getField().getId());
        if (playerList.isEmpty()) {
            return Optional.of(new PlayerList());
        }

        activeRound.setEnemyPlayerFought(playerList.get(0));
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        playedGameRepository.save(playedGame);
        return Optional.of(new PlayerList(new ArrayList<>(Collections.singletonList(playerList.get(0)))));
    }

    /**
     * Retrieves all items that increase health statistics from player's hand.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose hand cards are to be filtered.
     * @return A structure containing a list of item cards.
     */
    public Optional<ItemCardList> findHealthCardsInPlayer(String playedGameId, String playerLogin) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if (game.isEmpty() || player.isEmpty()) {
            return Optional.empty();
        }
        List<ItemCard> itemCardList = playedGameRepository.findHealthCardsInPlayerHand(playedGameId, playerLogin);
        if (itemCardList.isEmpty()) {
            return Optional.of(new ItemCardList());
        }
        return Optional.of(new ItemCardList(itemCardList));
    }

    /**
     * Retrieves all items that increase strength statistics from player's hand.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose hand cards are to be filtered.
     * @return A structure containing a list of item cards.
     */
    public Optional<ItemCardList> findStrengthCardsInPlayer(String playedGameId, String playerLogin) {
        Optional<PlayedGame> game = playedGameRepository.findById(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if (game.isEmpty() || player.isEmpty()) {
            return Optional.empty();
        }
        List<ItemCard> itemCardList = playedGameRepository.findStrengthCardsInPlayerHand(playedGameId, playerLogin);
        if (itemCardList.isEmpty()) {
            return Optional.of(new ItemCardList());
        }
        return Optional.of(new ItemCardList(itemCardList));
    }

    /**
     * Stars initialized PlayedGame. Starts a first round and selects a random player order.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param startBoolean A boolean to set if game is to be started
     * @return An updated game.
     */
    public Optional<PlayedGame> startGame(String playedGameId, boolean startBoolean) throws IllegalGameStateException {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        if (!startBoolean || playedGame.isEmpty()) {
            return Optional.empty();
        }
        PlayedGame game = playedGame.get();
        if (game.getIsStarted()) {
            throw new IllegalGameStateException(GameStartedMessage);
        }
        Round round = new Round();
        round.setId(1);
        List<Player> players = game.getPlayers();
        if (players.isEmpty()) {
            throw new IllegalGameStateException(GameCannotBeStartedPlayersMessage);
        }
        for (Player player : players) {
            if (player.getCharacter() == null) {
                throw new IllegalGameStateException(GameCannotBeStartedCharactersMessage);
            }
        }

        Collections.shuffle(players);
        round.setPlayerList(players);
        Player startingPlayer = players.get(0);
        round.setActivePlayer(startingPlayer);
        round.initiateRoundStates();
        game.startGame(round);
        return Optional.of(playedGameRepository.save(game));
    }

    /**
     * Sets next player from list as active player (if player is not blocked).
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return An updated game.
     */
    public Optional<PlayedGame> nextRound(String playedGameId, String playerLogin) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_NEXT_ROUND);
        Player activePlayer = activeRound.getActivePlayer();

        Round nextRound = new Round();
        nextRound.setId(activeRound.getId() + 1);
        nextRound.setPlayerList(activeRound.getPlayerList());

        playedGame.getRounds().add(playedGame.getActiveRound());

        Optional<Player> optionalPlayer = nextRound.getPlayerList().stream().filter(player ->
                player.getLogin().equals(activePlayer.getLogin())
        ).findFirst();
        if (optionalPlayer.isEmpty()) {
            return Optional.empty();
        }
        int id = nextRound.getPlayerList().indexOf(optionalPlayer.get());
        Player nextPlayer = null;
        boolean found = false;
        int lookedId = id;
        while (!found) {
            if (lookedId + 1 < nextRound.getPlayerList().size()) {
                lookedId += 1;
            } else {
                lookedId = 0;
            }
            nextPlayer = nextRound.getPlayerList().get(lookedId);
            if (nextPlayer.getBlockedTurns() > 0) {
                nextPlayer.setBlockedTurns(nextPlayer.getBlockedTurns() - 1);
                updatePlayer(playedGame, nextPlayer);
                nextRound.setId(nextRound.getId() + 1);
            } else {
                found = true;
            }
        }
        nextRound.setActivePlayer(nextPlayer);
        if (nextPlayer.getPositionField().getType().equals(FieldType.BOSS_FIELD)) {
            nextRound.initiateRoundStatesBossField();
        } else {
            nextRound.initiateRoundStates();
        }
        playedGame.setActiveRound(nextRound);
        return Optional.of(playedGameRepository.save(playedGame));
    }

    /**
     * Assigns round option chosen by player to active round. Sets next steps in game.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of player.
     * @param fieldOption  Chosen option of action.
     * @return Updated game.
     */
    public Optional<PlayedGame> selectRoundOption(String playedGameId, String playerLogin, FieldOption fieldOption) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_FIELD_ACTION_CHOICE);

        if (!activeRound.getFieldOptionList().getPossibleOptions().contains(fieldOption)) {
            throw new IllegalGameStateException(PlayerActionNotAllowedMessage);
        }
        switch (fieldOption) {
            case TAKE_ONE_CARD, TAKE_TWO_CARDS -> activeRound.addRoundState(RoundState.WAITING_FOR_CARD_DRAWN);
            case FIGHT_WITH_ENEMY_ON_FIELD, BOSS_FIELD, BRIDGE_FIELD -> {
                activeRound.addRoundState(RoundState.WAITING_FOR_ENEMIES_TO_FIGHT);
                activeRound.addRoundState(RoundState.WAITING_FOR_FIGHT_ROLL);
                activeRound.addRoundState(RoundState.WAITING_FOR_ENEMY_ROLL);
                activeRound.addRoundState(RoundState.WAITING_FOR_FIGHT_RESULT);
            }
            case FIGHT_WITH_PLAYER -> {
                activeRound.addRoundState(RoundState.WAITING_FOR_PLAYER_TO_FIGHT);
                activeRound.addRoundState(RoundState.WAITING_FOR_FIGHT_ROLL);
                activeRound.addRoundState(RoundState.WAITING_FOR_ENEMY_PLAYER_ROLL);
                activeRound.addRoundState(RoundState.WAITING_FOR_FIGHT_RESULT);
            }
            case LOSE_ONE_ROUND, LOSE_TWO_ROUNDS -> {
                activeRound.addRoundState(RoundState.WAITING_FOR_BLOCK);
                activeRound.addRoundState(RoundState.WAITING_FOR_NEXT_ROUND);
            }
        }
        activeRound.setPlayerFieldOptionChosen(fieldOption);
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);

        return Optional.of(playedGameRepository.save(playedGame));
    }


    /**
     * Adds new player to player list.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player to be added to the game
     * @return An updated game.
     */
    public Optional<PlayedGame> addPlayer(String playedGameId, String playerLogin) throws IllegalGameStateException {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = playerService.findByLogin(playerLogin);
        if (playedGame.isEmpty() || player.isEmpty()) {
            return Optional.empty();
        }
        if (playedGame.get().getPlayers().stream().filter(p -> p.getLogin().equals(playerLogin)).findAny().isPresent()) {
            throw new IllegalGameStateException(PlayerAlreadyAddedMessage);
        }
        playedGame.get().addPlayerToGame(player.get());
        playerService.addGame(playerLogin, playedGameId);
        return Optional.of(playedGameRepository.save(playedGame.get()));
    }

    /**
     * Sets specified player's character to a character given by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player which has a character to be set.
     * @param characterId  An identifier of character to be assigned to the player.
     * @return An updated game.
     */
    public Optional<PlayedGame> assignCharacterToPlayer(String playedGameId, String playerLogin, Integer characterId) throws IllegalGameStateException {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        Optional<Character> character = findCharacter(playedGameId, characterId);
        if (playedGame.isEmpty() || player.isEmpty()) {
            return Optional.empty();
        }
        if (character.isEmpty()) {
            throw new NoSuchElementException(CharacterNotFoundMessage);
        }
        Optional<Field> field = findField(playedGameId, character.get().getField().getId());
        if (field.isEmpty()) {
            throw new NoSuchElementException(CharacterHasNoFieldAssignedMessage);
        }
        for (Player p : playedGame.get().getPlayers()) {
            if (p.getCharacter() != null && p.getCharacter().getId().equals(characterId)) {
                throw new IllegalGameStateException(CharacterChosenMessage);
            }
        }
        player.get().setCharacter(character.get());
        updatePlayer(playedGame.get(), player.get());
        playedGameRepository.save(playedGame.get());
        return changePosition(playedGameId, playerLogin, field.get().getId());
    }

    /**
     * Removes card from Card Deck and adds to Used Card Deck.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param cardId       An identifier of a card to be moved.
     * @return An updated game.
     */

    public Optional<PlayedGame> moveCardFromCardDeckToUsedCardDeck(String playedGameId, Integer cardId) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Round activeRound = playedGame.getActiveRound();
        if (activeRound.getRoundState().equals(RoundState.WAITING_FOR_CARD_TO_USED)) {
            throw new IllegalGameStateException(PlayerWrongActionMessage);
        }
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        Optional<Card> card = findCardInCardDeck(playedGameId, cardId);
        if (card.isEmpty()) {
            throw new NoSuchElementException(CardNotFoundMessage);
        }
        playedGame.addCardToUsedDeck(card.get());
        playedGame.removeCardFromDeck(card.get());
        return Optional.of(playedGameRepository.save(playedGame));
    }

    /**
     * Removes card from Card Deck and adds to Player's cards on hand.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player to whose hand a card is to be moved.
     * @param cardId       An identifier of a card that is to be moved to a player hand.
     * @return An updated game.
     */
    public Optional<PlayedGame> moveCardToPlayer(String playedGameId, String playerLogin, Integer cardId) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_CARD_TO_HAND);

        Optional<Card> card = findCardInCardDeck(playedGameId, cardId);
        if (card.isEmpty()) {
            throw new NoSuchElementException(CardNotFoundMessage);
        }
        if (!card.get().getCardType().equals(CardType.ITEM_CARD)) {
            throw new IllegalGameStateException(ItemInvalidTypeMessage);
        }
        if (activeRound.getItemCardToTake() == null || !activeRound.getItemCardToTake().getId().equals(cardId)) {
            throw new IllegalGameStateException(PlayerDidNotDrawMessage);
        }
        if (!player.checkCardsOnHand()) {
            activeRound.setRoundState(RoundState.WAITING_FOR_CARD_TO_USED);
            playedGame.setActiveRound(activeRound);
            playedGameRepository.save(playedGame);
            throw new IllegalGameStateException(PlayerHasNoPlaceOnHandMessage);
        }
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        player.moveCardToPlayer(card.get());
        playedGame.removeCardFromDeck(card.get());
        updatePlayer(playedGame, player);
        return Optional.of(playedGameRepository.save(playedGame));
    }

    /**
     * Removes card from Card Deck and adds to Player's trophies.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player who has earned a trophy.
     * @param cardId       An identifier of a card to be moved to player's trophy list.
     * @return An updated game.
     */
    public Optional<PlayedGame> moveCardToPlayerTrophies(String playedGameId, String playerLogin, Integer cardId) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_CARD_TO_TROPHIES);

        Optional<Card> card = findCardInCardDeck(playedGameId, cardId);
        if (card.isEmpty()) {
            throw new NoSuchElementException(CardNotFoundMessage);
        }
        if (!card.get().getCardType().equals(CardType.ENEMY_CARD)) {
            throw new IllegalGameStateException(EnemyInvalidTypeMessage);
        }
        if (!activeRound.getEnemyFought().getId().equals(cardId)) {
            throw new IllegalGameStateException(PlayerWrongEnemyTrophyMessage);
        }

        player.moveCardToTrophies(card.get());
        playedGame.removeCardFromDeck(card.get());
        checkTrophies(playedGame, player);
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        updatePlayer(playedGame, player);
        return Optional.of(playedGameRepository.save(playedGame));
    }

    /**
     * Removes card from Players' hand and adds to Used Card Deck.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose card is meant to be moved to used card deck.
     * @param cardId       An identifier of a card to be moved.
     * @return An updated game.
     */
    public Optional<PlayedGame> moveCardFromPlayerToUsedCardDeck(String playedGameId, String playerLogin, Integer cardId) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        if (!playedGame.getIsStarted()) {
            throw new IllegalGameStateException(GameNotStartedMessage);
        }
        Round activeRound = playedGame.getActiveRound();
        if (!activeRound.getRoundState().equals(RoundState.WAITING_FOR_CARD_TO_USED)) {
            throw new IllegalGameStateException(PlayerWrongActionMessage);
        }
        Optional<ItemCard> itemCard = findCardInPlayerHand(playedGameId, playerLogin, cardId);
        if (itemCard.isEmpty()) {
            throw new NoSuchElementException(CardNotFoundMessage);
        }
        Card card = itemCard.get();

        if (!activeRound.getActivePlayer().getLogin().equals(playerLogin)) {
            if (!activeRound.getEnemyPlayerFought().getLogin().equals(playerLogin)) {
                throw new IllegalGameStateException(PlayerIsNotActiveMessage);
            } else {
                player.removeCardFromPlayer(card);
                playedGame.addCardToUsedDeck(card);
                updatePlayer(playedGame, player);
                activeRound.nextRoundState();
            }
        } else {
            player.removeCardFromPlayer(card);
            playedGame.addCardToUsedDeck(card);
            updatePlayer(playedGame, player);
            activeRound.setRoundState(RoundState.WAITING_FOR_CARD_TO_HAND);
        }
        playedGame.setActiveRound(activeRound);
        return Optional.of(playedGameRepository.save(playedGame));
    }

    /**
     * Move card from one player's hand and add to another player's hand.
     *
     * @param playedGameId    An identifier of a played game to perform actions on.
     * @param playerFromLogin An identifier of a player to take card from.
     * @param playerToLogin   An identifier of a player to add card to.
     * @param cardId          An identifier of card to move.
     * @return An updated game.
     */
    public Optional<PlayedGame> moveCardFromPlayerToPlayer(String playedGameId, String playerFromLogin, String playerToLogin, Integer cardId) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player playerFrom = checkCompletePlayer(playedGameId, playerFromLogin);
        Player playerTo = checkCompletePlayer(playedGameId, playerToLogin);
        if (!playedGame.getIsStarted()) {
            throw new IllegalGameStateException(GameNotStartedMessage);
        }
        Round activeRound = playedGame.getActiveRound();
        if (!activeRound.getRoundState().equals(RoundState.WAITING_FOR_CARD_THEFT)) {
            throw new IllegalGameStateException(PlayerWrongActionMessage);
        }

        Optional<ItemCard> itemCard = findCardInPlayerHand(playedGameId, playerFromLogin, cardId);
        if (itemCard.isEmpty()) {
            throw new NoSuchElementException(CardNotFoundMessage);
        }
        Card card = itemCard.get();

        playerFrom.removeCardFromPlayer(card);
        playerTo.moveCardToPlayer(card);
        updatePlayer(playedGame, playerFrom);
        updatePlayer(playedGame, playerTo);
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        return Optional.of(playedGameRepository.save(playedGame));
    }

    /**
     * Change Player's character position Field.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose position is to be changed.
     * @param fieldId      An identifier of a new player's position field.
     * @return An updated game.
     */
    public Optional<PlayedGame> changePosition(String playedGameId, String playerLogin, Integer fieldId) throws IllegalGameStateException {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        Optional<Field> field = findField(playedGameId, fieldId);
        if (playedGame.isEmpty() || player.isEmpty()) {
            return Optional.empty();
        }
        if (player.get().getCharacter() == null) {
            throw new NoSuchElementException(PlayerHasNoCharacterAssignedMessage);
        }
        if (field.isEmpty()) {
            throw new NoSuchElementException(FieldNotFoundMessage);
        }
        if (playedGame.get().getIsStarted()) {
            Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_MOVE);

            if (activeRound.getFieldListToMove().stream().filter(field1 -> field1.getId().equals(fieldId)).findFirst().isEmpty()) {
                throw new IllegalGameStateException(PlayerCannotMoveToGivenFieldMessage);
            }
            activeRound.nextRoundState();
            playedGame.get().setActiveRound(activeRound);
        }

        player.get().setPositionField(field.get());
        updatePlayer(playedGame.get(), player.get());
        return Optional.of(playedGameRepository.save(playedGame.get()));
    }

    /**
     * Retrieves fields a player can move to after a die roll.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player that has rolled a die.
     * @return A structure containing a list of fields.
     */
    public Optional<FieldList> checkPossibleNewPositions(String playedGameId, String playerLogin) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_FIELDS_TO_MOVE);

        if (activeRound.getPlayerMoveRoll() == null) {
            throw new IllegalGameStateException(PlayerWrongActionMessage);
        }
        Integer rollValue = activeRound.getPlayerMoveRoll();
        Field currentPlayersField = player.getPositionField();
        List<Field> tempFieldList = new ArrayList<>();
        List<Field> fieldList = playedGameRepository.findFieldsOnBoard(playedGameId);
        int boardSize = fieldList.size() - 1; // -1 because of boss field

        // check forward:
        Integer firstOptionId = (currentPlayersField.getId() + rollValue - 1) % boardSize + 1;
        Optional<Field> firstOption = this.findField(playedGame.getId(), firstOptionId);
        firstOption.ifPresent(tempFieldList::add);

        // check backward:
        int secondOptionId = currentPlayersField.getId() - rollValue;
        if (secondOptionId <= 0) {
            secondOptionId += boardSize;
        }
        Optional<Field> secondOption = this.findField(playedGame.getId(), secondOptionId);
        secondOption.ifPresent(tempFieldList::add);

        // check whether a player won a fight with Bridge Guardian and can go for boss
        if (currentPlayersField.getType() == FieldType.BRIDGE_FIELD && player.getBridgeGuardianDefeated()) {
            Optional<Field> thirdOption = this.findField(playedGame.getId(), PlayedGameProperties.bossFieldID); // boss field id
            thirdOption.ifPresent(tempFieldList::add);
        }

        // check whether a player can move back from Boss to Bridge Guardian
        if (currentPlayersField.getType() == FieldType.BOSS_FIELD) {
            Optional<Field> thirdOption = this.findField(playedGame.getId(), PlayedGameProperties.guardianFieldID); // boss field id
            tempFieldList = new ArrayList<>();
            thirdOption.ifPresent(tempFieldList::add);
        }
        activeRound.nextRoundState();
        activeRound.setFieldListToMove(tempFieldList);
        playedGame.setActiveRound(activeRound);
        playedGameRepository.save(playedGame);
        return Optional.of(new FieldList(tempFieldList));
    }

    /**
     * Checks type of field the player stands on, returns list of possible options on that field.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player whose position field is to be checked.
     * @return A structure containing list of possible options.
     */
    public Optional<FieldOptionList> checkFieldOption(String playedGameId, String playerLogin) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_FIELD_OPTIONS);

        Field field = player.getCharacter().getField();

        FieldOptionList list = new FieldOptionList();
        List<Player> enemyPlayerList = playedGameRepository.findDifferentPlayersByField(playedGameId, playerLogin, field.getId());
        list.getPossibleOptions().add(FieldOption.valueOf(field.getType().toString()));

        if (field.getType() == FieldType.BOSS_FIELD && !enemyPlayerList.isEmpty()) {
            list.getPossibleOptions().remove(0);
        }
        if (field.getType() == FieldType.BOSS_FIELD) {
            list.getPossibleOptions().add(FieldOption.BRIDGE_FIELD);
        }
        if (field.getType() == FieldType.BRIDGE_FIELD && player.getBridgeGuardianDefeated()) {
            list.getPossibleOptions().add(FieldOption.BOSS_FIELD);
        }
        if (!enemyPlayerList.isEmpty()) {
            list.getPossibleOptions().add(FieldOption.FIGHT_WITH_PLAYER);
        }
        Optional<EnemyCardList> enemyCardList = findEnemyCardsOnField(playedGameId, field.getId());
        if (enemyCardList.isPresent() && !enemyCardList.get().getEnemyCardList().isEmpty() && field.getType() != FieldType.BOSS_FIELD && field.getType() != FieldType.BRIDGE_FIELD) {
            list.getPossibleOptions().add(FieldOption.FIGHT_WITH_ENEMY_ON_FIELD);
        }
        activeRound.nextRoundState();
        activeRound.setFieldOptionList(list);
        playedGame.setActiveRound(activeRound);
        playedGameRepository.save(playedGame);

        return Optional.of(list);
    }

    /**
     * Returns a random card from card deck.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A randomly drawn card.
     */
    public Optional<Card> drawCard(String playedGameId, String playerLogin) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_CARD_DRAWN);

        Random random = new Random();
        List<Card> cardList = playedGameRepository.findCardDeck(playedGameId);
        if (cardList.isEmpty()) {
            if (playedGame.getUsedCardDeck().isEmpty()) {
                throw new NoSuchElementException("There are no cards in the card deck.");
            }
            Collections.shuffle(playedGame.getUsedCardDeck());
            cardList.addAll(playedGame.getUsedCardDeck());
            playedGame.getUsedCardDeck().clear();
        }
        int cardToDrawIndex = random.nextInt(cardList.size() - 1);
        Optional<Card> cardToDraw = playedGameRepository.findCardByIndexInCardDeck(playedGameId, cardToDrawIndex).stream().findFirst();
        activeRound.increaseNumOfCardsTaken(1);
        if (cardToDraw.get().getCardType().equals(CardType.ENEMY_CARD)) {
            activeRound.setEnemyFought((EnemyCard) cardToDraw.get());
            activeRound.addRoundState(RoundState.WAITING_FOR_FIGHT_ROLL);
            activeRound.addRoundState(RoundState.WAITING_FOR_ENEMY_ROLL);
            activeRound.addRoundState(RoundState.WAITING_FOR_FIGHT_RESULT);
        } else {
            activeRound.setItemCardToTake((ItemCard) cardToDraw.get());
            activeRound.addRoundState(RoundState.WAITING_FOR_CARD_TO_HAND);
        }
        if (activeRound.getPlayerNumberOfCardsTaken() >= activeRound.getPlayerFieldOptionChosen().numOfCardsToTake) {
            activeRound.addRoundState(RoundState.WAITING_FOR_NEXT_ROUND);
        } else {
            activeRound.addRoundState(RoundState.WAITING_FOR_CARD_DRAWN);
        }
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        playedGameRepository.save(playedGame);
        return cardToDraw;
    }

    /**
     * Calculates fight result between Player and Enemy from card.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player that participates in a fight.
     * @param enemyCardId  An identifier of en enemy that participates in a fight.
     * @return A result of a fight.
     */
    public Optional<FightResult> calculateFightWithEnemyCard(String playedGameId, String playerLogin, Integer enemyCardId) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_FIGHT_RESULT);
        Optional<Card> card = findCardInCardDeck(playedGameId, enemyCardId);
        Boolean fieldEnemy = false;

        if (activeRound.getEnemyFought() == null || !activeRound.getEnemyFought().getId().equals(enemyCardId)) {
            throw new IllegalGameStateException(PlayerWrongEnemyMessage);
        }
        if (activeRound.getEnemyFightRoll() == null || activeRound.getPlayerFightRoll() == null) {
            throw new IllegalGameStateException(PlayerWrongActionMessage);
        }
        Integer playerRollValue = activeRound.getPlayerFightRoll();
        Integer enemyRollValue = activeRound.getEnemyFightRoll();

        if (card.isEmpty()) {
            if (player.getCharacter().getField().getEnemy() != null && player.getCharacter().getField().getEnemy().getId().equals(enemyCardId)) {
                card = Optional.of(player.getCharacter().getField().getEnemy());
                fieldEnemy = true;
            } else {
                throw new NoSuchElementException(CardNotFoundMessage);
            }
        }
        if (!card.get().getCardType().equals(CardType.ENEMY_CARD)) {
            throw new IllegalGameStateException(EnemyInvalidTypeMessage);
        }

        EnemyCard enemyCard = (EnemyCard) card.get();
        FightResult fightResult = new FightResult();
        int playerResult = player.getStrength() + playerRollValue;
        int enemyResult = enemyCard.getInitialStrength() + enemyRollValue;
        if (playerResult >= enemyResult) { // player won
            if (!fieldEnemy) {
                activeRound.setRoundState(RoundState.WAITING_FOR_CARD_TO_TROPHIES);
                playedGame.setActiveRound(activeRound);
                playedGameRepository.save(playedGame);
            }
            playedGame = decreaseHealth(playedGame, player, enemyCard, 1, fightResult);
            fightResult.setAttackerWon(true);
        } else { // player lost
            playedGame = decreaseHealth(playedGame, player, 1, fightResult);
            fightResult.setAttackerWon(false);
            if (!player.isAlive()) {
                fightResult.setPlayerDead(true);
                for (Player p : playedGame.getPlayers()) {
                    if (checkIfPlayerLastStanding(playedGame, p)) {
                        fightResult.setGameWon(true);
                        fightResult.setWonPlayer(p.getLogin());
                    }
                }
            }
        }
        activeRound.addRoundState(RoundState.WAITING_FOR_NEXT_ROUND);
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        playedGameRepository.save(playedGame);
        return Optional.of(fightResult);
    }

    /**
     * Calculates fight result between two players. Player is treated as a defender and Enemy is treated as an attacker.
     *
     * @param playedGameId     An identifier of a played game to perform actions on.
     * @param playerLogin      An identifier of a player (attacker) that participates in a fight.
     * @param enemyPlayerLogin An identifier of a player (victim) that participates in a fight.
     * @return A result of a fight.
     */
    public Optional<FightResult> calculateFightWithPlayer(String playedGameId, String playerLogin, String enemyPlayerLogin) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        Player enemyPlayer = checkCompletePlayer(playedGameId, enemyPlayerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_FIGHT_RESULT);

        if (activeRound.getEnemyPlayerFought() == null || !activeRound.getEnemyPlayerFought().getLogin().equals(enemyPlayerLogin)) {
            throw new IllegalGameStateException(PlayerWrongEnemyMessage);
        }
        if (activeRound.getPlayerFightRoll() == null || activeRound.getEnemyFightRoll() == null) {
            throw new IllegalGameStateException(PlayerWrongActionMessage);
        }

        Integer playerRollValue = activeRound.getPlayerFightRoll();
        Integer enemyPlayerRollValue = activeRound.getEnemyFightRoll();

        FightResult fightResult = new FightResult();
        int playerResult = player.getStrength() + playerRollValue;
        int enemyResult = enemyPlayer.getStrength() + enemyPlayerRollValue;
        if (playerResult >= enemyResult) { // ATTACKER WON
            fightResult.setAttackerWon(true);
            fightResult.setWonPlayer(player.getLogin());
            fightResult.setLostPlayer(enemyPlayer.getLogin());
            List<ItemCard> loserHealthCards = playedGameRepository.findHealthCardsInPlayerHand(playedGameId, enemyPlayerLogin);
            if (loserHealthCards.isEmpty()) { // no health cards
                decreaseHealth(playedGame, enemyPlayer, 1, fightResult);
                if (!enemyPlayer.isAlive()) {
                    fightResult.setEnemyKilled(true);
                    if (checkIfPlayerLastStanding(playedGame, player)) {
                        fightResult.setGameWon(true);
                    }
                }
            } else {
                if (player.getCardsOnHand().size() >= PlayedGameProperties.numberOfCardsOnHand) {
                    activeRound.addRoundState(RoundState.WAITING_FOR_CARD_TO_USED);
                }
                activeRound.addRoundState(RoundState.WAITING_FOR_CARD_THEFT);
                fightResult.setChooseCardFromEnemyPlayer(true);
            }
        } else { // VICTIM WON
            fightResult.setAttackerWon(false);
            fightResult.setWonPlayer(enemyPlayer.getLogin());
            fightResult.setLostPlayer(player.getLogin());
            List<ItemCard> loserHealthCards = playedGameRepository.findHealthCardsInPlayerHand(playedGameId, playerLogin);
            if (loserHealthCards.isEmpty()) { // no health cards
                decreaseHealth(playedGame, player, 1, fightResult);
                if (!player.isAlive()) {
                    fightResult.setPlayerDead(true);
                    if (checkIfPlayerLastStanding(playedGame, enemyPlayer)) {
                        fightResult.setGameWon(true);
                    }
                }
            } else {
                if (player.getCardsOnHand().size() >= PlayedGameProperties.numberOfCardsOnHand) {
                    activeRound.addRoundState(RoundState.WAITING_FOR_CARD_TO_USED);
                }
                activeRound.addRoundState(RoundState.WAITING_FOR_CARD_THEFT);
                fightResult.setChooseCardFromEnemyPlayer(true);
            }
        }
        activeRound.addRoundState(RoundState.WAITING_FOR_NEXT_ROUND);
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        playedGameRepository.save(playedGame);
        return Optional.of(fightResult);
    }

    /**
     * Simulates die roll.
     *
     * @param playedGameId The ID of a played game to perform actions on.
     * @param playerLogin  The ID of a player performing a die roll.
     * @return Random number.
     */
    public Optional<Integer> rollDice(String playedGameId, String playerLogin) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        checkCompletePlayer(playedGameId, playerLogin);
        Random random = new Random();
        Integer value = random.nextInt(PlayedGameProperties.diceLowerBound, PlayedGameProperties.diceUpperBound + 1);

        Round activeRound = playedGame.getActiveRound();
        if (!activeRound.getActivePlayer().getLogin().equals(playerLogin)) {
            if (activeRound.getEnemyPlayerFought() == null || !activeRound.getEnemyPlayerFought().getLogin().equals(playerLogin)) {
                throw new IllegalGameStateException(PlayerIsNotActiveMessage);
            }
        }

        if (activeRound.getEnemyPlayerFought() != null && activeRound.getEnemyPlayerFought().getLogin().equals(playerLogin)) {
            if (activeRound.getRoundState() == RoundState.WAITING_FOR_ENEMY_PLAYER_ROLL) {
                activeRound.setEnemyFightRoll(value);
            } else {
                throw new IllegalGameStateException(PlayerWrongActionMessage);
            }
        } else if (activeRound.getRoundState() == RoundState.WAITING_FOR_MOVE_ROLL) {
            activeRound.setPlayerMoveRoll(value);
        } else if (activeRound.getRoundState() == RoundState.WAITING_FOR_FIGHT_ROLL) {
            activeRound.setPlayerFightRoll(value);
        } else if (activeRound.getRoundState() == RoundState.WAITING_FOR_ENEMY_ROLL) {
            activeRound.setEnemyFightRoll(value);
        } else {
            throw new IllegalGameStateException(PlayerWrongActionMessage);
        }
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        playedGameRepository.save(playedGame);
        return Optional.of(value);
    }

    /**
     * Blocks player for a given number of turns.
     *
     * @param playedGameId      An identifier of a played game to perform actions on.
     * @param playerLogin       An identifier of a player to be blocked.
     * @param numOfTurnsToBlock A number of turns to be blocked.
     * @return An updated player.
     */
    public Optional<Player> blockTurnsOfPlayer(String playedGameId, String playerLogin, Integer numOfTurnsToBlock) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);

        if (numOfTurnsToBlock <= 0) {
            throw new IllegalGameStateException(PlayerCannotBeBlockedForNegativeTurnsMessage);
        }
        player.setBlockedTurns(player.getBlockedTurns() + numOfTurnsToBlock);
        updatePlayer(playedGame, player);
        playedGameRepository.save(playedGame);
        return Optional.of(player);
    }

    /**
     * Blocks a player for number of turns dependent on field type.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin  An identifier of a player to be blocked.
     * @return An updated player.
     */
    public Optional<Player> automaticallyBlockTurnsOfPlayer(String playedGameId, String playerLogin) throws IllegalGameStateException {
        PlayedGame playedGame = checkGame(playedGameId);
        Player player = checkCompletePlayer(playedGameId, playerLogin);
        Round activeRound = checkActiveRound(playedGameId, playerLogin, RoundState.WAITING_FOR_BLOCK);

        Field field = player.getCharacter().getField();
        int numOfTurnsToBlock = 0;
        if (field.getType() == FieldType.LOSE_ONE_ROUND) {
            numOfTurnsToBlock = 1;
        } else if (field.getType() == FieldType.LOSE_TWO_ROUNDS) {
            numOfTurnsToBlock = 2;
        } else {
            throw new IllegalGameStateException(PlayerCannotBeBlockedMessage);
        }
        activeRound.nextRoundState();
        playedGame.setActiveRound(activeRound);
        playedGameRepository.save(playedGame);
        return blockTurnsOfPlayer(playedGameId, playerLogin, numOfTurnsToBlock);
    }

    private PlayedGame checkGame(String playedGameId) throws IllegalGameStateException {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        if (playedGame.isEmpty()) {
            throw new NoSuchElementException(GameNotFoundMessage);
        }
        if (!playedGame.get().getIsStarted()) {
            throw new IllegalGameStateException(GameNotStartedMessage);
        }
        return playedGame.get();
    }

    private Player checkCompletePlayer(String playedGameId, String playerLogin) {
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if (player.isEmpty()) {
            throw new NoSuchElementException(PlayerNotFoundMessage);
        }
        if (player.get().getCharacter() == null) {
            throw new NoSuchElementException(PlayerHasNoCharacterAssignedMessage);
        }
        if (player.get().getCharacter().getField() == null) {
            throw new NoSuchElementException(CharacterHasNoFieldAssignedMessage);
        }
        return player.get();
    }

    private Round checkActiveRound(String playedGameId, String playerLogin, RoundState roundState) throws IllegalGameStateException {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        if (playedGame.isPresent() && playedGame.get().getIsStarted()) {
            Round activeRound = playedGame.get().getActiveRound();
            if (!activeRound.getActivePlayer().getLogin().equals(playerLogin)) {
                throw new IllegalGameStateException(PlayerIsNotActiveMessage);
            }
            if (!activeRound.getRoundState().equals(roundState)) {
                throw new IllegalGameStateException(PlayerWrongActionMessage);
            }
            return activeRound;
        }
        throw new IllegalGameStateException(GameNotFoundMessage);
    }


    /**
     * Converts PlayedGameList into PlayedGameListDTO.
     *
     * @param modelMapper    The mapper allowing transformation between Object and DTO.
     * @param playedGameList The object that contains list of played games.
     * @return A DTO.
     */
    public PlayedGameListDTO convertPlayedGameListToDTO(ModelMapper modelMapper, PlayedGameList playedGameList) {
        List<PlayedGameDTO> playedGameDTOList = new ArrayList<>();
        playedGameList.getPlayedGameList().forEach(playedGame -> {
            PlayedGameDTO playedGameDTO = modelMapper.map(playedGame, PlayedGameDTO.class);
            playedGameDTOList.add(playedGameDTO);
        });
        return new PlayedGameListDTO(playedGameDTOList);
    }

    /**
     * Converts FieldList into FieldListDTO.
     *
     * @param modelMapper The mapper allowing transformation between Object and DTO.
     * @param fieldList   A structure containing a list of fields.
     * @return A DTO.
     */
    public FieldListDTO convertFieldListToDTO(ModelMapper modelMapper, FieldList fieldList) {
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        fieldList.getFieldList().forEach(field -> {
            FieldDTO fieldDTO = modelMapper.map(field, FieldDTO.class);
            fieldDTOList.add(fieldDTO);
        });
        return new FieldListDTO(fieldDTOList);
    }

    /**
     * Converts CardList into CardListDTO.
     *
     * @param modelMapper The mapper allowing transformation between Object and DTO.
     * @param cardList    The object that contains list of cards.
     * @return A DTO.
     */
    public CardListDTO convertCardListToDTO(ModelMapper modelMapper, CardList cardList) {
        List<CardDTO> cardDTOList = new ArrayList<>();
        cardList.getCardList().forEach(card -> {
            CardDTO cardDTO = modelMapper.map(card, CardDTO.class);
            cardDTOList.add(cardDTO);
        });
        return new CardListDTO(cardDTOList);
    }

    /**
     * Converts ItemCardList into ItemCardListDTO.
     *
     * @param modelMapper  The mapper allowing transformation between Object and DTO.
     * @param itemCardList The object that contains list of item cards.
     * @return A DTO.
     */
    public ItemCardListDTO convertItemCardListToDTO(ModelMapper modelMapper, ItemCardList itemCardList) {
        List<ItemCardDTO> itemCardDTOList = new ArrayList<>();
        itemCardList.getItemCardList().forEach(itemCard -> {
            ItemCardDTO itemCardDTO = modelMapper.map(itemCard, ItemCardDTO.class);
            itemCardDTOList.add(itemCardDTO);
        });
        return new ItemCardListDTO(itemCardDTOList);
    }

    /**
     * Converts EnemyCardList into EnemyCardListDTO.
     *
     * @param modelMapper   The mapper allowing transformation between Object and DTO.
     * @param enemyCardList A structure containing a list of enemy cards.
     * @return A DTO.
     */
    public EnemyCardListDTO convertEnemyCardListToDTO(ModelMapper modelMapper, EnemyCardList enemyCardList) {
        List<EnemyCardDTO> enemyCardDTOList = new ArrayList<>();
        enemyCardList.getEnemyCardList().forEach(enemyCard -> {
            EnemyCardDTO enemyCardDTO = modelMapper.map(enemyCard, EnemyCardDTO.class);
            enemyCardDTOList.add(enemyCardDTO);
        });
        return new EnemyCardListDTO(enemyCardDTOList);
    }

    /**
     * Converts CharacterList to CharacterListDTO
     *
     * @param modelMapper   The mapper allowing transformation between Object and DTO.
     * @param characterList A structure containing list of characters.
     * @return A DTO.
     */
    public CharacterListDTO convertCharacterListToDTO(ModelMapper modelMapper, CharacterList characterList) {
        List<CharacterDTO> characterDTOList = new ArrayList<>();
        characterList.getCharacterList().forEach(character -> {
            CharacterDTO characterDTO = modelMapper.map(character, CharacterDTO.class);
            characterDTOList.add(characterDTO);
        });
        return new CharacterListDTO(characterDTOList);
    }

    /**
     * Converts PlayerList into PlayerListDTO
     *
     * @param modelMapper The mapper allowing transformation between Object and DTO.
     * @param playerList  A structure containing a list of players.
     * @return A DTO.
     */
    public PlayerListDTO convertPlayerListToDTO(ModelMapper modelMapper, PlayerList playerList) {
        List<PlayerDTO> playerDTOList = new ArrayList<>();
        playerList.getPlayerList().forEach(player -> {
            PlayerDTO playerDTO = modelMapper.map(player, PlayerDTO.class);
            playerDTOList.add(playerDTO);
        });
        return new PlayerListDTO(playerDTOList);
    }


    /**
     * Decreases health points of specified enemy card by given value.
     *
     * @param game      The game to perform actions on.
     * @param player    The player who may get a trophy for defeating an enemy.
     * @param enemyCard The enemy card which health points are to be reduced.
     * @param value     A number that is to be subtracted from enemy's health points.
     */
    private PlayedGame decreaseHealth(PlayedGame game, Player player, EnemyCard enemyCard, Integer value, FightResult fightResult) throws IllegalGameStateException {
        enemyCard.reduceHealth(value);
        if (!enemyCard.isAlive()) {
            if (Objects.equals(enemyCard.getId(), PlayedGameProperties.guardianID)) {
                player.setBridgeGuardianDefeated(true);
                updatePlayer(game, player);
            } else if (Objects.equals(enemyCard.getId(), PlayedGameProperties.bossID)) {
                player.setBossDefeated(true);
                fightResult.setGameWon(true);
                updatePlayer(game, player);
            } else {
                Optional<PlayedGame> updatedGame = moveCardToPlayerTrophies(game.getId(), player.getLogin(), enemyCard.getId());
                fightResult.setEnemyKilled(true);
                if (updatedGame.isPresent()) {
                    checkTrophies(updatedGame.get(), player);
                    return playedGameRepository.save(updatedGame.get());
                }
            }
        }
        updateCardDeck(game, enemyCard);
        return playedGameRepository.save(game);
    }

    /**
     * Decreases health points of a specified player by given value.
     *
     * @param game   The game to perform actions on.
     * @param player The player whose health points are to be reduced.
     * @param value  A number that is to be subtracted from player's health.
     */
    private PlayedGame decreaseHealth(PlayedGame game, Player player, Integer value, FightResult fightResult) throws IllegalGameStateException {
        Optional<ItemCard> card = player.getCardsOnHand().stream().filter(itemCard -> itemCard.getHealth() > 0 && !itemCard.isUsed()).findFirst();
        if (card.isEmpty()) { // no health cards
            player.reduceHealth(value);
            updatePlayer(game, player);
        } else { // decrease health card
            card.get().reduceHealth(value);
            if (card.get().isUsed()) { // remove used up card
                game.getActiveRound().addRoundState(game.getActiveRound().getRoundState());
                game.getActiveRound().setRoundState(RoundState.WAITING_FOR_CARD_TO_USED);
                playedGameRepository.save(game);
                game = moveCardFromPlayerToUsedCardDeck(game.getId(), player.getLogin(), card.get().getId()).get();
            } else {
                updatePlayer(game, player);
                return playedGameRepository.save(game);
            }
        }
        return game;
    }

    /**
     * Check whether the player is the last alive player in game.
     *
     * @param game          The game to check
     * @param checkedPlayer The player to check
     * @return Whether the player to check won the game
     */
    private boolean checkIfPlayerLastStanding(PlayedGame game, Player checkedPlayer) {
        for (Player player : game.getPlayers()) {
            if (player.isAlive() && !player.getLogin().equals(checkedPlayer.getLogin())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if Player has enough trophies to get Strength point. If so, increases Strength points and removes trophies from Player.
     *
     * @param game   The game to perform actions on.
     * @param player The player whose trophies are to be checked.
     */
    private void checkTrophies(PlayedGame game, Player player) {
        if (player.checkTrophies()) {
            player.moveAndIncreaseTrophies();
            updatePlayer(game, player);
            playedGameRepository.save(game);
        }
    }

    /**
     * Updates list of players with a specified updated player.
     *
     * @param game   The played game to perform actions on.
     * @param player The player to be updated.
     */
    private void updatePlayer(PlayedGame game, Player player) {
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getLogin(), player.getLogin()))
                .findFirst();
        if (index.isPresent()) {
            players.set(index.getAsInt(), player);
            game.setPlayers(players);
        }
        if (game.getIsStarted()) {
            for (int d = 0; d < game.getActiveRound().getPlayerList().size(); d++) {
                Player p = game.getActiveRound().getPlayerList().get(d);
                game.getActiveRound().getPlayerList().set(d, players.stream().filter(p1 -> p1.getLogin().equals(p.getLogin())).findFirst().get());
            }
            game.getActiveRound().setActivePlayer(players.stream().filter(p1 -> p1.getLogin().equals(game.getActiveRound().getActivePlayer().getLogin())).findFirst().get());
        }
    }

    /**
     * Updates deck of cards with a specified updated card.
     *
     * @param game The played game to perform actions on.
     * @param card The card to be updated in the card deck.
     */
    private void updateCardDeck(PlayedGame game, Card card) {
        List<Card> cards = game.getCardDeck();
        OptionalInt index = IntStream.range(0, cards.size())
                .filter(i -> Objects.equals(cards.get(i).getId(), card.getId()))
                .findFirst();
        if (index.isPresent()) {
            cards.set(index.getAsInt(), card);
            game.setCardDeck(cards);
        }
    }

    /**
     * Updates board with a specified updated field.
     *
     * @param game  PlayedGame to perform operations on.
     * @param field Field to be updated.
     */
    private void updateField(PlayedGame game, Field field) {
        List<Field> fields = game.getBoard().getFieldsOnBoard();
        OptionalInt index = IntStream.range(0, fields.size())
                .filter(i -> Objects.equals(fields.get(i).getId(), field.getId()))
                .findFirst();
        if (index.isPresent()) {
            fields.set(index.getAsInt(), field);
            PlayedBoard board = new PlayedBoard();
            board.setFieldsOnBoard(fields);
            game.setBoard(board);
        }
    }
}
