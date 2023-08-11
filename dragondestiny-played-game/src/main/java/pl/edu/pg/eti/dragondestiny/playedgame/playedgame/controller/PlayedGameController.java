package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import pl.edu.pg.eti.dragondestiny.playedgame.board.DTO.PlayedBoardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.DTO.ItemCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.DTO.ItemCardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.character.DTO.CharacterDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.character.DTO.CharacterListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.Character;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldOptionListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.*;
import pl.edu.pg.eti.dragondestiny.playedgame.fightresult.DTO.FightResultDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.fightresult.object.FightResult;
import pl.edu.pg.eti.dragondestiny.playedgame.initialization.service.InitializingPlayedGameService;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO.PlayedGameDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO.PlayedGameListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGame;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGameList;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.service.PlayedGameService;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.PlayerList;
import pl.edu.pg.eti.dragondestiny.playedgame.round.DTO.RoundDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.Round;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.CharacterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * Represents REST controller, allows to handle requests to get played game's data.
 */
@RestController
@RequestMapping("/api/playedgames")
public class PlayedGameController {

    /**
     * ModelMapper allows to map entity object to DTO and DTO to entity.
     */
    private final ModelMapper modelMapper;

    /**
     * Played Game service to get played game from database.
     */
    private final PlayedGameService playedGameService;

    /**
     * Service to initialize game from Game Engine.
     */
    private final InitializingPlayedGameService initializingPlayedGameService;

    /**
     * A constructor for PlayedGameController with PlayedGameService, PlayerService and ModelMapper instances.
     *
     * @param playedGameService A service to handle played game data.
     * @param initializingPlayedGameService A service to initialize a game.
     * @param modelMapper A mapper used to transform objects to DTOs.
     */
    @Autowired
    public PlayedGameController(PlayedGameService playedGameService, InitializingPlayedGameService initializingPlayedGameService, ModelMapper modelMapper) {
        this.playedGameService = playedGameService;
        this.initializingPlayedGameService = initializingPlayedGameService;
        this.modelMapper = modelMapper;
    }

    // TEST - ignore it
    @GetMapping("/{playedGameId}/players/{playerLogin}/test")
    public ResponseEntity<PlayerDTO> testGame(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if(game.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if(player.isEmpty()){
            return ResponseEntity.notFound().build();
        }
       // Player updatedPlayer = player.get().getPlayerManager().checkTrophies(player.get());
        PlayerDTO playerDTO = modelMapper.map(player.get(), PlayerDTO.class);
        return ResponseEntity.ok().body(playerDTO);
    }

    // GAME ------------------------------------------------------
    /**
     * Retrieves a played game specified by its ID.
     *
     * @param playedGameId An identifier of a game to be retrieved.
     * @return A retrieved played game.
     */
    @GetMapping("/{playedGameId}")
    public ResponseEntity<PlayedGameDTO> getGame(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> playedGame = playedGameService.findPlayedGame(playedGameId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all played games stored in the database.
     *
     * @return A list of all games.
     */
   @GetMapping("")
    public ResponseEntity<PlayedGameListDTO> getAllGames() {
        PlayedGameList playedGameList = new PlayedGameList(playedGameService.findPlayedGames());
        return ResponseEntity.ok().body(playedGameService.convertPlayedGameListToDTO(modelMapper, playedGameList));
    }

    /**
     * Initializes a new game based on chosen scenario ID.
     *
     * @param gameId A scenario ID representing game ID in game engine.
     * @return An initialized game.
     */
    @PutMapping("{gameId}")
    public ResponseEntity<PlayedGameDTO> initializeGame(@PathVariable(name = "gameId") Integer gameId) {
        Optional<PlayedGame> playedGame = initializingPlayedGameService.initialize(gameId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Starts a game after players have joined and chosen characters.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @return A started game.
     */
    @PutMapping("{playedGameId}/start")
    public ResponseEntity<PlayedGameDTO> startGame(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> playedGame = playedGameService.startGame(playedGameId, true);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a game by its ID.
     *
     * @param playedGameId An identifier of a game to be deleted.
     * @return A confirmation of deletion.
     */
    @DeleteMapping("{playedGameId}")
    public ResponseEntity<String> deleteGame(@PathVariable(name = "playedGameId") String playedGameId) {
        if(playedGameService.delete(playedGameId)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ROUND -----------------------------------------------------------------------------------------------------------

    /**
     * Retrieves data about currently ongoing (active) round.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return An active round.
     */
    @GetMapping("{playedGameId}/round")
    public ResponseEntity<RoundDTO> getActiveRound(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<Round> round = playedGameService.findActiveRound(playedGameId);
        return round.map(value -> ResponseEntity.ok().body(modelMapper.map(value, RoundDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Starts next round (a successor of active round) in the game.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @return A new active round.
     */
    @PutMapping("{playedGameId}/round/next")
    public ResponseEntity<RoundDTO> setNextRound(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> playedGame = playedGameService.nextRound(playedGameId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game.getActiveRound(), RoundDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // BOARD + FIELDS --------------------------------------------------------------------------------------------------

    /**
     * Retrieves played board from the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A retrieved board.
     */
    @GetMapping("{playedGameId}/board")
    public ResponseEntity<PlayedBoardDTO> getBoard(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedBoard> board = playedGameService.findBoard(playedGameId);
        return board.map(playedBoard -> ResponseEntity.ok().body(modelMapper.map(playedBoard, PlayedBoardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of fields used in the board of the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing a list of fields.
     */
    @GetMapping("{playedGameId}/board/fields")
    public ResponseEntity<FieldListDTO> getFields(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<FieldList> fieldList = playedGameService.findFields(playedGameId);
        return fieldList.map(list -> ResponseEntity.ok().body(playedGameService.convertFieldListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves data about a specified field in the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param fieldId An identifier of a field to be retrieved.
     * @return A retrieved field.
     */
    @GetMapping("{playedGameId}/board/fields/{fieldId}")
    public ResponseEntity<FieldDTO> getField(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "fieldId") Integer fieldId) {
        Optional<Field> field = playedGameService.findField(playedGameId, fieldId);
        return field.map(value -> ResponseEntity.ok().body(modelMapper.map(value, FieldDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CARDS -----------------------------------------------------------------------------------------------------------

    /**
     * Retrieves data about deck of cards used in game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing a list of cards.
     */
    @GetMapping("{playedGameId}/cards/deck")
    public ResponseEntity<CardListDTO> getCardDeck(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<CardList> cardList = playedGameService.findCardDeck(playedGameId);
        return cardList.map(list -> ResponseEntity.ok().body(playedGameService.convertCardListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves data about used cards deck.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing list of cards.
     */
    @GetMapping("{playedGameId}/cards/used")
    public ResponseEntity<CardListDTO> getUsedCardDeck(@PathVariable(name = "playedGameId") String playedGameId) {
       Optional<CardList> cardList = playedGameService.findUsedCardDeck(playedGameId);
        return cardList.map(list -> ResponseEntity.ok().body(playedGameService.convertCardListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a specified card from card deck.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param cardId An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    @GetMapping("{playedGameId}/cards/deck/{cardId}")
    public ResponseEntity<CardDTO> getCardFromCardDeck(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId) {
        Optional<Card> card = playedGameService.findCardInCardDeck(playedGameId, cardId);
        return card.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a specified card from used cards deck.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param cardId An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    @GetMapping("{playedGameId}/cards/used/{cardId}")
    public ResponseEntity<CardDTO> getCardFromUsedCardDeck(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId) {
        Optional<Card> card = playedGameService.findCardInUsedCardDeck(playedGameId, cardId);
        return card.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Removes a specified card from the deck and add it to used cards deck.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param cardId An identifier of a card to be updated.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/cards/used/{cardId}")
    public ResponseEntity<PlayedGameDTO> moveCardFromCardDeckToUsedCardDeck(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId) {
        Optional<PlayedGame> playedGame = playedGameService.moveCardFromCardDeckToUsedCardDeck(playedGameId, cardId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Removes a specified card from given player's hand and add it to used cards deck.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin An identifier of a player whose hand card is used.
     * @param cardId An identifier of a card to be moved.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/cards/used/{cardId}")
    public ResponseEntity<PlayedGameDTO> moveCardFromPlayerHandToUsedCardDeck(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        Optional<PlayedGame> playedGame = playedGameService.moveCardFromPlayerToUsedCardDeck(playedGameId, playerLogin, cardId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves an item card from the deck and add it to a specified player's hand.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin An identifier of a player who gets the card.
     * @param cardId An identifier of a card to be moved to player's hand.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/cards/hand/{cardId}")
    public ResponseEntity<PlayedGameDTO> moveItemCardFromDeckToPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        Optional<PlayedGame> playedGame = playedGameService.moveCardToPlayer(playedGameId, playerLogin, cardId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Moves defeated enemy to a specified player's trophies.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player who has earned a trophy.
     * @param cardId An identifier of a defeated enemy.
     * @return An updated game.
     */
    @PutMapping("/{playedGameId}/players/{playerLogin}/cards/trophies/{cardId}")
    public ResponseEntity<PlayedGameDTO> moveCardToPlayersTrophies(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        Optional<PlayedGame> playedGame = playedGameService.moveCardToPlayerTrophies(playedGameId, playerLogin, cardId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CHARACTERS ------------------------------------------------------------------------------------------------------

    /**
     * Retrieves a list of all possible character in the specified game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing list of characters.
     */
    @GetMapping("{playedGameId}/characters")
    public ResponseEntity<CharacterListDTO> getAllCharacters(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<CharacterList> characterList = playedGameService.findCharacters(playedGameId);
        return characterList.map(list -> ResponseEntity.ok().body(playedGameService.convertCharacterListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves data about a specified character from the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param characterId An identifier of a character to be retrieved.
     * @return A retrieved character.
     */
    @GetMapping("{playedGameId}/characters/{characterId}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "characterId") Integer characterId) {
        Optional<Character> character = playedGameService.findCharacter(playedGameId, characterId);
        return character.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CharacterDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves data about characters already assigned to the players.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing list of characters.
     */
    @GetMapping("{playedGameId}/characters/used")
    public ResponseEntity<CharacterListDTO> getCharactersInUse(@PathVariable(name = "playedGameId") String playedGameId){
        Optional<CharacterList> characterList = playedGameService.findCharactersInUse(playedGameId);
        return characterList.map(list -> ResponseEntity.ok().body(playedGameService.convertCharacterListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of characters which are not assigned to any of the players in the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing list of characters.
     */
    @GetMapping("{playedGameId}/characters/free")
    public ResponseEntity<CharacterListDTO> getCharactersNotInUse(@PathVariable(name = "playedGameId") String playedGameId){
        Optional<CharacterList> characterList = playedGameService.findCharactersNotInUse(playedGameId);
        return characterList.map(list -> ResponseEntity.ok().body(playedGameService.convertCharacterListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PLAYERS ---------------------------------------------------------------------------------------------------------

    /**
     * Retrieves all players participating in a specified game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing a list of players.
     */
    @GetMapping("{playedGameId}/players")
    public ResponseEntity<PlayerListDTO> getPlayers(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayerList> playerList = playedGameService.findPlayers(playedGameId);
        return playerList.map(list -> ResponseEntity.ok().body(playedGameService.convertPlayerListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves data about a specified player from the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player to be retrieved.
     * @return A retrieved player.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        return player.map(value -> ResponseEntity.ok().body(modelMapper.map(value, PlayerDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of players whose characters stand on the same field.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param fieldId An identifier of a field to be checked.
     * @return A structure containing a list of players.
     */
    @GetMapping("{playedGameId}/players/field/{fieldId}")
    public ResponseEntity<PlayerListDTO> getPlayersOnField(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "fieldId") Integer fieldId){
        Optional<PlayerList> playerList = playedGameService.findPlayersByField(playedGameId, fieldId);
        return playerList.map(list -> ResponseEntity.ok().body(playedGameService.convertPlayerListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Retrieves character of a specified player.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose character is to be retrieved.
     * @return A retrieved player's character.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/character")
    public ResponseEntity<CharacterDTO> getPlayersCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<Character> character = playedGameService.findPlayersCharacter(playedGameId, playerLogin);
        return character.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CharacterDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of cards which belong to given player.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose hand cards are to be retrieved.
     * @return A structure containing a list of item cards.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand")
    public ResponseEntity<ItemCardListDTO> getCardsFromPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<ItemCardList> itemCardList = playedGameService.findPlayersHandCards(playedGameId, playerLogin);
        return itemCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertItemCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves hand cards of a specified player which increase health statistics.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose hand cards are to be checked.
     * @return A structure containing a list of item cards.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand/health")
    public ResponseEntity<ItemCardListDTO> getHealthCardsFromPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<ItemCardList> itemCardList = playedGameService.findHealthCardsInPlayer(playedGameId, playerLogin);
        return itemCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertItemCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves hand cards of a specified player which increase strength statistics.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose hand cards are to be checked.
     * @return A structure containing a list of item cards.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand/strength")
    public ResponseEntity<ItemCardListDTO> getStrengthCardsFromPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<ItemCardList> itemCardList = playedGameService.findStrengthCardsInPlayer(playedGameId, playerLogin);
        return itemCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertItemCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a specified card from player's hand.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose hand cards are to be retrieved.
     * @param cardId An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand/{cardId}")
    public ResponseEntity<ItemCardDTO> getCardFromPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        Optional<ItemCard> card = playedGameService.findCardInPlayerHand(playedGameId, playerLogin, cardId);
        return card.map(c -> ResponseEntity.ok().body(modelMapper.map(c, ItemCardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all trophies (defeated enemy cards) from a specified player.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose trophies are to be retrieved.
     * @return A structure containing list of trophies (enemy cards).
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/trophies")
    public ResponseEntity<EnemyCardListDTO> getTrophies(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<EnemyCardList> enemyCardList = playedGameService.findPlayerTrophies(playedGameId, playerLogin);
        return enemyCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertEnemyCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Adds a specified player to the given game.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin An identifier of a player to be added to the game.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}")
    public ResponseEntity<PlayedGameDTO> addPlayerToGameByLogin(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<PlayedGame> playedGame = playedGameService.addPlayer(playedGameId, playerLogin);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Assigns a specified character to given player.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin An identifier of a player whose character is to be set.
     * @param characterId An identifier of a character which is to be assigned to the player.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/character/{characterId}")
    public ResponseEntity<PlayedGameDTO> selectCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "characterId") Integer characterId) {
        Optional<PlayedGame> playedGame = playedGameService.assignCharacterToPlayer(playedGameId, playerLogin, characterId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of fields to which a player can make a move after rolling a specified number from the dice.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose possible new positions are to be calculated.
     * @param rollValue A number corresponding to the number rolled on a die.
     * @return A structure containing a list of fields.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/field/move/{rollValue}/fields")
    public ResponseEntity<FieldListDTO> checkPossibleNewPositions(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "rollValue") Integer rollValue){
        Optional<FieldList> fieldList = playedGameService.checkPossibleNewPositions(playedGameId, playerLogin, rollValue);
        return fieldList.map(list -> ResponseEntity.ok().body(playedGameService.convertFieldListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates a specified player's position field.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin An identifier of a player whose new position is to be set.
     * @param fieldId An identifier of a field which is player's new position.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/field/{fieldId}")
    public ResponseEntity<PlayedGameDTO> changeFieldPositionOfCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "fieldId") Integer fieldId) {
        Optional<PlayedGame> playedGame = playedGameService.changePosition(playedGameId, playerLogin, fieldId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Checks possible actions for a player to take while standing on a given field.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose position field is to be checked.
     * @return A structure containing list of possible actions.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/field/options")
    public ResponseEntity<FieldOptionListDTO> getPlayersPossibleActions(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin){
        Optional<FieldOptionList> fieldOptionList = playedGameService.checkFieldOption(playedGameId, playerLogin);
        return fieldOptionList.map(optionList -> ResponseEntity.ok().body(new FieldOptionListDTO(optionList.getPossibleOptions())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Checks with which enemy players there can be a fight arranged on a field a player stands on.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose position field is ot be checked.
     * @return A structure containing a list of players.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/field/options/players")
    public ResponseEntity<PlayerListDTO> getPlayersToFightWith(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin){
        Optional<PlayerList> playerList = playedGameService.findDifferentPlayersByField(playedGameId, playerLogin);
        return playerList.map(list -> ResponseEntity.ok().body(playedGameService.convertPlayerListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Checks with which enemy cards a player can fight arranged on a field a player stands on
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player whose position field is to be checked.
     * @return A structure containing a list of enemy cards.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/field/options/enemies")
    public ResponseEntity<EnemyCardListDTO> getEnemiesToFightWith(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin){
        Optional<EnemyCardList> enemyCardList = playedGameService.findEnemyCardOnPlayersField(playedGameId, playerLogin);
        return enemyCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertEnemyCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Blocks turns of a specified player for a given amount.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin An identifier of a player who is to be blocked.
     * @param numOfTurnsToBlock The number representing number of turns to be blocked.
     * @return An updated player's data.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/block/{numOfTurnsToBlock}")
    public ResponseEntity<PlayerDTO> blockTurnsOfPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "numOfTurnsToBlock") Integer numOfTurnsToBlock) {
        Optional<Player> player = playedGameService.blockTurnsOfPlayer(playedGameId, playerLogin, numOfTurnsToBlock);
        return player.map(value -> ResponseEntity.ok().body(modelMapper.map(value, PlayerDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Blocks turns of a player according to the field a player is standing on.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin An identifier of a player to be blocked automatically.
     * @return An updated player's data.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/block")
    public ResponseEntity<PlayerDTO> blockTurnsOfPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<Player> player = playedGameService.automaticallyBlockTurnsOfPlayer(playedGameId, playerLogin);
        return player.map(value -> ResponseEntity.ok().body(modelMapper.map(value, PlayerDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Draws a random card from the deck of cards.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A random card from the deck.
     */
    @GetMapping("{playedGameId}/cards/deck/draw")
    public ResponseEntity<CardDTO> drawRandomCard(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<Card> card = playedGameService.drawCard(playedGameId);
        return card.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // ACTIONS -----------------------------------------------------------------------------------------------------------

    /**
     * Simulates rolling a die by a specified player.
     *
     * @param playedGameId An identifier a game to retrieve data about.
     * @param playerLogin An identifier a player who is rolling a die.
     * @return A number representing a number rolled on a die.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/roll")
    public ResponseEntity<Integer> rollDice(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<Integer> roll = playedGameService.rollDice(playedGameId, playerLogin);
        return roll.map(integer -> ResponseEntity.ok().body(integer))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handles fight between a specified player and a specified enemy card.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin An identifier of a player participating in a fight.
     * @param cardId An identifier of an enemy card participating in a fight.
     * @param playerRoll A number that player has rolled on a die.
     * @param enemyRoll A number that an enemy (server) has rolled on a die.
     * @return A result of a fight between a player and enemy card.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/fight/roll/{playerRoll}/enemy/{cardId}/roll/{enemyRoll}")
    public ResponseEntity<FightResultDTO> handleFightWithEnemyCard(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId, @PathVariable(name = "playerRoll") Integer playerRoll, @PathVariable(name = "enemyRoll") Integer enemyRoll) {
        Optional<FightResult> fightResult = playedGameService.calculateFightWithEnemyCard(playedGameId, playerLogin, cardId, playerRoll, enemyRoll);
        return fightResult.map(result -> ResponseEntity.ok().body(modelMapper.map(result, FightResultDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


// Fight with an Enemy on field is still a fight with EnemyCard which has its ID, so method above should be used

//    /**
//     * Call to get result of fight between Player and Enemy from field where the player's character stays.
//     *
//     * @param playedGameId
//     * @param playerLogin
//     * @param playerRoll
//     * @param enemyRoll
//     * @return
//     */
//    @PutMapping("{playedGameId}/players/{playerLogin}/roll/{playerRoll}/enemy/roll/{enemyRoll}")
//    public ResponseEntity<FightResult> handleFight(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "playerRoll") Integer playerRoll, @PathVariable(name = "enemyRoll") Integer enemyRoll) {
//        // find game
//        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
//        if (gameRequest.isEmpty())
//            return ResponseEntity.notFound().build();
//        // find player
//        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
//        if (player.isEmpty())
//            return ResponseEntity.notFound().build();
//        // find enemy from field
//        Optional<Field> field = playedGameService.findField(playedGameId, player.get().getCharacter().getField().getId());
//        if (field.isEmpty())
//            return ResponseEntity.notFound().build();
//        EnemyCard enemy = field.get().getEnemy();
//        if (enemy == null)
//            return ResponseEntity.notFound().build();
//        FightResult fightResult = playedGameService.calculateFight(gameRequest.get(), player.get(), field.get(), enemy, playerRoll, enemyRoll);
//        return ResponseEntity.ok().body(fightResult);
//    }

    /**
     * Handles a fight between one specified player and another specified player.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin An identifier of a player (defender) participating in a fight.
     * @param playerRoll A number that player (defender) has rolled on a die.
     * @param enemyPlayerLogin An identifier of an enemy player (attacker) participating in a fight.
     * @return A result of a fight between attacking and defending player.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/fight/roll/{playerRoll}/player/{enemyPlayerLogin}")
    public ResponseEntity<FightResultDTO> handleFightWithPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "playerRoll") Integer playerRoll, @PathVariable(name = "enemyPlayerLogin") String enemyPlayerLogin) {
        Optional<PlayedGame> playerGame = playedGameService.setPlayerFightRoll(playedGameId, playerLogin, playerRoll);
        if(playerGame.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Optional<FightResult> fightResult = playedGameService.calculateFightWithPlayer(playedGameId, playerLogin, enemyPlayerLogin, playerRoll);
        return fightResult.map(result -> ResponseEntity.ok().body(modelMapper.map(result, FightResultDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}