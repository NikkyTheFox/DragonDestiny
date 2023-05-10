package com.example.played_game;

import com.example.played_game.played_board.PlayedBoard;
import com.example.played_game.played_card.CardType;
import com.example.played_game.played_card.PlayedCard;
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

        System.out.println("-----------------------------------------");
        System.out.println(playedGameResponseEntity.getBody().getId());
        System.out.println("-----------------------------------------");
        PlayedGame playedGame = playedGameResponseEntity.getBody();
        playedGame.setId(playedGameId);


        // GET CARDS:
        ResponseEntity<PlayedEnemyCard[]> enemyCardResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/" + gameId + "/cards/enemycards")
                .retrieve()
                .toEntity(PlayedEnemyCard[].class)
                .block();

        System.out.println("-----------------------------------------");
        for (PlayedEnemyCard c : enemyCardResponseEntity.getBody())
        {
            System.out.println(c.getId());
            c.setCardType(CardType.ENEMY_CARD);
            playedGame.addCardToDeck(c);
        }
        System.out.println("-----------------------------------------");

        ResponseEntity<PlayedItemCard[]> itemCardResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/" + gameId+ "/cards/itemcards")
                .retrieve()
                .toEntity(PlayedItemCard[].class)
                .block();

        System.out.println("-----------------------------------------");
        for (PlayedItemCard c : itemCardResponseEntity.getBody())
        {
            System.out.println(c.getId());
            c.setCardType(CardType.ITEM_CARD);
            playedGame.addCardToDeck(c);
        }
        System.out.println("-----------------------------------------");


        // GET CHARACTERS:
        ResponseEntity<PlayedCharacter[]> characterResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/" + gameId + "/characters")
                .retrieve()
                .toEntity(PlayedCharacter[].class)
                .block();

        System.out.println("-----------------------------------------");
        for (PlayedCharacter c : characterResponseEntity.getBody())
        {
            System.out.println(c.getId());
            playedGame.addCharacterToGame(c);
        }
        System.out.println("-----------------------------------------");


        // GET BOARD:
        ResponseEntity<PlayedBoard> boardResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/"+ gameId +"/board/1")
                .retrieve()
                .toEntity(PlayedBoard.class)
                .block();

        System.out.println("-----------------------------------------");
        PlayedBoard playedBoard = boardResponseEntity.getBody();
        System.out.println("-----------------------------------------");


        // GET FIELDS:
        ResponseEntity<PlayedField[]> fieldResponseEntity = client.get()
                .uri("http://localhost:8080/api/games/"+ gameId + "/board/1/fields")
                .retrieve()
                .toEntity(PlayedField[].class)
                .block();

        System.out.println("-----------------------------------------");
        for (PlayedField c : fieldResponseEntity.getBody())
        {
            System.out.println(c.getFieldType());
            playedBoard.addFieldsInBoard(c);
        }
        System.out.println("-----------------------------------------");

        playedGame.setBoard(playedBoard);

        System.out.println("BIG TESTING _------------------------------------------------------------");
        for (PlayedCharacter c : playedGame.getCharactersInGame())
        {
            System.out.println(c.getId() + " " + c.getInitialHealth() + " " + c.getInitialStrength());
        }
        for (PlayedCard c : playedGame.getCardDeck())
        {
            System.out.println(c.getId() + " " + c.getCardType());
            if (c.getCardType() == CardType.ENEMY_CARD)
            {
                PlayedEnemyCard c2 = (PlayedEnemyCard) c;
                System.out.println("ENEMY : " + c2.getInitialHealth() + " " + c2.getInitialStrength());
            }
            else if (c.getCardType() == CardType.ITEM_CARD)
            {
                PlayedItemCard c3 = (PlayedItemCard) c;
                System.out.println("ITEM : " + c3.getAdditionalHealth() + " " + c3.getAdditionalStrength());
            }
        }

        System.out.println(playedGame.getBoard().getId());
        System.out.println(playedBoard.getId());

        for (PlayedField f : playedGame.getBoard().getFieldsOnBoard())
        {
            System.out.println(f.getFieldType() + " " + f.getXPosition() + " " + f.getYPosition());
        }


        return playedGame;
    }



}
