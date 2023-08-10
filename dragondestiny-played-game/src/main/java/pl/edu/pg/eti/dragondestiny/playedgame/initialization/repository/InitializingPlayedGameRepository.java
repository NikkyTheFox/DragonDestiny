package pl.edu.pg.eti.dragondestiny.playedgame.initialization.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.CharacterList;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldList;
import pl.edu.pg.eti.dragondestiny.playedgame.initialization.DTO.GameEngineGameDTO;

/**
 * This repository class interacts with a remote game engine API to retrieve game data.
 */
@Repository
public class InitializingPlayedGameRepository {

    private final WebClient client;

    /**
     * Constructs the InitializingPlayedGameRepository with a WebClient instance.
     *
     * @param webClientBuilder The builder used to create the WebClient instance.
     */
    @Autowired
    public InitializingPlayedGameRepository(WebClient.Builder webClientBuilder){
        this.client = webClientBuilder.build();
    }

    /**
     * Retrieves the game data for a specified game ID from the game engine API.
     *
     * @param gameEngineGameId An identifier of the game in the game engine.
     * @return The game meant to be retrieved.
     */
    public GameEngineGameDTO getGameById(Integer gameEngineGameId){
        ResponseEntity<GameEngineGameDTO> gameEngineGameDTOResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/{gameId}", gameEngineGameId)
                .retrieve()
                .toEntity(GameEngineGameDTO.class)
                .block();
        if(gameEngineGameDTOResponseEntity == null) {
            return null;
        }
        return gameEngineGameDTOResponseEntity.getBody();
    }

    /**
     * Retrieves a list of enemy cards for a specified game ID from the game engine API.
     *
     * @param gameEngineGameId An identifier of the game in the game engine.
     * @return A structure containing a list of enemy cards.
     */
    public EnemyCardList getGameEnemyCards(Integer gameEngineGameId){
        ResponseEntity<EnemyCardList> enemyCardListResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/{gameId}/cards/enemy", gameEngineGameId)
                .retrieve()
                .toEntity(EnemyCardList.class)
                .block();
        if(enemyCardListResponseEntity == null) {
            return null;
        }
        return enemyCardListResponseEntity.getBody();
    }

    /**
     * Retrieves a list of item cards for a specified game ID from the game engine API.
     *
     * @param gameEngineGameId An identifier of the game in the game engine.
     * @return A structure containing a list of item cards.
     */
    public ItemCardList getGameItemCards(Integer gameEngineGameId){
        ResponseEntity<ItemCardList> itemCardListResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/{gameId}/cards/item", gameEngineGameId)
                .retrieve()
                .toEntity(ItemCardList.class)
                .block();
        if(itemCardListResponseEntity == null) {
            return null;
        }
        return itemCardListResponseEntity.getBody();
    }

    /**
     * Retrieves a list of characters for a specified game ID from the game engine API.
     *
     * @param gameEngineGameId An identifier of the game in the game engine.
     * @return A structure containing list of characters.
     */
    public CharacterList getGameCharacters(Integer gameEngineGameId){
        ResponseEntity<CharacterList> characterListResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/{gameId}/characters", gameEngineGameId)
                .retrieve()
                .toEntity(CharacterList.class)
                .block();
        if(characterListResponseEntity == null) {
            return null;
        }
        return characterListResponseEntity.getBody();
    }

    /**
     * Retrieves played board for a specified game ID from the game engine API.
     *
     * @param gameEngineGameId An identifier of the game in the game engine.
     * @return A retrieved board.
     */
    public PlayedBoard getGamePlayedBoard(Integer gameEngineGameId) {
        ResponseEntity<PlayedBoard> playedBoardResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/{gameId}/board", gameEngineGameId)
                .retrieve()
                .toEntity(PlayedBoard.class)
                .block();
        if(playedBoardResponseEntity == null) {
            return null;
        }
        return playedBoardResponseEntity.getBody();
    }

    /**
     * Retrieves a list of fields for a specified game ID from the game engine API.
     *
     * @param gameEngineGameId An identifier of the game in the game engine.
     * @return A structure containing a list of fields.
     */
    public FieldList getGameFieldList(Integer gameEngineGameId){
        ResponseEntity<FieldList> fieldListResponseEntity = client.get()
                .uri("http://GAME-ENGINE/api/games/{gameId}/board/fields", gameEngineGameId)
                .retrieve()
                .toEntity(FieldList.class)
                .block();
        if(fieldListResponseEntity == null) {
            return null;
        }
        return fieldListResponseEntity.getBody();
    }

    /**
     * Adds game identifier to the table 'games' in user's database.
     *
     * @param gameId An identifier to be added.
     */
    public void addGameToUserDatabase(String gameId){
        client.put()
                .uri("http://GAME-USER/api/users/games/{gameId}", gameId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
