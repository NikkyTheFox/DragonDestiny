package pl.edu.pg.eti.game.playedgame.game.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import pl.edu.pg.eti.game.playedgame.board.entity.PlayedBoard;
import pl.edu.pg.eti.game.playedgame.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.game.playedgame.card.enemycard.response.EnemyCardList;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.card.entity.CardList;
import pl.edu.pg.eti.game.playedgame.card.entity.CardType;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.card.itemcard.response.ItemCardList;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.character.response.CharacterList;
import pl.edu.pg.eti.game.playedgame.field.FieldOptionList;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.field.response.FieldList;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGame;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGameList;
import pl.edu.pg.eti.game.playedgame.game.service.PlayedGameService;
import pl.edu.pg.eti.game.playedgame.game.service.initialize.InitializePlayedGame;
import pl.edu.pg.eti.game.playedgame.player.response.PlayerList;
import pl.edu.pg.eti.game.playedgame.player.entity.PlayerManager;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.edu.pg.eti.game.playedgame.player.service.PlayerService;
import pl.edu.pg.eti.game.playedgame.round.Round;

import java.util.Optional;

@RestController
@RequestMapping("/api/playedgames")
public class PlayedGameController {

    /**
     * Played Game service to get played game from database.
     */
    private PlayedGameService playedGameService;

    /**
     * Player service to get players from database.
     */
    private PlayerService playerService;

    /**
     * Service to initialize game from Game Engine.
     */
    private InitializePlayedGame initializePlayedGame;

    @Autowired
    public PlayedGameController(PlayedGameService playedGameService, PlayerService playerService, InitializePlayedGame initializePlayedGame) {
        this.playedGameService = playedGameService;
        this.playerService = playerService;
        this.initializePlayedGame = initializePlayedGame;
    }

    // TEST - ignore it
    @GetMapping("/{playedGameId}/players/{playerLogin}/test")
    public ResponseEntity<Player> testGame(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
       // Player updatedPlayer = player.get().getPlayerManager().checkTrophies(player.get());
        return ResponseEntity.ok().body(player.get());
    }

    // GAME ------------------------------------------------------
    /**
     * Call to get played game with id playedGameId
     *
     * @param playedGameId
     * @return PlayedGame
     */
    @GetMapping("/{playedGameId}")
    public ResponseEntity<PlayedGame> getGame(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(game.get());
    }

    /**
     * Call to get all played games
     *
     * @return list of all games
     */
    @GetMapping("")
    public ResponseEntity<PlayedGameList> getAllGames() {
        PlayedGameList playedGameList = new PlayedGameList(playedGameService.findPlayedGames());
        return ResponseEntity.ok().body(playedGameList);
    }

    /**
     * Call to initialize new played game from general games with id gameId.
     * Uses communication with game engine to fetch game with id gameId.
     *
     * @param gameId
     * @return initialized game
     */
    @PostMapping("{gameId}")
    public ResponseEntity<PlayedGame> initializeGame(@PathVariable(name = "gameId") Integer gameId) {
        PlayedGame playedGameRequest = initializePlayedGame.initialize(gameId);
        PlayedGame game = playedGameService.save(playedGameRequest);
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to start initialized game with players added.
     *
     * @param playedGameId
     * @return
     */
    @PutMapping("{playedGameId}/start")
    public ResponseEntity<PlayedGame> startGame(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        playedGameService.startGame(game.get());
        return ResponseEntity.ok().body(game.get());
    }

    /**
     * Call to delete game by ID.
     *
     * @param playedGameId
     * @return
     */
    @DeleteMapping("{playedGameId}")
    public ResponseEntity<String> deleteGame(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        playedGameService.deleteById(playedGameId);
        return ResponseEntity.ok().build();
    }

    // ROUND -----------------------------------------------------------------------------------------------------------

    /**
     * Call to get active round in played game.
     *
     * @param playedGameId
     * @return
     */
    @GetMapping("{playedGameId}/round")
    public ResponseEntity<Round> getActiveRound(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<Round> round = playedGameService.findActiveRound(playedGameId);
        if (round.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(round.get());
    }

    /**
     * Call to set next round in played game.
     * Increases ID of round and sets next player from the list as active player.
     *
     * @param playedGameId
     * @return
     */
    @PutMapping("{playedGameId}/round/next")
    public ResponseEntity<Round> setNextRound(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<Round> round = playedGameService.findActiveRound(playedGameId);
        if (round.isEmpty()) { // create first round
            playedGameService.startGame(game.get());
            return ResponseEntity.ok().body(game.get().getActiveRound());
        } // get next round
        playedGameService.nextRound(game.get(), round.get());
        return ResponseEntity.ok().body(game.get().getActiveRound());
    }

    // BOARD + FIELDS --------------------------------------------------------------------------------------------------

    /**
     * Call to get board in played game.
     *
     * @param playedGameId
     * @return
     */
    @GetMapping("{playedGameId}/board")
    public ResponseEntity<PlayedBoard> getBoard(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        PlayedBoard board = game.get().getBoard();
        if (board == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(board);
    }

    /**
     * Call to get all fields of board in played game.
     *
     * @param playedGameId
     * @return
     */
    @GetMapping("{playedGameId}/board/fields")
    public ResponseEntity<FieldList> getFields(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        PlayedBoard board = game.get().getBoard();
        if (board == null)
            return ResponseEntity.notFound().build();
        FieldList fieldList = new FieldList(board.getFieldsOnBoard());
        return ResponseEntity.ok().body(fieldList);
    }

    /**
     * Call to get specific field from fields on board in played game.
     *
     * @param playedGameId
     * @param fieldId
     * @return
     */
    @GetMapping("{playedGameId}/board/fields/{fieldId}")
    public ResponseEntity<Field> getField(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "fieldId") Integer fieldId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        PlayedBoard board = game.get().getBoard();
        if (board == null)
            return ResponseEntity.notFound().build();
        Optional<Field> field = playedGameService.findField(playedGameId, fieldId);
        if (field.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(field.get());
    }

    // CARDS -----------------------------------------------------------------------------------------------------------

    /**
     * Call to get all cards in deck of played game.
     *
     * @param playedGameId
     * @return list of cards
     */
    @GetMapping("{playedGameId}/cards/deck")
    public ResponseEntity<CardList> getCards(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        CardList cardList = new CardList(game.get().getCardDeck());
        return ResponseEntity.ok().body(cardList);
    }

    /**
     * Call to get all cards in used deck of played game.
     *
     * @param playedGameId
     * @return list of cards
     */
    @GetMapping("{playedGameId}/cards/used")
    public ResponseEntity<CardList> getUsedCards(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        CardList cardList = new CardList(game.get().getUsedCardDeck());
        return ResponseEntity.ok().body(cardList);
    }

    /**
     * Call to get card in deck of played game.
     *
     * @param playedGameId
     * @param cardId
     * @return found card
     */
    @GetMapping("{playedGameId}/cards/deck/{cardId}")
    public ResponseEntity<Card> getDeckCard(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find card
        Optional<Card> card = playedGameService.findCardInCardDeck(playedGameId, cardId);
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(card.get());
    }

    /**
     * Call to get card in played game.
     *
     * @param playedGameId
     * @param cardId
     * @return found card
     */
    @GetMapping("{playedGameId}/cards/used/{cardId}")
    public ResponseEntity<Card> getUsedCard(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find card
        Optional<Card> card = playedGameService.findCardInUsedDeck(playedGameId, cardId);
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(card.get());
    }

    /**
     * Call to move card from cardDeck to usedCardDeck.
     * Removes card from cardDeck and adds to usedCardDeck.
     *
     * @param playedGameId
     * @param cardId
     * @return game with updated cards
     */
//    @PutMapping("{playedGameId}/cardToUsed/{cardId}")
    @PutMapping("{playedGameId}/cards/used/{cardId}")
    public ResponseEntity<PlayedGame> moveCardToUsed(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find card
        Optional<Card> card = playedGameService.findCardInCardDeck(playedGameId, cardId);
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        PlayedGame game = playedGameService.moveCardToUsed(gameRequest.get(), card.get());
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to move card from players cards on hand to usedCardDeck.
     * Removes card from players hand and adds to usedCardDeck.
     *
     * @param playedGameId
     * @param playerLogin
     * @param cardId
     * @return game with updated cards
     */
//    @PutMapping("{playedGameId}/players/{playerLogin}/cardToUsed/{cardId}")
    @PutMapping("{playedGameId}/players/{playerLogin}/cards/used/{cardId}")
    public ResponseEntity<PlayedGame> moveCardToUsed(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find card
        Optional<ItemCard> card = playedGameService.findCardInPlayer(playedGameId, playerLogin, cardId);
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();

        PlayedGame game = playedGameService.moveCardFromPlayer(gameRequest.get(), player.get(), card.get());
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to handle item card. Checks if player has place on hand, if so moves the card from deck to player's hand.
     *
     * @param playedGameId
     * @param playerLogin
     * @param cardId
     * @return true if player can take the card, false if not
     */
//    @PutMapping("{playedGameId}/players/{playerLogin}/cardToPlayer/{cardId}")
    @PutMapping("{playedGameId}/players/{playerLogin}/cards/hand/{cardId}")
    public ResponseEntity<Boolean> handleItemCard(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find card
        Optional<Card> card = playedGameService.findCardInCardDeck(playedGameId, cardId);
        if (card.isEmpty() || card.get().getCardType() != CardType.ITEM_CARD)
            return ResponseEntity.notFound().build();
        // check num of cards on hand
        boolean havePlaceOnHand = player.get().getPlayerManager().checkCardsOnHand(player.get());
        if (havePlaceOnHand) {
            // move card to player's hand
            playedGameService.moveCardToPlayer(gameRequest.get(), card.get(), player.get());
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        return ResponseEntity.ok().body(Boolean.FALSE);
    }

    /**
     * Call to move card from cardDeck to trophies of player.
     * Removes card from card deck and adds to trophies.
     *
     * @param playedGameId
     * @param playerLogin
     * @param cardId
     * @return
     */
//    @PutMapping("/{playedGameId}/players/{playerLogin}/cardToTrophies/{cardId}")
    @PutMapping("/{playedGameId}/players/{playerLogin}/cards/trophies/{cardId}")
    public ResponseEntity<PlayedGame> moveCardToTrophies(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty()) {
            System.out.println("no game found");
            return ResponseEntity.notFound().build();
        }
        // find card
        Optional<Card> card = playedGameService.findCardInCardDeck(playedGameId, cardId);
        if (card.isEmpty()) {
            System.out.println("no card found");
            return ResponseEntity.notFound().build();
        }
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty()) {
            System.out.println("no player found");
            return ResponseEntity.notFound().build();
        }
        PlayedGame game = playedGameService.moveCardToTrophies(gameRequest.get(), card.get(), player.get());
        // check trophies
        game = playedGameService.checkTrophies(game, player.get());
        return ResponseEntity.ok().body(game);
    }

    // CHARACTERS ------------------------------------------------------------------------------------------------------

    /**
     * Call to get all characters in played game.
     *
     * @param playedGameId
     * @return list of all characters
     */
    @GetMapping("{playedGameId}/characters")
    public ResponseEntity<CharacterList> getAllCharacters(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        CharacterList characterList = new CharacterList(game.get().getCharactersInGame());
        return ResponseEntity.ok().body(characterList);
    }

    /**
     * Call to get character in played game.
     *
     * @param playedGameId
     * @param characterId
     * @return found character
     */
    @GetMapping("{playedGameId}/characters/{characterId}")
    public ResponseEntity<Character> getCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "characterId") Integer characterId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find character
        Optional<Character> character = playedGameService.findCharacter(playedGameId, characterId);
        if (character.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(character.get());
    }

    // PLAYERS ---------------------------------------------------------------------------------------------------------

    /**
     * Call to get all players in played game.
     *
     * @param playedGameId
     * @return list of all players
     */
    @GetMapping("{playedGameId}/players")
    public ResponseEntity<PlayerList> getPlayers(@PathVariable(name = "playedGameId") String playedGameId) {
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        PlayerList playerList = new PlayerList(game.get().getPlayers());
        return ResponseEntity.ok().body(playerList);
    }

    /**
     * Call to get player in played game.
     *
     * @param playedGameId
     * @param playerLogin
     * @return found player
     */
    @GetMapping("{playedGameId}/players/{playerLogin}")
    public ResponseEntity<Player> getPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(player.get());
    }

    /**
     * Call to get played character of player.
     *
     * @param playedGameId
     * @param playerLogin
     * @return
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/character")
    public ResponseEntity<Character> getCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        Character character = player.get().getCharacter();
        if (character == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(character);
    }

    /**
     * Call to get cards in hand of player in played game.
     *
     * @param playedGameId
     * @param playerLogin
     * @return cards in hand of player
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand")
    public ResponseEntity<ItemCardList> getCards(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find cards
        ItemCardList cardList = new ItemCardList(player.get().getCardsOnHand());
        return ResponseEntity.ok().body(cardList);
    }

    /**
     * Call to get particular card in hand of player in played game.
     *
     * @param playedGameId
     * @param playerLogin
     * @param cardId
     * @return card in hand of player
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/hand/{cardId}")
    public ResponseEntity<ItemCard> getCards(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find card
        Optional<ItemCard> card = playedGameService.findCardInPlayer(playedGameId, playerLogin, cardId);
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(card.get());
    }

    /**
     * Call to get trophies of player in played game.
     *
     * @param playedGameId
     * @param playerLogin
     * @return trophies in hand of player
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/cards/trophies")
    public ResponseEntity<EnemyCardList> getTrophies(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find trophies
        EnemyCardList cardList = new EnemyCardList(player.get().getTrophies());
        return ResponseEntity.ok().body(cardList);
    }

    /**
     * Call to add player to played game.
     * Adds player from Player Service database.
     *
     * @param playedGameId
     * @param playerLogin
     * @return updated game with new players
     */
//    @PutMapping("{playedGameId}/addPlayer/{playerLogin}")
    @PutMapping("{playedGameId}/players/{playerLogin}")
    public ResponseEntity<PlayedGame> addPlayerToGameByLogin(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        // get game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playerService.findByLogin(playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        player.get().setPlayerManager(new PlayerManager());
        PlayedGame game = playedGameService.addPlayer(gameRequest.get(), player.get());
        playerService.addGame(playerLogin, playedGameId);
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to add playedCharacter to player.
     *
     * @param playedGameId
     * @param playerLogin
     * @param characterId
     * @return updated game with character assigned to player
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/character/{characterId}")
    public ResponseEntity<PlayedGame> selectCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "characterId") Integer characterId) {
        // get game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find character
        Optional<Character> character = playedGameService.findCharacter(playedGameId, characterId);
        if (character.isEmpty())
            return ResponseEntity.notFound().build();
        PlayedGame game = playedGameService.setCharacterToPlayer(gameRequest.get(), player.get(), character.get());
        // find field
        Optional<Field> field = playedGameService.findField(playedGameId, character.get().getField().getId());
        if (field.isEmpty())
            return ResponseEntity.notFound().build();
        playedGameService.changePosition(gameRequest.get(), player.get(), character.get(), field.get());
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to update player's position on the board.
     *
     * @param playedGameId
     * @param playerLogin
     * @param fieldId
     * @return type of field
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/field/{fieldId}")
    public ResponseEntity<FieldOptionList> changeFieldPositionOfCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "fieldId") Integer fieldId) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find character
        Character character = player.get().getCharacter();
        if (character == null) {
            return ResponseEntity.notFound().build();
        }
        // find field
        Optional<Field> field = playedGameService.findField(playedGameId, fieldId);
        if (field.isEmpty())
            return ResponseEntity.notFound().build();
        playedGameService.changePosition(gameRequest.get(), player.get(), character, field.get());
        // return possible actions on field
        FieldOptionList optionList = playedGameService.checkField(gameRequest.get(), player.get(), field.get());
        return ResponseEntity.ok().body(optionList);
    }

    /**
     * Call to block player for given number of turns.
     *
     * @param playedGameId
     * @param playerLogin
     * @param blockedNum
     * @return
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/blocked/{blockedNum}")
    public ResponseEntity<Player> blockTurnsOfPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "blockedNum") Integer blockedNum) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        playedGameService.blockTurnsOfPlayer(gameRequest.get(), player.get(), blockedNum);
        return ResponseEntity.ok().body(player.get());
    }

    /**
     * Call to draw random card from card deck of the game.
     * Does not remove the card from card deck.
     *
     * @param playedGameId
     * @return random card
     */
//    @PutMapping("{playedGameId}/players/{playerLogin}/drawCard")
    @GetMapping("{playedGameId}/cards/deck/draw")
    public ResponseEntity<Card> drawRandomCard(@PathVariable(name = "playedGameId") String playedGameId) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
//        // find player
//        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
//        if (player.isEmpty())
//            return ResponseEntity.notFound().build();
        Optional<Card> card = playedGameService.drawCard(gameRequest.get());
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(card.get());
    }


    // FIGHT -----------------------------------------------------------------------------------------------------------

    /**
     * Call to get random Integer score for roll of the dice.
     *
     * @param playedGameId
     * @param playerLogin
     * @return
     */
    @GetMapping("{playedGameId}/players/{playerLogin}/roll")
    public ResponseEntity<Integer> rollDice(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        Integer roll = playedGameService.rollDice();
        return ResponseEntity.ok().body(roll);
    }

    /**
     * Call to get result of fight between Player and Enemy from card.
     * Decreases health points of player and enemy. If enemy killed, adds to trophies of player.
     *
     * @param playedGameId
     * @param playerLogin
     * @param cardId
     * @param playerRoll
     * @param enemyRoll
     * @return true if player won, false if lost
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/roll/{playerRoll}/enemy/{cardId}/roll/{enemyRoll}")
    public ResponseEntity<Boolean> handleFight(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "cardId") Integer cardId, @PathVariable(name = "playerRoll") Integer playerRoll, @PathVariable(name = "enemyRoll") Integer enemyRoll) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        player.get().getPlayerManager().setFightRoll(player.get(), playerRoll);
        // find card
        Optional<Card> card = playedGameService.findCardInCardDeck(playedGameId, cardId);
        if (card.isEmpty() || card.get().getCardType() != CardType.ENEMY_CARD)
            return ResponseEntity.notFound().build();
        boolean fightResult = playedGameService.calculateFight(gameRequest.get(), player.get(), (EnemyCard) card.get(), playerRoll, enemyRoll);
        if (fightResult) { // player won
            playedGameService.decreaseHealth(gameRequest.get(), player.get(), (EnemyCard) card.get(), -1);
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        playedGameService.decreaseHealth(gameRequest.get(), player.get(), -1);
        return ResponseEntity.ok().body(Boolean.FALSE); // player lost
    }

    /**
     * Call to get result of fight between Player and Enemy from field where the player's character stays.
     *
     * @param playedGameId
     * @param playerLogin
     * @param playerRoll
     * @param enemyRoll
     * @return true if player won, false if lost
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/roll/{playerRoll}/enemy/roll/{enemyRoll}")
    public ResponseEntity<Boolean> handleFight(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "playerRoll") Integer playerRoll, @PathVariable(name = "enemyRoll") Integer enemyRoll) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find enemy from field
        Optional<Field> field = playedGameService.findField(playedGameId, player.get().getCharacter().getField().getId());
        EnemyCard enemy = field.get().getEnemy();
        if (enemy == null)
            return ResponseEntity.notFound().build();
        boolean fightResult = playedGameService.calculateFight(gameRequest.get(), player.get(), enemy, playerRoll, enemyRoll);
        if (fightResult) {// player won
            playedGameService.decreaseHealth(gameRequest.get(), player.get(), field.get(), enemy, -1);
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        playedGameService.decreaseHealth(gameRequest.get(), player.get(), -1);
        return ResponseEntity.ok().body(Boolean.FALSE); // player lost
    }

    /**
     * Call to get result of fight between Player and Player. Returns a result only when both players rolled.
     *
     * @param playedGameId
     * @param playerLogin
     * @param playerRoll
     * @return
     */
    @PutMapping("{playedGameId}/players/{playerLogin}/roll/{playerRoll}")
    public ResponseEntity<Boolean> handleFight(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin, @PathVariable(name = "playerRoll") Integer playerRoll) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find player enemy from field
        Field field = player.get().getCharacter().getField();
        if (field == null)
            return ResponseEntity.notFound().build();
        Optional<Player> enemyPlayer = playedGameService.findDifferentPlayerByField(playedGameId, playerLogin, field.getId());
        if (enemyPlayer.isEmpty())
            return ResponseEntity.notFound().build();
        playedGameService.setPlayerFightRoll(gameRequest.get(), player.get(), playerRoll);
        if (enemyPlayer.get().getFightRoll() == 0) { // call from attacker, wait for attacked roll
            return ResponseEntity.notFound().build();
        }
        boolean fightResult = playedGameService.calculateFight(gameRequest.get(), player.get(), enemyPlayer.get(), playerRoll, enemyPlayer.get().getFightRoll());
        playedGameService.setPlayerFightRoll(gameRequest.get(), player.get(), 0);
        playedGameService.setPlayerFightRoll(gameRequest.get(), enemyPlayer.get(), 0);
        if (fightResult) { // attacked (player) won
            playedGameService.decreaseHealth(gameRequest.get(), enemyPlayer.get(), -1);
            return ResponseEntity.ok(Boolean.TRUE);
        }
        playedGameService.decreaseHealth(gameRequest.get(), player.get(), -1);
        return ResponseEntity.ok(Boolean.FALSE); // attacking (enemy) won
    }



}
