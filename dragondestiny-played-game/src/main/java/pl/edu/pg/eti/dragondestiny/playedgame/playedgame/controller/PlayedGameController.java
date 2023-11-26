package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.dragondestiny.playedgame.GameWebSocketHandler;
import pl.edu.pg.eti.dragondestiny.playedgame.board.DTO.PlayedBoardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.Card;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardListDTO;
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
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldOptionListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldList;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldOption;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldOptionList;
import pl.edu.pg.eti.dragondestiny.playedgame.fightresult.DTO.FightResultDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.fightresult.object.FightResult;
import pl.edu.pg.eti.dragondestiny.playedgame.initialization.service.InitializingPlayedGameService;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO.PlayedGameDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.DTO.PlayedGameListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.NotificationEnum;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.NotificationMessage;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGame;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGameList;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.service.PlayedGameService;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerListDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.PlayerList;
import pl.edu.pg.eti.dragondestiny.playedgame.round.DTO.RoundDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.IllegalGameStateException;
import pl.edu.pg.eti.dragondestiny.playedgame.round.object.Round;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Game Web Socket Handler to communicate with players.
     */
    private final GameWebSocketHandler gameWebSocketHandler;

    /**
     * A constructor for PlayedGameController with PlayedGameService, PlayerService and ModelMapper instances.
     *
     * @param playedGameService             A service to handle played game data.
     * @param initializingPlayedGameService A service to initialize a game.
     * @param modelMapper                   A mapper used to transform objects to DTOs.
     */
    @Autowired
    public PlayedGameController(PlayedGameService playedGameService, InitializingPlayedGameService initializingPlayedGameService,
                                ModelMapper modelMapper, GameWebSocketHandler gameWebSocketHandler) {
        this.playedGameService = playedGameService;
        this.initializingPlayedGameService = initializingPlayedGameService;
        this.modelMapper = modelMapper;
        this.gameWebSocketHandler = gameWebSocketHandler;
    }

    // GAME ------------------------------------------------------

    /**
     * Retrieves all played games stored in the database.
     *
     * @return A list of all games.
     */
    @GetMapping("")
    @Tag(name = "Played Game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    public ResponseEntity<PlayedGameListDTO> getAllGames() {
        PlayedGameList playedGameList = new PlayedGameList(playedGameService.findPlayedGames());
        return ResponseEntity.ok().body(playedGameService.convertPlayedGameListToDTO(modelMapper, playedGameList));
    }

    /**
     * Retrieves a played game specified by its ID.
     *
     * @param playedGameId An identifier of a game to be retrieved.
     * @return A retrieved played game.
     */
    @GetMapping("/{playedGameId}")
    @Tag(name = "Played Game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity<PlayedGameDTO> getGame(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> playedGame = playedGameService.findPlayedGame(playedGameId);
        return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Initializes a new game based on chosen scenario ID.
     *
     * @param gameId A scenario ID representing game ID in game engine.
     * @return An initialized game.
     */
    @PutMapping("{gameId}")
    @Tag(name = "Played Game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Game scenario not found", content = @Content)})
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
    @Tag(name = "Played Game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity startGame(@PathVariable(name = "playedGameId") String playedGameId) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.startGame(playedGameId, true);
            if (playedGame.isPresent()) {
                try {
                    gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.GAME_STARTED));
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
                return ResponseEntity.ok().body(modelMapper.map(playedGame.get(), PlayedGameDTO.class));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Deletes a game by its ID.
     *
     * @param playedGameId An identifier of a game to be deleted.
     * @return A confirmation of deletion.
     */
    @DeleteMapping("{playedGameId}")
    @Tag(name = "Played Game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity deleteGame(@PathVariable(name = "playedGameId") String playedGameId) {
        if (playedGameService.delete(playedGameId)) {
            return ResponseEntity.noContent().build();
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
    @Tag(name = "Played Game - round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = RoundDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity getActiveRound(@PathVariable(name = "playedGameId") String playedGameId) {
        try {
            Optional<Round> round = playedGameService.findActiveRound(playedGameId);
            return round.map(value -> ResponseEntity.ok().body(modelMapper.map(value, RoundDTO.class)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        }
    }

    /**
     * Starts next round (a successor of active round) in the game.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @return A new active round.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/round/next")
    @Tag(name = "Played Game - round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = RoundDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity setNextRound(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.nextRound(playedGameId, playerLogin);
            if (playedGame.isPresent()) {
                try {
                    gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.NEXT_ROUND));
                    return ResponseEntity.ok().body(modelMapper.map(playedGame.get().getActiveRound(), RoundDTO.class));
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        }
    }

    /**
     * Selects a round option for the current round for the active player.
     *
     * @param playedGameId
     * @param playerLogin
     * @param fieldOption
     * @return
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/action/{fieldOption}")
    @Tag(name = "Played Game - round")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = RoundDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity selectRoundOption(@PathVariable(name = "playedGameId") String playedGameId,
                                            @PathVariable(name = "playerLogin") String playerLogin,
                                            @PathVariable(name = "fieldOption") FieldOption fieldOption) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.selectRoundOption(playedGameId, playerLogin, fieldOption);
            return ResponseEntity.ok().body(modelMapper.map(playedGame.get().getActiveRound(), RoundDTO.class));
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        }
    }

    // BOARD + FIELDS --------------------------------------------------------------------------------------------------

    /**
     * Retrieves played board from the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A retrieved board.
     */
    @GetMapping("{playedGameId}/board")
    @Tag(name = "Played Game - board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedBoardDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
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
    @Tag(name = "Played Game - board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = FieldListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity<FieldListDTO> getFields(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<FieldList> fieldList = playedGameService.findFields(playedGameId);
        return fieldList.map(list -> ResponseEntity.ok().body(playedGameService.convertFieldListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves data about a specified field in the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param fieldId      An identifier of a field to be retrieved.
     * @return A retrieved field.
     */
    @GetMapping("{playedGameId}/board/fields/{fieldId}")
    @Tag(name = "Played Game - board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = FieldDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Field on board in played game not found", content = @Content)})
    public ResponseEntity<FieldDTO> getField(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "fieldId") Integer fieldId) {
        Optional<Field> field = playedGameService.findField(playedGameId, fieldId);
        return field.map(value -> ResponseEntity.ok().body(modelMapper.map(value, FieldDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves ID of boss field in played game.
     *
     * @param playedGameId
     * @return Boss field ID
     */
    @GetMapping("{playedGameId}/board/fields/boss")
    @Tag(name = "Played Game - board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity getGameBossField(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<Integer> bossFieldId = playedGameService.findBossField(playedGameId);
        return bossFieldId.map(value -> ResponseEntity.ok().body(bossFieldId))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves ID of bridge field in played game.
     *
     * @param playedGameId
     * @return Boss field ID
     */
    @GetMapping("{playedGameId}/board/fields/bridge")
    @Tag(name = "Played Game - board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity getGameBridgeField(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<Integer> bridgeFieldId = playedGameService.findBridgeField(playedGameId);
        return bridgeFieldId.map(value -> ResponseEntity.ok().body(bridgeFieldId))
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
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
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
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity<CardListDTO> getUsedCardDeck(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<CardList> cardList = playedGameService.findUsedCardDeck(playedGameId);
        return cardList.map(list -> ResponseEntity.ok().body(playedGameService.convertCardListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a specified card from card deck.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param cardId       An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    @GetMapping("{playedGameId}/cards/deck/{cardId}")
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CardDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Card in played game not found", content = @Content)})
    public ResponseEntity<CardDTO> getCardFromCardDeck(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId) {
        Optional<Card> card = playedGameService.findCardInCardDeck(playedGameId, cardId);
        return card.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a specified card from used cards deck.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param cardId       An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    @GetMapping("{playedGameId}/cards/used/{cardId}")
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CardDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Card in played game not found", content = @Content)})
    public ResponseEntity<CardDTO> getCardFromUsedCardDeck(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId) {
        Optional<Card> card = playedGameService.findCardInUsedCardDeck(playedGameId, cardId);
        return card.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Removes a specified card from the deck and add it to used cards deck.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param cardId       An identifier of a card to be updated.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/cards/used/{cardId}")
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity moveCardFromCardDeckToUsedCardDeck(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.moveCardFromCardDeckToUsedCardDeck(playedGameId, cardId);
            return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Removes a specified card from given player's hand and add it to used cards deck.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin  An identifier of a player whose hand card is used.
     * @param cardId       An identifier of a card to be moved.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/cards/used/{cardId}")
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Card or player in played game not found", content = @Content)})
    public ResponseEntity moveCardFromPlayerHandToUsedCardDeck(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.moveCardFromPlayerToUsedCardDeck(playedGameId, playerLogin, cardId);
            return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    @PutMapping("{playedGameId}/players/{playerFromLogin}/cards/{cardId}/players/{playerToLogin}")
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Card or player in played game not found", content = @Content)})
    public ResponseEntity moveCardFromPlayerToPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerFromLogin") String playerFromLogin,
                                                     @PathVariable(name = "cardId") Integer cardId, @PathVariable(name = "playerToLogin") String playerToLogin) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.moveCardFromPlayerToPlayer(playedGameId, playerFromLogin, playerToLogin, cardId);
            return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Retrieves an item card from the deck and add it to a specified player's hand.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin  An identifier of a player who gets the card.
     * @param cardId       An identifier of a card to be moved to player's hand.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/cards/hand/{cardId}")
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Card or player in played game not found", content = @Content)})
    public ResponseEntity moveItemCardFromDeckToPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.moveCardToPlayer(playedGameId, playerLogin, cardId);
            if (playedGame.isPresent()) {
                try {
                    gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_GOT_ITEM, playerLogin, cardId));
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
                return ResponseEntity.ok().body(modelMapper.map(playedGame.get(), PlayedGameDTO.class));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Moves defeated enemy to a specified player's trophies.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player who has earned a trophy.
     * @param cardId       An identifier of a defeated enemy.
     * @return An updated game.
     */
    @PutMapping("/{playedGameId}/players/{playerLogin}/cards/trophies/{cardId}")
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Card or player in played game not found", content = @Content)})
    public ResponseEntity moveCardToPlayersTrophies(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.moveCardToPlayerTrophies(playedGameId, playerLogin, cardId);
            return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        }
    }

    /**
     * Draws a random card from the deck of cards.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A random card from the deck.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/deck/draw")
    @Tag(name = "Played Game - cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CardDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity drawRandomCard(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<Card> card = playedGameService.drawCard(playedGameId, playerLogin);
            return card.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CardDTO.class)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    // CHARACTERS ------------------------------------------------------------------------------------------------------

    /**
     * Retrieves a list of all possible characters in the specified game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing list of characters.
     */
    @GetMapping("{playedGameId}/characters")
    @Tag(name = "Played Game - characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity<CharacterListDTO> getAllCharacters(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<CharacterList> characterList = playedGameService.findCharacters(playedGameId);
        return characterList.map(list -> ResponseEntity.ok().body(playedGameService.convertCharacterListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves data about a specified character from the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param characterId  An identifier of a character to be retrieved.
     * @return A retrieved character.
     */
    @GetMapping("{playedGameId}/characters/{characterId}")
    @Tag(name = "Played Game - characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Character in played game not found", content = @Content)})
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
    @Tag(name = "Played Game - characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity<CharacterListDTO> getCharactersInUse(@PathVariable(name = "playedGameId") String playedGameId) {
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
    @Tag(name = "Played Game - characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity getCharactersNotInUse(@PathVariable(name = "playedGameId") String playedGameId) {
        try {
            Optional<CharacterList> characterList = playedGameService.findCharactersNotInUse(playedGameId);
            return characterList.map(list -> ResponseEntity.ok().body(playedGameService.convertCharacterListToDTO(modelMapper, list)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        }
    }

    // PLAYERS ---------------------------------------------------------------------------------------------------------

    /**
     * Retrieves all players participating in a specified game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @return A structure containing a list of players.
     */
    @GetMapping("{playedGameId}/players")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Played game not found", content = @Content)})
    public ResponseEntity<PlayerListDTO> getPlayers(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayerList> playerList = playedGameService.findPlayers(playedGameId);
        return playerList.map(list -> ResponseEntity.ok().body(playedGameService.convertPlayerListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves data about a specified player from the game.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player to be retrieved.
     * @return A retrieved player.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        return player.map(value -> ResponseEntity.ok().body(modelMapper.map(value, PlayerDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of players whose characters stand on the same field.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param fieldId      An identifier of a field to be checked.
     * @return A structure containing a list of players.
     */
    @GetMapping("{playedGameId}/players/field/{fieldId}")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Field on board in played game not found", content = @Content)})
    public ResponseEntity<PlayerListDTO> getPlayersOnField(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "fieldId") Integer fieldId) {
        Optional<PlayerList> playerList = playedGameService.findPlayersByField(playedGameId, fieldId);
        return playerList.map(list -> ResponseEntity.ok().body(playedGameService.convertPlayerListToDTO(modelMapper, list)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Retrieves character of a specified player.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose character is to be retrieved.
     * @return A retrieved player's character.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/character")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Character of player in played game not found", content = @Content)})
    public ResponseEntity getPlayersCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<Character> character = playedGameService.findPlayersCharacter(playedGameId, playerLogin);
            return character.map(value -> ResponseEntity.ok().body(modelMapper.map(value, CharacterDTO.class)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        }
    }

    /**
     * Retrieves a list of cards which belong to given player.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose hand cards are to be retrieved.
     * @return A structure containing a list of item cards.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ItemCardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity<ItemCardListDTO> getCardsFromPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<ItemCardList> itemCardList = playedGameService.findPlayersHandCards(playedGameId, playerLogin);
        return itemCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertItemCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves hand cards of a specified player which increase health statistics.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose hand cards are to be checked.
     * @return A structure containing a list of item cards.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand/health")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ItemCardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity<ItemCardListDTO> getHealthCardsFromPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<ItemCardList> itemCardList = playedGameService.findHealthCardsInPlayer(playedGameId, playerLogin);
        return itemCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertItemCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves hand cards of a specified player which increase strength statistics.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose hand cards are to be checked.
     * @return A structure containing a list of item cards.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand/strength")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ItemCardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity<ItemCardListDTO> getStrengthCardsFromPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<ItemCardList> itemCardList = playedGameService.findStrengthCardsInPlayer(playedGameId, playerLogin);
        return itemCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertItemCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a specified card from player's hand.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose hand cards are to be retrieved.
     * @param cardId       An identifier of a card to be retrieved.
     * @return A retrieved card.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand/{cardId}")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ItemCardDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Card in player's hand in played game not found", content = @Content)})
    public ResponseEntity<ItemCardDTO> getCardFromPlayerHand(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        Optional<ItemCard> card = playedGameService.findCardInPlayerHand(playedGameId, playerLogin, cardId);
        return card.map(c -> ResponseEntity.ok().body(modelMapper.map(c, ItemCardDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all trophies (defeated enemy cards) from a specified player.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose trophies are to be retrieved.
     * @return A structure containing list of trophies (enemy cards).
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/trophies")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = EnemyCardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity<EnemyCardListDTO> getTrophies(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        Optional<EnemyCardList> enemyCardList = playedGameService.findPlayerTrophies(playedGameId, playerLogin);
        return enemyCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertEnemyCardListToDTO(modelMapper, cardList)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Adds a specified player to the given game.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin  An identifier of a player to be added to the game.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player or played game not found", content = @Content)})
    public ResponseEntity addPlayerToGameByLogin(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.addPlayer(playedGameId, playerLogin);
            return playedGame.map(game -> ResponseEntity.ok().body(modelMapper.map(game, PlayedGameDTO.class)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Assigns a specified character to given player.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin  An identifier of a player whose character is to be set.
     * @param characterId  An identifier of a character which is to be assigned to the player.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/character/{characterId}")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Character or player or played game not found", content = @Content)})
    public ResponseEntity selectCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "characterId") Integer characterId) {
        try {
            Optional<PlayedGame> playedGame = playedGameService.assignCharacterToPlayer(playedGameId, playerLogin, characterId);
            if (playedGame.isPresent()) {
                try {
                    gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.CHARACTER_CHOSEN));
                    return ResponseEntity.ok().body(modelMapper.map(playedGame.get(), PlayedGameDTO.class));
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Retrieves a list of fields to which a player can make a move after rolling a specified number from the dice.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose possible new positions are to be calculated.
     * @return A structure containing a list of fields.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/field/move/fields")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity checkPossibleNewPositions(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<FieldList> fieldList = playedGameService.checkPossibleNewPositions(playedGameId, playerLogin);
            return fieldList.map(list -> ResponseEntity.ok().body(playedGameService.convertFieldListToDTO(modelMapper, list)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Updates a specified player's position field.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin  An identifier of a player whose new position is to be set.
     * @param fieldId      An identifier of a field which is player's new position.
     * @return An updated game.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/field/{fieldId}")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayedGameDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Field or player in played game not found", content = @Content)})
    public ResponseEntity changeFieldPositionOfCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "fieldId") Integer fieldId) throws JsonProcessingException {
        try {
            Optional<PlayedGame> playedGame = playedGameService.changePosition(playedGameId, playerLogin, fieldId);
            if (playedGame.isPresent()) {
                try {
                    gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.POSITION_UPDATED));
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
                return ResponseEntity.ok().body(modelMapper.map(playedGame, PlayedGameDTO.class));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Checks possible actions for a player to take while standing on a given field.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose position field is to be checked.
     * @return A structure containing list of possible actions.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/field/options")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = FieldOptionListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity getPlayersPossibleActions(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<FieldOptionList> fieldOptionList = playedGameService.checkFieldOption(playedGameId, playerLogin);
            return fieldOptionList.map(optionList -> ResponseEntity.ok().body(new FieldOptionListDTO(
                            optionList.getPossibleOptions().stream().map(fieldOption -> fieldOption.toDTO()).collect(Collectors.toList()))))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Checks with which enemy players there can be a fight arranged on a field a player stands on.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose position field is ot be checked.
     * @return A structure containing a list of players.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/field/options/players")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity getPlayersToFightWith(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<PlayerList> playerList = playedGameService.findDifferentPlayersByField(playedGameId, playerLogin);
            if (playerList.isPresent()) {
                try {
                    gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_ATTACKED));
                    return ResponseEntity.ok().body(playedGameService.convertPlayerListToDTO(modelMapper, playerList.get()));
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Checks with which enemy cards a player can fight arranged on a field a player stands on.
     *
     * @param playedGameId An identifier of a game to retrieve data about.
     * @param playerLogin  An identifier of a player whose position field is to be checked.
     * @return A structure containing a list of enemy cards.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/field/options/enemies")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = EnemyCardListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity getEnemiesToFightWith(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<EnemyCardList> enemyCardList = playedGameService.findEnemyCardOnPlayersField(playedGameId, playerLogin);
            return enemyCardList.map(cardList -> ResponseEntity.ok().body(playedGameService.convertEnemyCardListToDTO(modelMapper, cardList)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }

    /**
     * Blocks turns of a specified player for a given amount.
     *
     * @param playedGameId      An identifier of a game to be updated.
     * @param playerLogin       An identifier of a player who is to be blocked.
     * @param numOfTurnsToBlock The number representing number of turns to be blocked.
     * @return An updated player's data.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/block/{numOfTurnsToBlock}")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity blockTurnsOfPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "numOfTurnsToBlock") Integer numOfTurnsToBlock) {
        try {
            Optional<Player> player = playedGameService.blockTurnsOfPlayer(playedGameId, playerLogin, numOfTurnsToBlock);
            if (player.isPresent()) {
                try {
                    gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_BLOCKED, playerLogin, player.get().getBlockedTurns()));
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
                return ResponseEntity.ok().body(modelMapper.map(player.get(), PlayerDTO.class));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        }
    }

    /**
     * Blocks turns of a player according to the field a player is standing on.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin  An identifier of a player to be blocked automatically.
     * @return An updated player's data.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/block")
    @Tag(name = "Played Game - players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity blockTurnsOfPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<Player> player = playedGameService.automaticallyBlockTurnsOfPlayer(playedGameId, playerLogin);
            if (player.isPresent()) {
                try {
                    gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_BLOCKED, playerLogin, player.get().getBlockedTurns()));
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
                return ResponseEntity.ok().body(modelMapper.map(player.get(), PlayerDTO.class));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }

    }

    // ACTIONS -----------------------------------------------------------------------------------------------------------

    /**
     * Simulates rolling a die by a specified player.
     *
     * @param playedGameId An identifier a game to retrieve data about.
     * @param playerLogin  An identifier a player who is rolling a die.
     * @return A number representing a number rolled on a die.
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/roll")
    @Tag(name = "Played Game - actions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity rollDice(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        try {
            Optional<Integer> roll = playedGameService.rollDice(playedGameId, playerLogin);
            return roll.map(integer -> ResponseEntity.ok().body(integer))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        }
    }

    /**
     * Handles fight between a specified player and a specified enemy card.
     *
     * @param playedGameId An identifier of a game to be updated.
     * @param playerLogin  An identifier of a player participating in a fight.
     * @param cardId       An identifier of an enemy card participating in a fight.
     * @return A result of a fight between a player and enemy card.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/fight/enemy/{cardId}")
    @Tag(name = "Played Game - actions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = FightResultDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity handleFightWithEnemyCard(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        try {
            Optional<FightResult> fightResult = playedGameService.calculateFightWithEnemyCard(playedGameId, playerLogin, cardId);
            if (fightResult.isPresent()) {
                try {
                    gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_FIGHT, playerLogin, cardId, fightResult.get().getAttackerWon()));
                    if (fightResult.get().getPlayerDead()) {
                        gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_DIED, playerLogin));
                    }
                    if (fightResult.get().getGameWon()) {
                        gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_WON_GAME, fightResult.get().getWonPlayer()));
                    }
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
                return ResponseEntity.ok().body(modelMapper.map(fightResult.get(), FightResultDTO.class));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        }
    }

    /**
     * Handles a fight between one specified player and another specified player.
     *
     * @param playedGameId     An identifier of a game to retrieve data about.
     * @param playerLogin      An identifier of a player (defender) participating in a fight.
     * @param enemyPlayerLogin An identifier of an enemy player (attacker) participating in a fight.
     * @return A result of a fight between attacking and defending player.
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/fight/player/{enemyPlayerLogin}")
    @Tag(name = "Played Game - actions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = FightResultDTO.class))}),
            @ApiResponse(responseCode = "204", description = "Roll saved, waiting for enemy's roll", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Player in played game not found", content = @Content)})
    public ResponseEntity handleFightWithPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "enemyPlayerLogin") String enemyPlayerLogin) {
        try {
            Optional<FightResult> fightResult = playedGameService.calculateFightWithPlayer(playedGameId, playerLogin, enemyPlayerLogin);
            if (fightResult.isPresent()) {
                try {
                    if (fightResult.get().getWonPlayer() != null) {
                        gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_FIGHT, fightResult.get().getWonPlayer(), true));
                        gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_FIGHT, fightResult.get().getLostPlayer(), false));
                    }
                    if (fightResult.get().getPlayerDead()) {
                        gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_DIED, fightResult.get().getLostPlayer()));
                    }
                    if (fightResult.get().getGameWon()) {
                        gameWebSocketHandler.broadcastMessage(playedGameId, new NotificationMessage(NotificationEnum.PLAYER_WON_GAME, fightResult.get().getWonPlayer()));
                    }
                } catch (Exception ex) {
                    return ResponseEntity.internalServerError().body(ex.getMessage());
                }
                return ResponseEntity.ok().body(modelMapper.map(fightResult.get(), FightResultDTO.class));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(ex.toString());
        } catch (IllegalGameStateException ex) {
            return ResponseEntity.status(400).body(ex.toString());
        }
    }
}
