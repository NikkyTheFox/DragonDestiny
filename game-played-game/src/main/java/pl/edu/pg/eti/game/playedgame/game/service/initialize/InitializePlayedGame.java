package pl.edu.pg.eti.game.playedgame.game.service.initialize;

import pl.edu.pg.eti.game.playedgame.board.entity.PlayedBoard;
import pl.edu.pg.eti.game.playedgame.card.enemycard.response.EnemyCardList;
import pl.edu.pg.eti.game.playedgame.card.entity.CardType;
import pl.edu.pg.eti.game.playedgame.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.game.playedgame.card.itemcard.entity.ItemCard;
import pl.edu.pg.eti.game.playedgame.card.itemcard.response.ItemCardList;
import pl.edu.pg.eti.game.playedgame.character.entity.Character;
import pl.edu.pg.eti.game.playedgame.character.response.CharacterList;
import pl.edu.pg.eti.game.playedgame.field.entity.Field;
import pl.edu.pg.eti.game.playedgame.field.response.FieldList;
import pl.edu.pg.eti.game.playedgame.game.entity.GameManager;
import pl.edu.pg.eti.game.playedgame.game.entity.PlayedGame;
import pl.edu.pg.eti.game.playedgame.game.repository.PlayedGameRepository;
import pl.edu.pg.eti.game.playedgame.game.response.PlayedGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;

@Service
public class InitializePlayedGame {

    private final WebClient.Builder webClientBuilder;

    private final PlayedGameRepository playedGameRepository;

    @Autowired
    public InitializePlayedGame(WebClient.Builder webClientBuilder, PlayedGameRepository playedGameRepository) {
        this.webClientBuilder = webClientBuilder;
        this.playedGameRepository = playedGameRepository;
    }

    public PlayedGame initialize(Integer gameId) {
        // GET GAME:
        WebClient client = webClientBuilder.build();

        ResponseEntity<PlayedGameResponse> playedGameResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/" + gameId)
                .retrieve()
                .toEntity(PlayedGameResponse.class)
                .block();

        PlayedGameResponse playedGameResponse = playedGameResponseEntity.getBody();
        PlayedGame playedGame = new PlayedGame();
        playedGame.setPlayers(playedGameResponse.getPlayers());
        playedGame.setBoard(playedGameResponse.getBoard());
        playedGame.setCardDeck(playedGameResponse.getCardDeck());
        playedGame.setUsedCardDeck(playedGameResponse.getUsedCardDeck());
        playedGame.setCharactersInGame(playedGameResponse.getCharactersInGame());
        GameManager gameManager = new GameManager();
        playedGame.setGameManager(gameManager);

        // GET CARDS:
        ResponseEntity<EnemyCardList> enemyCardResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/" + gameId + "/cards/enemy")
                .retrieve()
                .toEntity(EnemyCardList.class)
                .block();
        for (EnemyCard c : enemyCardResponseEntity.getBody().getEnemyCardList()) {
            EnemyCard card = c;
            card.setCardType(CardType.ENEMY_CARD);
            gameManager.addCardToDeck(playedGame, card);
        }

        ResponseEntity<ItemCardList> itemCardResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/" + gameId+ "/cards/item")
                .retrieve()
                .toEntity(ItemCardList.class)
                .block();

        for (ItemCard c : itemCardResponseEntity.getBody().getItemCardList()) {
            ItemCard card = c;
            card.setCardType(CardType.ITEM_CARD);
            gameManager.addCardToDeck(playedGame, card);
        }

        // GET CHARACTERS:
        ResponseEntity<CharacterList> characterResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/" + gameId + "/characters")
                .retrieve()
                .toEntity(CharacterList.class)
                .block();

        for (Character c : characterResponseEntity.getBody().getCharacterList()) {
            Character character = c;
            gameManager.addCharacterToGame(playedGame, character);
        }

        // GET BOARD:
        ResponseEntity<PlayedBoard> boardResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/"+ gameId +"/board")
                .retrieve()
                .toEntity(PlayedBoard.class)
                .block();

        PlayedBoard playedBoard = boardResponseEntity.getBody();

        // GET FIELDS:
        ResponseEntity<FieldList> fieldResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/"+ gameId + "/board/fields")
                .retrieve()
                .toEntity(FieldList.class)
                .block();

        for (Field c : fieldResponseEntity.getBody().getFieldList()) {
            playedBoard.addFieldsInBoard(c);
        }
        playedGame.setBoard(playedBoard);

        return playedGameRepository.save(playedGame);
    }

}
