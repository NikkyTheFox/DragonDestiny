package pl.edu.pg.eti.game.playedgame.game.controller;

import org.springframework.web.ErrorResponse;
import pl.edu.pg.eti.game.playedgame.card.enemycard.response.EnemyCardList;
import pl.edu.pg.eti.game.playedgame.card.entity.Card;
import pl.edu.pg.eti.game.playedgame.card.entity.CardList;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.card.itemcard.response.ItemCardList;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.character.response.CharacterList;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGame;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGameList;
import pl.edu.pg.eti.game.playedgame.game.service.PlayedGameService;
import pl.edu.pg.eti.game.playedgame.game.service.initialize.InitializePlayedGame;
import pl.edu.pg.eti.game.playedgame.player.response.PlayerList;
import pl.edu.pg.eti.game.playedgame.player.entity.PlayerManager;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.game.playedgame.player.service.PlayerService;

import java.util.Optional;

@RestController
@RequestMapping("/api/playedgames")
public class PlayedGameController {

    private PlayedGameService playedGameService;

    /**
     * Player service to get players from database.
     */
    private PlayerService playerService;

    private InitializePlayedGame initializePlayedGame;

    @Autowired
    public PlayedGameController(PlayedGameService playedGameService, PlayerService playerService, InitializePlayedGame initializePlayedGame) {
        this.playedGameService = playedGameService;
        this.playerService = playerService;
        this.initializePlayedGame = initializePlayedGame;
    }

    // TEST - ignore it
    @GetMapping("/{playedGameId}/players/{playerId}/test")
    public ResponseEntity<Player> testGame(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerId") String playerId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
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
     * Call to create new played game from request.
     *
     * @param playedGameRequest
     * @return created game
     */
    @PostMapping
    public ResponseEntity<PlayedGame> createGame(@RequestBody PlayedGame playedGameRequest) {
        PlayedGame game = playedGameService.save(playedGameRequest);
        return ResponseEntity.ok().body(game);
    }

    // CARDS ---------------------------------------

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
    @PutMapping("{playedGameId}/cardToUsed/{cardId}")
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
     * Call to move card from cardDeck to cardsOnHand of player.
     *
     * @param playedGameId
     * @param cardId
     * @param playerId
     * @return game with updated cards
     */
    @PutMapping("{playedGameId}/players/{playerId}/cardToPlayer/{cardId}")
    public ResponseEntity<PlayedGame> moveCardToPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "cardId") Integer cardId, @PathVariable(name = "playerId") String playerId) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find card
        Optional<Card> card = playedGameService.findCardInCardDeck(playedGameId, cardId);
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        PlayedGame game = playedGameService.moveCardToPlayer(gameRequest.get(), card.get(), player.get());
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to move card from cardDeck to trophies of player.
     *
     * @param playedGameId
     * @param playerId
     * @param cardId
     * @return
     */
    @PutMapping("/{playedGameId}/players/{playerId}/cardToTrophies/{cardId}")
    public ResponseEntity<PlayedGame> moveCardToTrophies(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerId") String playerId, @PathVariable(name = "cardId") Integer cardId) {
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
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
        if (player.isEmpty()) {
            System.out.println("no player found");
            return ResponseEntity.notFound().build();
        }
        PlayedGame game = playedGameService.moveCardToTrophies(gameRequest.get(), card.get(), player.get());
        // check trophies
        game = playedGameService.checkTrophies(game, player.get());
        return ResponseEntity.ok().body(game);
    }

    // CHARACTERS -------------------------------------------------------

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

    // PLAYERS -------------------------------------------------------------

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
     * @param playerId
     * @return found player
     */
    @GetMapping("{playedGameId}/players/{playerId}")
    public ResponseEntity<Player> getPlayer(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerId") String playerId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(player.get());
    }

    @GetMapping("{playedGameId}/players/{playerId}/character")
    public ResponseEntity<Character> getCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerId") String playerId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
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
     * @param playerId
     * @return cards in hand of player
     */
    @GetMapping("{playedGameId}/players/{playerId}/cards")
    public ResponseEntity<ItemCardList> getCards(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerId") String playerId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
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
     * @param playerId
     * @param cardId
     * @return card in hand of player
     */
    @GetMapping("{playedGameId}/players/{playerId}/cards/{cardId}")
    public ResponseEntity<ItemCard> getCards(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerId") String playerId, @PathVariable(name = "cardId") Integer cardId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find card
        Optional<ItemCard> card = playedGameService.findCardInPlayer(playedGameId, playerId, cardId);
        if (card.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(card.get());
    }

    /**
     * Call to get trophies of player in played game.
     *
     * @param playedGameId
     * @param playerId
     * @return trophies in hand of player
     */
    @GetMapping("{playedGameId}/players/{playerId}/trophies")
    public ResponseEntity<EnemyCardList> getTrophies(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerId") String playerId) {
        // find game
        Optional<PlayedGame> game = playedGameService.findPlayedGame(playedGameId);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find trophies
        EnemyCardList cardList = new EnemyCardList(player.get().getTrophies());
        return ResponseEntity.ok().body(cardList);
    }

    /**
     * Call to add player to played game.
     * For now by body; later by ID and by ID will be found in players database and added to played game
     * check in database if exists then add
     *
     * @param playedGameId
     * @param playerLogin
     * @return updated game with new players
     */
    @PutMapping("{playedGameId}/addPlayer/{playerLogin}")
    public ResponseEntity<PlayedGame> addPlayerToGameByLogin(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerLogin") String playerLogin) {
        // get game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        System.out.println("Looking for player " + playerLogin);
        Optional<Player> player = playerService.findByLogin(playerLogin);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        //Player player = playerToAdd;
        player.get().setPlayerManager(new PlayerManager());
        System.out.println("ADDING PLAYER: " + player.hashCode());
        PlayedGame game = playedGameService.addPlayer(gameRequest.get(), player.get());
        playerService.addGame(playerLogin, playedGameId);
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to add playedCharacter to player.
     *
     * @param playedGameId
     * @param playerId
     * @param characterId
     * @return updated game with character assigned to player
     */
    @PutMapping("{playedGameId}/players/{playerId}/character/{characterId}")
    public ResponseEntity<PlayedGame> selectCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerId") String playerId, @PathVariable(name = "characterId") Integer characterId) {
        // get game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find character
        Optional<Character> character = playedGameService.findCharacter(playedGameId, characterId);
        if (character.isEmpty())
            return ResponseEntity.notFound().build();
        PlayedGame game = playedGameService.setCharacterToPlayer(gameRequest.get(), player.get(), character.get());
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to update player's position on the board.
     *
     * @param playedGameId
     * @param playerId
     * @param fieldId
     * @return updated game
     */
    @PutMapping("{playedGameId}/players/{playerId}/character/field/{fieldId}")
    public ResponseEntity<PlayedGame> changeFieldPositionOfCharacter(@PathVariable(name = "playedGameId") String playedGameId, @PathVariable(name = "playerId") String playerId, @PathVariable(name = "fieldId") Integer fieldId) {
        // find game
        Optional<PlayedGame> gameRequest = playedGameService.findPlayedGame(playedGameId);
        if (gameRequest.isEmpty())
            return ResponseEntity.notFound().build();
        // find player
        Optional<Player> player = playedGameService.findPlayer(playedGameId, playerId);
        if (player.isEmpty())
            return ResponseEntity.notFound().build();
        // find character
        Character character = player.get().getCharacter();
        if (character == null) {
            return ResponseEntity.notFound().build();
        }
        //Optional<Character> character = playedGameService.findCharacter(playedGameId, characterId);
        //if (character.isEmpty())
         //   return ResponseEntity.notFound().build();
        // find field
        Optional<Field> field = playedGameService.findField(playedGameId, fieldId);
        if (field.isEmpty())
            return ResponseEntity.notFound().build();
        PlayedGame game = playedGameService.changePosition(gameRequest.get(), player.get(), character, field.get());
        return ResponseEntity.ok().body(game);
    }

}
