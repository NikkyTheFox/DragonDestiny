package com.example.played_game;

import com.example.played_game.played_board.PlayedBoard;
import com.example.played_game.played_card.CardType;
import com.example.played_game.played_card.PlayedEnemyCard;
import com.example.played_game.played_card.PlayedItemCard;
import com.example.played_game.played_character.PlayedCharacter;
import com.example.played_game.played_field.PlayedField;
import com.example.played_game.played_game.PlayedGame;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class InitializePlayedGame
{
    public PlayedGame initialize(Integer gameId, Integer playedGameId)
    {
        // GET GAME:
        WebClient client = WebClient.create();

        ResponseEntity<PlayedGame> playedGameResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/" + gameId)
                .retrieve()
                .toEntity(PlayedGame.class)
                .block();

        PlayedGame playedGame = playedGameResponseEntity.getBody();
        playedGame.setId(playedGameId);


        // GET CARDS:
        ResponseEntity<PlayedEnemyCard[]> enemyCardResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/" + gameId + "/cards/enemyCards")
                .retrieve()
                .toEntity(PlayedEnemyCard[].class)
                .block();

        for (PlayedEnemyCard c : enemyCardResponseEntity.getBody())
        {
            c.setCardType(CardType.ENEMY_CARD);
            playedGame.addCardToDeck(c);
        }

        ResponseEntity<PlayedItemCard[]> itemCardResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/" + gameId+ "/cards/itemCards")
                .retrieve()
                .toEntity(PlayedItemCard[].class)
                .block();

        for (PlayedItemCard c : itemCardResponseEntity.getBody())
        {
            c.setCardType(CardType.ITEM_CARD);
            playedGame.addCardToDeck(c);
        }


        // GET CHARACTERS:
        ResponseEntity<PlayedCharacter[]> characterResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/" + gameId + "/characters")
                .retrieve()
                .toEntity(PlayedCharacter[].class)
                .block();

        for (PlayedCharacter c : characterResponseEntity.getBody())
        {
            playedGame.addCharacterToGame(c);
        }


        // GET BOARD:
        ResponseEntity<PlayedBoard> boardResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/"+ gameId +"/boards/1")
                .retrieve()
                .toEntity(PlayedBoard.class)
                .block();

        PlayedBoard playedBoard = boardResponseEntity.getBody();


        // GET FIELDS:
        ResponseEntity<PlayedField[]> fieldResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/"+ gameId + "/boards/1/fields")
                .retrieve()
                .toEntity(PlayedField[].class)
                .block();

        for (PlayedField c : fieldResponseEntity.getBody())
        {
            playedBoard.addFieldsInBoard(c);
        }

        playedGame.setBoard(playedBoard);

        return playedGame;
    }



}
