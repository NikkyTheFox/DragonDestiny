package com.example.played_game;

import com.example.played_game.played_card.PlayedCard;
import com.example.played_game.played_character.PlayedCharacter;
import com.example.played_game.played_field.PlayedField;
import com.example.played_game.played_game.PlayedGame;
import com.example.played_game.played_game.PlayedGameService;
import com.example.played_game.playing_player.PlayingPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playedgames")
public class PlayedGameController
{
    private PlayedGameService playedGameService;

    @Autowired
    PlayedGameController(PlayedGameService playedGameService)
    {
        this.playedGameService = playedGameService;
    }

    // GAME ------------------------------------------------------
    /**
     * Call to get played game with id playedGameId
     * @param playedGameId
     * @return PlayedGame
     */
    @GetMapping("/{playedGameId}")
    public ResponseEntity<PlayedGame> getGame(@PathVariable(name = "playedGameId") Integer playedGameId)
    {
        PlayedGame game = playedGameService.findById(playedGameId);
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to get all played games
     * @return list of all games
     */
    @GetMapping("")
    public List<PlayedGame> getAllGames()
    {
        return playedGameService.findAll();
    }

    /**
     * Call to initialize new played game from general games with id gameId.
     * Uses communication with game engine to fetch game with id gameId.
     * @param gameId
     * @return initialized game
     */
    @PostMapping("{gameId}")
    public ResponseEntity<PlayedGame> initializeGame(@PathVariable(name = "gameId") Integer gameId)
    {
        InitializePlayedGame initializer = new InitializePlayedGame();
        PlayedGame playedGameRequest = initializer.initialize(gameId, PlayedGameApplication.numOfPlayedGames++);
        PlayedGame game = playedGameService.save(playedGameRequest);
        PlayedGameApplication.playedGames.add(game);
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to create new played game from request.
     * @param playedGameRequest
     * @return created game
     */
    @PostMapping
    public ResponseEntity<PlayedGame> createGame(@RequestBody PlayedGame playedGameRequest)
    {
        PlayedGame game = playedGameService.save(playedGameRequest);
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to move card from cardDeck to usedCardDeck.
     * Removes card from cardDeck and adds to usedCardDeck.
     * @param playedGameId
     * @param cardId
     * @return game with updated cards
     */
    @PutMapping("{playedGameId}/cardToUsed")
    public ResponseEntity<PlayedGame> moveCardToUsed(@PathVariable(name = "playedGameId") Integer playedGameId, @RequestParam Integer cardId)
    {
        PlayedGame gameRequest = playedGameService.findById(playedGameId);
        PlayedCard cardToMove = null;
        for (PlayedCard c : gameRequest.getCardDeck())
        {
            if (c.getId() == cardId)
                cardToMove = c;
        }
        if (cardToMove == null)
            return ResponseEntity.notFound().build();

        gameRequest.addCardToUsedDeck(cardToMove);
        gameRequest.removeCardFromDeck(cardToMove);

        PlayedGame game = playedGameService.update(playedGameId, gameRequest);
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to move card for cardDeck to cardsOnHand of player
     * @param playedGameId
     * @param cardId
     * @param playerId
     * @return game with updated cards
     */
    @PutMapping("{playedGameId}/cardToPlayer")
    public ResponseEntity<PlayedGame> moveCardToPlayer(@PathVariable(name = "playedGameId") Integer playedGameId, @RequestParam Integer cardId, @RequestParam Integer playerId)
    {
        PlayedGame gameRequest = playedGameService.findById(playedGameId);
        // find card
        PlayedCard cardToMove = null;
        for (PlayedCard c : gameRequest.getCardDeck())
        {
            if (c.getId() == cardId)
                cardToMove = c;
        }
        if (cardToMove == null)
            return ResponseEntity.notFound().build();
        // find player
        PlayingPlayer player = null;
        for (PlayingPlayer p : gameRequest.getPlayingPlayers())
        {
            if (p.getId() == playerId)
                player = p;
        }
        if (player == null)
            return ResponseEntity.notFound().build();

        gameRequest.removeCardFromDeck(cardToMove);
        player.addCardToPlayer(cardToMove);

        PlayedGame game = playedGameService.update(playedGameId, gameRequest);
        return ResponseEntity.ok().body(game);
    }

    // CHARACTERS -------------------------------------------------------
    /**
     * Call to get all characters in played game.
     * @param playedGameId
     * @return list of all characters
     */
    @GetMapping("{playedGameId}/characters")
    public List<PlayedCharacter> getAllCharacters(@PathVariable(name = "playedGameId") Integer playedGameId)
    {
        PlayedGame game = playedGameService.findById(playedGameId);
        return game.getCharactersInGame();
    }

    /**
     * Call to get character in played game.
     * @param playedGameId
     * @param characterId
     * @return found character
     */
    @GetMapping("{playedGameId}/characters/{characterId}")
    public ResponseEntity<PlayedCharacter> getCharacter(@PathVariable(name = "playedGameId") Integer playedGameId, @PathVariable(name = "characterId") Integer characterId)
    {
        PlayedGame game = playedGameService.findById(playedGameId);
        for (PlayedCharacter p : game.getCharactersInGame())
        {
            if (p.getId() == characterId)
                return ResponseEntity.ok().body(p);
        }
        return ResponseEntity.notFound().build();
    }

    // PLAYER -------------------------------------------------------------

    /**
     * Call to add player to played game.
     * For now by body; later by ID and by ID will be found in players database and added to played game
     * check in database if exists then add
     * @param playedGameId
     * @param playerToAdd
     * @return updated game with new players
     */
    @PutMapping("{playedGameId}/addPlayer")
    public ResponseEntity<PlayedGame> addPlayerToGame(@PathVariable(name = "playedGameId") Integer playedGameId, @RequestBody PlayingPlayer playerToAdd)
    {
        PlayedGame gameRequest = playedGameService.findById(playedGameId);
        gameRequest.addPlayerToGame(playerToAdd);
        PlayedGame game = playedGameService.update(playedGameId, gameRequest);
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to add playedCharacter to player.
     * @param playedGameId
     * @param playerId
     * @param characterId
     * @return updated game with character assigned to player
     */
    @PutMapping("{playedGameId}/players/{playerId}/selectCharacter")
    public ResponseEntity<PlayedGame> selectCharacter(@PathVariable(name = "playedGameId") Integer playedGameId, @PathVariable(name = "playerId") Integer playerId, @RequestParam Integer characterId)
    {
        PlayedGame gameRequest = playedGameService.findById(playedGameId);
        // find player
        PlayingPlayer player = null;
        for (PlayingPlayer p : gameRequest.getPlayingPlayers())
        {
            if (p.getId() == playerId)
                player = p;
        }
        if (player == null)
            return ResponseEntity.notFound().build();
        // find character
        PlayedCharacter character = null;
        for (PlayedCharacter p : gameRequest.getCharactersInGame())
        {
            if (p.getId() == characterId)
                character = p;
        }
        if (character == null)
            return ResponseEntity.notFound().build();

        player.setPlayedCharacter(character);
        PlayedGame game = playedGameService.update(playedGameId, gameRequest);
        return ResponseEntity.ok().body(game);
    }

    /**
     * Call to update player's position on the board.
     * Takes fieldId as Request Parameter.
     * @param playedGameId
     * @param playerId
     * @param fieldId
     * @return updated game
     */
    @PutMapping("{playedGameId}/players/{playerId}/changeField")
    public ResponseEntity<PlayedGame> changeFieldPositionOfCharacter(@PathVariable(name = "playedGameId") Integer playedGameId, @PathVariable(name = "playerId") Integer playerId, @RequestParam Integer fieldId)
    {
        PlayedGame gameRequest = playedGameService.findById(playedGameId);
        // find player
        PlayingPlayer player = null;
        for (PlayingPlayer p : gameRequest.getPlayingPlayers())
        {
            if (p.getId() == playerId)
                player = p;
        }
        if (player == null)
            return ResponseEntity.notFound().build();

        // find field
        PlayedField fieldToMove = null;
        for (PlayedField f : gameRequest.getBoard().getFieldsOnBoard())
        {
            if (f.getId() == fieldId)
                fieldToMove = f;
        }
        if (fieldToMove == null)
            return ResponseEntity.notFound().build();

        player.changeCharacterPosition(fieldToMove);
        PlayedGame game = playedGameService.update(playedGameId, gameRequest);
        return ResponseEntity.ok().body(game);
    }
}

//    @GetMapping("{playedGameId}")
//    public PlayedGame getGame(@PathVariable(name = "playedGameId") Integer playedGameId)
//    {
//        PlayedGame temp = null;
//        for (PlayedGame g : PlayedGameApplication.playedGames)
//        {
//            if (g.getId() == playedGameId)
//                temp = g;
//        }
//        if (temp == null)
//            return null;
//        return temp;
//    }
