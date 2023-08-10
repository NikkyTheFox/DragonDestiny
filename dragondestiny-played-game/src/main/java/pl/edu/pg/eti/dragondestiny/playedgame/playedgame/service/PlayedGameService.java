package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.service;

import org.modelmapper.ModelMapper;
import pl.edu.pg.eti.dragondestiny.playedgame.PlayedGameProperties;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.interfaces.HealthCalculable;
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
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldOption;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldOptionList;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldType;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldList;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO.PlayedGameDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO.PlayedGameListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.fightresult.object.FightResult;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGameList;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGame;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.repository.PlayedGameRepository;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.PlayerList;
import pl.edu.pg.eti.dragondestiny.playedgame.player.service.PlayerService;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.Round;

import java.util.*;
import java.util.stream.IntStream;

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
     * @param playerService A service to retrieve players' data.
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
     * Adds new played game to database.
     *
     * @param game The PlayedGame to be saved.
     * @return Saved played game.
     */
    public PlayedGame save(PlayedGame game) {
        return playedGameRepository.save(game);
    }

    /**
     * Deletes played game from database by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A Boolean(True) as a confirmation of deletion.
     */
    public Boolean delete(String playedGameId) {
        Optional<PlayedGame> playedGame = playedGameRepository.findById(playedGameId);
        if(playedGame.isEmpty()){
            return true;
        }
        playedGameRepository.deleteById(playedGameId);
        return true;
    }

    /**
     * Retrieves deck of cards.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing a list of cards.
     */

    public Optional<CardList> findCardDeck(String playedGameId){
        List<Card> cardList = playedGameRepository.findCardDeck(playedGameId);
        if(cardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new CardList(cardList));
    }

    /**
     * Retrieves deck of used cards.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing a list of cards.
     */
    public Optional<CardList> findUsedCardDeck(String playedGameId){
        List<Card> cardList = playedGameRepository.findUsedCardDeck(playedGameId);
        if(cardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new CardList(cardList));
    }

    /**
     * Retrieves card in the card deck by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param cardId An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    public Optional<Card> findCardInCardDeck(String playedGameId, Integer cardId) {
        return playedGameRepository.findCardByIdInCardDeck(playedGameId, cardId).stream().findFirst();
    }

    /**
     * Retrieves card in used card deck by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param cardId An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    public Optional<Card> findCardInUsedCardDeck(String playedGameId, Integer cardId) {
        return playedGameRepository.findCardByIdInUsedDeck(playedGameId, cardId).stream().findFirst();
    }

    /**
     * Find a given card in given player's hand by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose hand will be searched.
     * @param cardId An identifier of a card to be retrieved in player's hand.
     * @return A retrieved card.
     */
    public Optional<ItemCard> findCardInPlayerHand(String playedGameId, String playerLogin, Integer cardId) {
        return playedGameRepository.findCardByIdInPlayerHand(playedGameId, playerLogin, cardId).stream().findFirst();
    }

    /**
     * Retrieves player by Login.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player to be retrieved.
     * @return A retrieved player.
     */
    public Optional<Player> findPlayer(String playedGameId, String playerLogin) {
        return playedGameRepository.findPlayerByLogin(playedGameId, playerLogin).stream().findFirst();
    }

    /**
     * Retrieves player's character by player's login.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose character is to be retrieved.
     * @return A retrieved player's character.
     */
    public Optional<Character> findPlayersCharacter(String playedGameId, String playerLogin){
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        return player.map(Player::getCharacter);
    }

    /**
     * Retrieves all cards in player's hand by player login.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose hand cards are to be retrieved.
     * @return A structure containing a list of item cards.
     */
    public Optional<ItemCardList> findPlayersHandCards(String playedGameId, String playerLogin){
        List<ItemCard> itemCardList = playedGameRepository.findCardsInPlayerHand(playedGameId, playerLogin);
        if(itemCardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new ItemCardList(itemCardList));
    }

    /**
     * Retrieves trophies (beaten enemy card) of a player by player's login.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose trophies are to be retrieved.
     * @return A structure containing a list of trophies (enemy cards).
     */
    public Optional<EnemyCardList> findPlayerTrophies(String playedGameId, String playerLogin){
        List<EnemyCard> enemyCardList = playedGameRepository.findPlayersTrophies(playedGameId, playerLogin);
        if(enemyCardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new EnemyCardList(enemyCardList));
    }

    /**
     * Retrieves all characters that can be used in the game.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing list of characters.
     */
    public Optional<CharacterList> findCharacters(String playedGameId){
        List<Character> characterList = playedGameRepository.findCharacters(playedGameId);
        if(characterList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new CharacterList(characterList));
    }

    /**
     * Retrieves a given character by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param characterId An identifier of a character to be retrieved.
     * @return A retrieved character.
     */
    public Optional<Character> findCharacter(String playedGameId, Integer characterId) {
        return playedGameRepository.findCharacterById(playedGameId, characterId).stream().findFirst();
    }

    /**
     * Retrieves characters that are already assigned to players.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing list of characters.
     */
    public Optional<CharacterList> findCharactersInUse(String playedGameId){
        List<Player> playerList = playedGameRepository.findPlayers(playedGameId);
        if(playerList.isEmpty()){
            return Optional.empty();
        }
        List<Character> characterList = new ArrayList<>();
        playerList.forEach(player -> characterList.add(player.getCharacter()));
        return Optional.of(new CharacterList(characterList));
    }

    /**
     * Retrieves a list of characters from which no character is assigned to a player.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing list of characters.
     */
    public Optional<CharacterList> findCharactersNotInUse(String playedGameId){
        List<Player> playerList = playedGameRepository.findPlayers(playedGameId);
        List<Character> characterList = playedGameRepository.findCharacters(playedGameId);
        if(characterList.isEmpty()){
            return Optional.empty();
        }
        if(playerList.isEmpty()){
            System.out.println("here");
            return Optional.of(new CharacterList(characterList));
        }
        System.out.println("here2");
        List<Character> filteredList = characterList.stream()
                .filter(character -> playerList.stream()
                        .noneMatch(player -> player.getCharacter().getId().equals(character.getId()))
                ).toList();
        return Optional.of(new CharacterList(filteredList));
    }

    /**
     * Retrieves a list of enemies on a given field.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param fieldId An identifier of a field which is to be checked.
     * @return A structure containing a list of enemy cards.
     */
    public Optional<EnemyCardList> findEnemyCardsOnField(String playedGameId, Integer fieldId){
        List<EnemyCard> enemyCardList = playedGameRepository.findEnemyOnField(playedGameId, fieldId);
        if(enemyCardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new EnemyCardList(enemyCardList));
    }

    /**
     * Retrieve a list of enemies on a player's position field.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose position field is to be checked.
     * @return A structure containing a list of enemy cards.
     */
    public Optional<EnemyCardList> findEnemyCardOnPlayersField(String playedGameId, String playerLogin){
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if(player.isEmpty()){
            return Optional.empty();
        }
        return findEnemyCardsOnField(playedGameId, player.get().getCharacter().getField().getId());
    }

    /**
     * Retrieves field by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param fieldId An identifier of a field to be retrieved.
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
        return playedGameRepository.findActiveRound(playedGameId).stream().findFirst();
    }

    /**
     * Retrieves all fields that are on board in a given game.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing a list of fields.
     */
    public Optional<FieldList> findFields(String playedGameId) {
        List<Field> fieldList = playedGameRepository.findFieldsOnBoard(playedGameId);
        if(fieldList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new FieldList(fieldList));
    }

    /**
     * Retrieve all players from a game given by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A structure containing a list of players.
     */
    public Optional<PlayerList> findPlayers(String playedGameId){
        List<Player> playerList = playedGameRepository.findPlayers(playedGameId);
        if(playerList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new PlayerList(playerList));
    }

    /**
     * Retrieves all players on a field given by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param fieldId An identifier of a field to be checked.
     * @return A structure containing a list of players.
     */
    public Optional<PlayerList> findPlayersByField(String playedGameId, Integer fieldId) {
        List<Player> playerList =  playedGameRepository.findPlayersByField(playedGameId, fieldId);
        if(playerList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new PlayerList(playerList));
    }

    /**
     * Retrieves other players from player's position field.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose position field is to be checked.
     * @return A structure containing a list of players.
     */
    public Optional<PlayerList> findDifferentPlayersByField(String playedGameId, String playerLogin) {
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if(player.isEmpty()){
            return Optional.empty();
        }
        List<Player> playerList = playedGameRepository.findDifferentPlayersByField(playedGameId, playerLogin, player.get().getCharacter().getField().getId());
        if(playerList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new PlayerList(playerList));
    }

    /**
     * Retrieves all items that increase health statistics from player's hand.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose hand cards are to be filtered.
     * @return A structure containing a list of item cards.
     */
    public Optional<ItemCardList> findHealthCardsInPlayer(String playedGameId, String playerLogin) {
        List<ItemCard> itemCardList = playedGameRepository.findHealthCardsInPlayerHand(playedGameId, playerLogin);
        if(itemCardList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new ItemCardList(itemCardList));
    }

    /**
     * Retrieves all items that increase strength statistics from player's hand.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose hand cards are to be filtered.
     * @return A structure containing a list of item cards.
     */
    public Optional<ItemCardList> findStrengthCardsInPlayer(String playedGameId, String playerLogin) {
        List<ItemCard> itemCardList = playedGameRepository.findStrengthCardsInPlayerHand(playedGameId, playerLogin);
        if(itemCardList.isEmpty()){
            return Optional.empty();
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
    public Optional<PlayedGame> startGame(String playedGameId, boolean startBoolean) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        if(!startBoolean || playedGame.isEmpty()){
            return Optional.empty();
        }
        PlayedGame game = playedGame.get();
        Round round = new Round();
        round.setId(1);
        List<Player> players = game.getPlayers();
        Collections.shuffle(players);
        round.setPlayers(players);
        Player startingPlayer = players.get(0);
        round.setActivePlayer(startingPlayer);
        game.startGame(round);
        return Optional.of(playedGameRepository.save(game));
    }

    /**
     * Sets next player from list as active player (if player is not blocked).
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return An updated game.
     */
    public Optional<PlayedGame> nextRound(String playedGameId) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        if(playedGame.isEmpty() || !playedGame.get().getIsStarted()){
            return Optional.empty();
        }
        PlayedGame game = playedGame.get();
        Round round = game.getActiveRound();
        round.setId(round.getId() + 1);
        Player activePlayer = round.getActivePlayer();
        Optional<Player> optionalPlayer = round.getPlayers().stream().filter(player ->
            player.getLogin().equals(activePlayer.getLogin())
        ).findFirst();
        if(optionalPlayer.isEmpty()){
            return Optional.empty();
        }
        int id = round.getPlayers().indexOf(optionalPlayer.get());
        Player nextPlayer;
        if (id + 1 < round.getPlayers().size()) {
            nextPlayer = round.getPlayers().get(id + 1);
            if (nextPlayer.getBlockedTurns() > 0) {
                nextPlayer.setBlockedTurns(nextPlayer.getBlockedTurns() - 1);
                updatePlayer(game, nextPlayer);
                if (id + 2 < round.getPlayers().size()) {
                    nextPlayer = round.getPlayers().get(id + 2);
                } else {
                    nextPlayer = round.getPlayers().get(0);
                }
            }
        } else {
            nextPlayer = round.getPlayers().get(0);
        }
        round.setActivePlayer(nextPlayer);
        game.setActiveRound(round);
        return Optional.of(playedGameRepository.save(game));
    }

    /**
     * Adds new player to player list.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player to be added to the game
     * @return An updated game.
     */
    public Optional<PlayedGame> addPlayer(String playedGameId, String playerLogin) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = playerService.findByLogin(playerLogin);
        if(playedGame.isEmpty() || player.isEmpty()){
            return Optional.empty();
        }
        PlayedGame playedGame1 = playedGame.get();
        Player player1 = player.get();
        playedGame1.addPlayerToGame(player1);
        playerService.addGame(playerLogin, playedGameId);
        return Optional.of(playedGameRepository.save(playedGame1));
    }

    /**
     * Updates list of players with a specified updated player.
     *
     * @param game The played game to perform actions on.
     * @param player The player to be updated.
     */
    private void updatePlayer(PlayedGame game, Player player) {
        List<Player> players = game.getPlayers();
        OptionalInt index = IntStream.range(0, players.size())
                .filter(i -> Objects.equals(players.get(i).getLogin(), player.getLogin()))
                .findFirst();
        if (index.isPresent()){
            players.set(index.getAsInt(), player);
            game.setPlayers(players);
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
        if(index.isPresent()){
            cards.set(index.getAsInt(), card);
            game.setCardDeck(cards);
        }
    }

    /**
     * Updates board with a specified updated field.
     *
     * @param game PlayedGame to perform operations on.
     * @param field Field to be updated.
     */
    private void updateField(PlayedGame game, Field field) {
        List<Field> fields = game.getBoard().getFieldsOnBoard();
        OptionalInt index = IntStream.range(0, fields.size())
                .filter(i -> Objects.equals(fields.get(i).getId(), field.getId()))
                .findFirst();
        if(index.isPresent()){
            fields.set(index.getAsInt(), field);
            PlayedBoard board = new PlayedBoard();
            board.setFieldsOnBoard(fields);
            game.setBoard(board);
        }
    }

    /**
     * Sets specified player's character to a character given by ID.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player which has a character to be set.
     * @param characterId An identifier of character to be assigned to the player.
     * @return An updated game.
     */
    public Optional<PlayedGame> assignCharacterToPlayer(String playedGameId, String playerLogin, Integer characterId) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        Optional<Character> character = findCharacter(playedGameId, characterId);
        if(playedGame.isEmpty() || player.isEmpty() || character.isEmpty()){
            return Optional.empty();
        }
        PlayedGame playedGame1 = playedGame.get();
        Player player1 = player.get();
        Character character1 = character.get();
        Optional<Field> field = findField(playedGameId, character1.getField().getId());
        if(field.isEmpty()){
            return Optional.empty();
        }
        player1.setCharacter(character1);
        updatePlayer(playedGame1, player1);
        playedGameRepository.save(playedGame1);
        return changePosition(playedGameId, playerLogin, field.get().getId());
    }

    /**
     * Sets roll value to Player's fight roll value.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player which has FightRoll to be set
     * @param rollValue A value to be set as player's FightRoll
     * @return An updated game.
     */
    public Optional<PlayedGame> setPlayerFightRoll(String playedGameId, String playerLogin, Integer rollValue) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if(playedGame.isEmpty() || player.isEmpty()){
            return Optional.empty();
        }
        PlayedGame playedGame1 = playedGame.get();
        Player player1 = player.get();
        player1.setFightRoll(rollValue);
        updatePlayer(playedGame1, player1);
        return Optional.of(playedGameRepository.save(playedGame1));
    }

    /**
     * Removes card from Card Deck and adds to Used Card Deck.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param cardId An identifier of a card to be moved.
     * @return An updated game.
     */

    public Optional<PlayedGame> moveCardFromCardDeckToUsedCardDeck(String playedGameId, Integer cardId) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Card> card = findCardInCardDeck(playedGameId, cardId);
        if(playedGame.isEmpty() || card.isEmpty()){
            return Optional.empty();
        }
        PlayedGame game = playedGame.get();
        Card card1 = card.get();
        game.addCardToUsedDeck(card1);
        game.removeCardFromDeck(card1);
        return Optional.of(playedGameRepository.save(game));
    }

    /**
     * Removes card from Card Deck and adds to Player's cards on hand.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player to whose hand a card is to be moved.
     * @param cardId An identifier of a card that is to be moved to a player hand.
     * @return An updated game.
     */
    public Optional<PlayedGame> moveCardToPlayer(String playedGameId, String playerLogin, Integer cardId) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        Optional<Card> card = findCardInCardDeck(playedGameId, cardId);
        if(playedGame.isEmpty() || player.isEmpty() || card.isEmpty() || !player.get().checkCardsOnHand()){
            return Optional.empty();
        }
        PlayedGame playedGame1 = playedGame.get();
        Player player1 = player.get();
        Card card1 = card.get();
        player1.moveCardToPlayer(card1);
        playedGame1.removeCardFromDeck(card1);
        updatePlayer(playedGame1, player1);
        return Optional.of(playedGameRepository.save(playedGame1));
    }

    /**
     * Removes card from Card Deck and adds to Player's trophies.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player who has earned a trophy.
     * @param cardId An identifier of a card to be moved to player's trophy list.
     * @return An updated game.
     */
    public Optional<PlayedGame> moveCardToPlayerTrophies(String playedGameId, String playerLogin, Integer cardId) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        Optional<Card> card = findCardInCardDeck(playedGameId, cardId);
        if(playedGame.isEmpty() || player.isEmpty() || card.isEmpty()){
            return Optional.empty();
        }
        PlayedGame playedGame1 = playedGame.get();
        Player player1 = player.get();
        Card card1 = card.get();
        player1.moveCardToTrophies(card1);
        playedGame1.removeCardFromDeck(card1);
        updatePlayer(playedGame1, player1);
        checkTrophies(playedGame1, player1);
        return Optional.of(playedGameRepository.save(playedGame1));
    }

    /**
     * Removes card from Players' hand and adds to Used Card Deck.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose card is meant to be moved to used card deck.
     * @param cardId An identifier of a card to be moved.
     * @return An updated game.
     */
    public Optional<PlayedGame> moveCardFromPlayerToUsedCardDeck(String playedGameId, String playerLogin, Integer cardId) {
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<ItemCard> itemCard = findCardInPlayerHand(playedGameId, playerLogin, cardId);
        if(playedGame.isEmpty() || player.isEmpty() || itemCard.isEmpty()){
            return Optional.empty();
        }
        Card card = itemCard.get();
        Player player1 = player.get();
        PlayedGame playedGame1 = playedGame.get();
        player1.removeCardFromPlayer(card);
        playedGame1.addCardToUsedDeck(card);
        updatePlayer(playedGame1, player1);
        return Optional.of(playedGameRepository.save(playedGame1));
    }


    /**
     * Check if Player has enough trophies to get Strength point. If so, increases Strength points and removes trophies from Player.
     *
     * @param game The game to perform actions on.
     * @param player The player whose trophies are to be checked.
     */
    public void checkTrophies(PlayedGame game, Player player) {
        if(player.checkTrophies()) {
            player.moveAndIncreaseTrophies();
            updatePlayer(game, player);
            playedGameRepository.save(game);
        }
    }

    /**
     * Change Player's character position Field.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose position is to be changed.
     * @param fieldId An identifier of a new player's position field.
     * @return An updated game.
     */
    public Optional<PlayedGame> changePosition(String playedGameId, String playerLogin, Integer fieldId) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        Optional<Field> field = findField(playedGameId, fieldId);
        if(playedGame.isEmpty() || player.isEmpty() || field.isEmpty()){
            return Optional.empty();
        }
        Player player1 = player.get();
        PlayedGame playedGame1 = playedGame.get();
        Field field1 = field.get();
        player1.setPositionField(field1);
        updatePlayer(playedGame1, player1);
        return Optional.of(playedGameRepository.save(playedGame1));
    }

    /**
     * Retrieves fields a player can move to after a die roll.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player that has rolled a die.
     * @param rollValue A value that has been rolled.
     * @return A structure containing a list of fields.
     */
    public Optional<FieldList> checkPossibleNewPositions(String playedGameId, String playerLogin, Integer rollValue) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if(playedGame.isEmpty() || player.isEmpty()){
            return Optional.empty();
        }
        Player player1 = player.get();
        PlayedGame playedGame1 = playedGame.get();
        Field currentPlayersField = player1.getPositionField();
        List<Field> temporalFieldList = new ArrayList<>();
        List<Field> fieldList = playedGameRepository.findFieldsOnBoard(playedGameId);
        int boardSize = fieldList.size() - 1; // -1 because of boss field

        // check forward:
        Integer firstOptionId = (currentPlayersField.getId() + rollValue - 1 ) % boardSize + 1;
        Optional<Field> firstOption = this.findField(playedGame1.getId(), firstOptionId);
        firstOption.ifPresent(temporalFieldList::add);

        // check backward:
        int secondOptionId = currentPlayersField.getId() - rollValue;
        if(secondOptionId <= 0){
          secondOptionId += boardSize;
        }
        Optional<Field> secondOption = this.findField(playedGame1.getId(), secondOptionId);
        secondOption.ifPresent(temporalFieldList::add);

        // check whether a player can fight Bridge Guardian in order to go for boss
        if(currentPlayersField.getType() == FieldType.BRIDGE_FIELD){
        Optional<Field> thirdOption = this.findField(playedGame1.getId(), PlayedGameProperties.guardianFieldID); // boss field id
        thirdOption.ifPresent(temporalFieldList::add);
        }

        // check whether a player can move back from Boss to Bridge Guardian
        if(currentPlayersField.getType() == FieldType.BOSS_FIELD){
            Optional<Field> thirdOption = this.findField(playedGame1.getId(), PlayedGameProperties.bossFieldID); // boss field id
            thirdOption.ifPresent(temporalFieldList::add);
        }
        return Optional.of(new FieldList(temporalFieldList));
    }

    /**
     * Checks type of field the player stands on, returns list of possible options on that field.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player whose position field is to be checked.
     * @return A structure containing list of possible options.
     */
    public Optional<FieldOptionList> checkFieldOption(String playedGameId, String playerLogin) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if(playedGame.isEmpty() || player.isEmpty()){
            return Optional.empty();
        }
        Optional<Field> field = findField(playedGameId, player.get().getCharacter().getField().getId());
        if(field.isEmpty()){
            return Optional.empty();
        }
        PlayedGame playedGame1 = playedGame.get();
        Player player1 = player.get();
        Field field1 = field.get();

        FieldOptionList list = new FieldOptionList();
        list.getPossibleOptions().add(FieldOption.valueOf(field1.getType().toString()));
        if (field1.getType() == FieldType.BOSS_FIELD){
            list.getPossibleOptions().add(FieldOption.BRIDGE_FIELD);
        }
        if(field1.getType() == FieldType.BRIDGE_FIELD){
            list.getPossibleOptions().add(FieldOption.BOSS_FIELD);
        }
        Optional<PlayerList> enemyPlayerList = findDifferentPlayersByField(playedGame1.getId(), player1.getLogin());
        if (enemyPlayerList.isPresent())
        {
            list.getPossibleOptions().add(FieldOption.FIGHT_WITH_PLAYER);
        }
        Optional<EnemyCardList> enemyCardList = findEnemyCardsOnField(playedGameId, field1.getId());
        if(enemyCardList.isPresent()){
            list.getPossibleOptions().add(FieldOption.FIGHT_WITH_ENEMY_ON_FIELD);
        }
        return Optional.of(list);
    }

    /**
     * Returns a random card from card deck.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @return A randomly drawn card.
     */
    public Optional<Card> drawCard(String playedGameId) {
        Random random = new Random();
        List<Card> cardList = playedGameRepository.findCardDeck(playedGameId);
        if(cardList.isEmpty()){
            return Optional.empty();
        }
        int cardToDrawIndex = random.nextInt(cardList.size());
        List<Card> cardToDraw = playedGameRepository.findCardByIndexInCardDeck(playedGameId, cardToDrawIndex - 1);
        return cardToDraw.stream().findFirst();
    }

    /**
     * Calculates fight result between Player and Enemy from card.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player that participates in a fight.
     * @param enemyCardId An identifier of en enemy that participates in a fight.
     * @param playerRollValue A value rolled by a player.
     * @param enemyRollValue A value rolled by an enemy (rolled by server).
     * @return A result of a fight.
     */
    public Optional<FightResult> calculateFightWithEnemyCard(String playedGameId, String playerLogin, Integer enemyCardId, Integer playerRollValue, Integer enemyRollValue) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        Optional<Card> card = findCardInCardDeck(playedGameId, enemyCardId);
        if(playedGame.isEmpty() || player.isEmpty() || card.isEmpty()){
            return Optional.empty();
        }
        PlayedGame playedGame1 = playedGame.get();
        Player player1 = player.get();
        Card card1 = card.get();
        EnemyCard enemyCard = (EnemyCard) card1;
        FightResult fightResult = new FightResult();
        int playerResult = player1.getStrength() + playerRollValue;
        System.out.println("PLAYER " + playerResult);
        int enemyResult = enemyCard.getInitialStrength() + enemyRollValue;
        System.out.println("ENEMY " + enemyResult);
        if (playerResult >= enemyResult) { // player won
            decreaseHealth(playedGame1, player1, enemyCard, -1);
            fightResult.setAttackerWon(true);
            if (!isAlive(enemyCard))
                fightResult.setEnemyKilled(true);
        } else { // player lost
            decreaseHealth(playedGame1, player1, 1);
            fightResult.setAttackerWon(false);
            if (!isAlive(player1))
                fightResult.setPlayerDead(true);
        }
        return Optional.of(fightResult);
    }

//    /**
//     * Method to calculate fight result between Player and Enemy from field.
//     *
//     * @param game
//     * @param player
//     * @param field
//     * @param enemy
//     * @param playerRoll
//     * @param enemyRoll
//     * @return
//     */
//    public FightResult calculateFight(PlayedGame game, Player player, Field field, EnemyCard enemy, Integer playerRoll, Integer enemyRoll) {
//        FightResult fightResult = new FightResult();
//        Integer playerResult = player.getPlayerManager().calculateTotalStrength(player) + playerRoll;
//        System.out.println("PLAYER " + playerResult);
//        Integer enemyResult = enemy.getCardManager().calculateTotalStrength(enemy) + enemyRoll;
//        System.out.println("ENEMY " + enemyResult);
//        if (playerResult >= enemyResult) { // PLAYER WON
//            decreaseHealth(game, player, field, enemy, -1);
//            fightResult.setAttackerWon(true);
//            if (enemy.getCardManager().calculateTotalHealth(enemy) <= 0) {
//                fightResult.setEnemyKilled(true);
//                resetFieldEnemy(game, player, field, enemy);
//                if (field.getType() == FieldType.BOSS_FIELD)
//                    fightResult.setGameWon(true);
//            }
//        } else { // PLAYER LOST
//            decreaseHealth(game, player, -1);
//            fightResult.setAttackerWon(false);
//            if (player.getPlayerManager().calculateTotalHealth(player) <= 0)
//                fightResult.setPlayerDead(true);
//        }
//        return fightResult;
//    }

    /**
     * Calculates fight result between two players. Player is treated as a defender and Enemy is treated as an attacker.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player (defender) that participates in a fight.
     * @param enemyPlayerLogin An identifier of a player (attacker) that participates in a fight.
     * @param playerRollValue A value rolled by player (defender)
     * @return A result of a fight.
     */
    public Optional<FightResult> calculateFightWithPlayer(String playedGameId, String playerLogin, String enemyPlayerLogin, Integer playerRollValue) {
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        Optional<Player> enemyPlayer = findPlayer(playedGameId, enemyPlayerLogin);
        if(playedGame.isEmpty() || player.isEmpty() || enemyPlayer.isEmpty()){
            return Optional.empty();
        }
        if(enemyPlayer.get().getFightRoll() == 0){
            // call from the attacker, wait for attacker roll
            return Optional.empty();
        }
        PlayedGame playedGame1 = playedGame.get();
        Player player1 = player.get();
        Player enemyPlayer1 = enemyPlayer.get();

        FightResult fightResult = new FightResult();
        int playerResult = player1.getStrength() + playerRollValue;
        System.out.println("PLAYER " + playerResult);
        int enemyResult = enemyPlayer1.getStrength() + enemyPlayer1.getFightRoll();
        System.out.println("ENEMY PLAYER " + enemyResult);
        setPlayerFightRoll(playedGameId, playerLogin, 0);
        setPlayerFightRoll(playedGameId, enemyPlayerLogin, 0);
        if (playerResult >= enemyResult) { // DEFENDER (PLAYER) WON
            fightResult.setAttackerWon(false);
            fightResult.setWonPlayer(player1.getLogin());
            fightResult.setLostPlayer(enemyPlayer1.getLogin());
            List<ItemCard> loserHealthCards = playedGameRepository.findHealthCardsInPlayerHand(playedGameId, enemyPlayerLogin);
            if (loserHealthCards.isEmpty()) { // no health cards
                decreaseHealth(playedGame1, enemyPlayer1, -1);
                if (!isAlive(enemyPlayer1))
                    fightResult.setEnemyKilled(true);
            } else {
                fightResult.setChooseCardFromEnemyPlayer(true);
            }
        }
        else { // ATTACKER (ENEMY) WON
            fightResult.setAttackerWon(true);
            fightResult.setWonPlayer(enemyPlayer1.getLogin());
            fightResult.setLostPlayer(player1.getLogin());
            List<ItemCard> loserHealthCards = playedGameRepository.findHealthCardsInPlayerHand(playedGameId, playerLogin);
            if (loserHealthCards.isEmpty()) { // no health cards
                decreaseHealth(playedGame1, player1, -1);
                if(!isAlive(player1)){
                    fightResult.setPlayerDead(true);
                }
            } else {
                fightResult.setChooseCardFromEnemyPlayer(true);
            }
        }
        return Optional.of(fightResult);
    }

    /**
     * Decreases health points of a specified player by given value.
     *
     * @param game The game to perform actions on.
     * @param player The player whose health points are to be reduced.
     * @param value A number that is to be subtracted from player's health.
     */
    public void decreaseHealth(PlayedGame game, Player player, Integer value) {
        Optional<ItemCard> card = player.getCardsOnHand().stream().filter(itemCard -> !itemCard.isUsed()).findFirst();
        if (card.isEmpty()) {
            // no health cards
            player.reduceHealth(value);
        } else {
            // decrease health card
            card.get().reduceHealth(value);
            if (card.get().isUsed()) { // remove used up card
                moveCardFromPlayerToUsedCardDeck(game.getId(), player.getLogin(), card.get().getId());
            }
        }
        updatePlayer(game, player);
        playedGameRepository.save(game);
    }

    /**
     * Decreases health points of specified enemy card by given value.
     *
     * @param game The game to perform actions on.
     * @param player The player who may get a trophy for defeating an enemy.
     * @param enemyCard The enemy card which health points are to be reduced.
     * @param value A number that is to be subtracted from enemy's health points.
     */
    public void decreaseHealth(PlayedGame game, Player player, EnemyCard enemyCard, Integer value) {
        enemyCard.reduceHealth(value);
        if (!isAlive(enemyCard) &&
                !Objects.equals(enemyCard.getId(), PlayedGameProperties.guardianID) && // check if defeated card is bridge guardian
                !Objects.equals(enemyCard.getId(), PlayedGameProperties.bossID)) { //check if defeated card is boss

            Optional<PlayedGame> updatedGame = moveCardToPlayerTrophies(game.getId(), player.getLogin(), enemyCard.getId());
            if(updatedGame.isPresent()){
                checkTrophies(updatedGame.get(), player);
                playedGameRepository.save(updatedGame.get());
                return;
            }
        }
        updateCardDeck(game, enemyCard);
        playedGameRepository.save(game);
    }

//    /**
//     * Method to decrease health points of enemy from field by value.
//     *
//     * @param game The game to perform actions on.
//     * @param enemyCard The enemy card which health points are to be reduced.
//     * @param value A number that is to be subtracted from enemy's health points.
//     */
//    public void decreaseHealth(PlayedGame game, Field field, EnemyCard enemyCard, Integer value) {
//        enemyCard.reduceHealth(value);
//        field.setEnemy(enemyCard);
//        System.out.println("Set enemy on field " + field.getEnemy().getReceivedHealth());
//        updateField(game, field);
//        playedGameRepository.save(game);
//    }

    /**
     * Resets enemy from field to its initial health points.
     *
     * @param game The game to perform actions on.
     * @param field The field on which enemy stands.
     * @param enemyCard The enemy card to reset,
     */
    public void resetFieldEnemy(PlayedGame game, Field field, EnemyCard enemyCard) {
        enemyCard.setHealth(0);
        field.setEnemy(enemyCard);
        updateField(game, field);
        playedGameRepository.save(game);
    }

    /**
     * Simulates die roll.
     *
     * @param playedGameId The ID of a played game to perform actions on.
     * @param playerLogin The ID of a player performing a die roll.
     * @return Random number.
     */
    public Optional<Integer> rollDice(String playedGameId, String playerLogin) {
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if(player.isEmpty()){
            return Optional.empty();
        }
        /*Random random = new Random();
        Integer toReturn = random.nextInt(PlayedGameApplication.lowDiceBound, PlayedGameApplication.upDiceBound + 1);
        return Optional.of(toReturn)*/

        //FOR TEST PURPOSES:
        return Optional.of(1);
    }

    /**
     * Blocks player for a given number of turns.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player to be blocked.
     * @param numOfTurnsToBlock A number of turns to be blocked.
     * @return An updated player.
     */
    public Optional<Player> blockTurnsOfPlayer(String playedGameId, String playerLogin, Integer numOfTurnsToBlock){
        Optional<PlayedGame> playedGame = findPlayedGame(playedGameId);
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if(playedGame.isEmpty() || player.isEmpty() || numOfTurnsToBlock == 0){
            return Optional.empty();
        }
        PlayedGame playedGame1 = playedGame.get();
        Player player1 = player.get();
        player1.setBlockedTurns(numOfTurnsToBlock);
        updatePlayer(playedGame1, player1);
        playedGameRepository.save(playedGame1);
        return Optional.of(player1);
    }

    /**
     * Blocks a player for number of turns dependent on field type.
     *
     * @param playedGameId An identifier of a played game to perform actions on.
     * @param playerLogin An identifier of a player to be blocked.
     * @return An updated player.
     */
    public Optional<Player> automaticallyBlockTurnsOfPlayer(String playedGameId, String playerLogin){
        Optional<Player> player = findPlayer(playedGameId, playerLogin);
        if(player.isEmpty()){
            return Optional.empty();
        }
        Optional<Field> field = findField(playedGameId, player.get().getCharacter().getField().getId());
        if(field.isEmpty()){
            return Optional.empty();
        }
        int numOfTurnsToBlock = 0;
        if(field.get().getType() == FieldType.LOSE_ONE_ROUND){
            numOfTurnsToBlock = 1;
        }
        if(field.get().getType() == FieldType.LOSE_TWO_ROUNDS){
            numOfTurnsToBlock = 2;
        }
        return blockTurnsOfPlayer(playedGameId, playerLogin, numOfTurnsToBlock);
    }

    /**
     * Helper method to check if an object instance (player or enemy card) is still alive.
     *
     * @param object A player or enemy card.
     * @return True if alive.
     */
    public <T extends HealthCalculable> Boolean isAlive(T object){

        return object.isAlive();
    }

    // methods to convert objects to DTO

    /**
     * Converts PlayedGameList into PlayedGameListDTO.
     *
     * @param modelMapper The mapper allowing transformation between Object and DTO.
     * @param playedGameList The object that contains list of played games.
     * @return A DTO.
     */
    public PlayedGameListDTO convertPlayedGameListToDTO(ModelMapper modelMapper, PlayedGameList playedGameList){
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
     * @param fieldList A structure containing a list of fields.
     * @return A DTO.
     */
    public FieldListDTO convertFieldListToDTO(ModelMapper modelMapper, FieldList fieldList){
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
     * @param cardList The object that contains list of cards.
     * @return A DTO.
     */
    public CardListDTO convertCardListToDTO(ModelMapper modelMapper, CardList cardList){
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
     * @param modelMapper The mapper allowing transformation between Object and DTO.
     * @param itemCardList The object that contains list of item cards.
     * @return A DTO.
     */
    public ItemCardListDTO convertItemCardListToDTO(ModelMapper modelMapper, ItemCardList itemCardList){
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
     * @param modelMapper The mapper allowing transformation between Object and DTO.
     * @param enemyCardList A structure containing a list of enemy cards.
     * @return A DTO.
     */
    public EnemyCardListDTO convertEnemyCardListToDTO(ModelMapper modelMapper, EnemyCardList enemyCardList){
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
     * @param modelMapper The mapper allowing transformation between Object and DTO.
     * @param characterList A structure containing list of characters.
     * @return A DTO.
     */
    public CharacterListDTO convertCharacterListToDTO(ModelMapper modelMapper, CharacterList characterList){
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
     * @param playerList A structure containing a list of players.
     * @return A DTO.
     */
    public PlayerListDTO convertPlayerListToDTO(ModelMapper modelMapper, PlayerList playerList){
        List<PlayerDTO> playerDTOList = new ArrayList<>();
        playerList.getPlayerList().forEach(player -> {
            PlayerDTO playerDTO = modelMapper.map(player, PlayerDTO.class);
            playerDTOList.add(playerDTO);
        });
        return new PlayerListDTO(playerDTOList);
    }
}