package pl.edu.pg.eti.dragondestiny.playedgame.initialization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.dragondestiny.playedgame.board.object.PlayedBoard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.object.CardType;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCardList;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.Character;
import pl.edu.pg.eti.dragondestiny.playedgame.character.object.CharacterList;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.Field;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldList;
import pl.edu.pg.eti.dragondestiny.playedgame.initialization.DTO.GameEngineGameDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.initialization.repository.InitializingPlayedGameRepository;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object.PlayedGame;
import pl.edu.pg.eti.dragondestiny.playedgame.playedgame.repository.PlayedGameRepository;

import java.util.Optional;

/**
 * A service responsible for initializing played game based on data retrieved from game engine.
 */
@Service
public class InitializingPlayedGameService {

    /**
     * MongoRepository communicating with database.
     */
    private final PlayedGameRepository playedGameRepository;

    /**
     * Repository communicating with database.
     */
    private final InitializingPlayedGameRepository initializingPlayedGameRepository;

    /**
     * A constructor to initialize InitializingPlayedGameService with PlayedGameRepository and InitializingPlayedGameRepository
     *
     * @param playedGameRepository             A repository with methods to handle played game data.
     * @param initializingPlayedGameRepository A repository with methods to retrieve data from game engine.
     */
    @Autowired
    public InitializingPlayedGameService(PlayedGameRepository playedGameRepository, InitializingPlayedGameRepository initializingPlayedGameRepository) {
        this.playedGameRepository = playedGameRepository;
        this.initializingPlayedGameRepository = initializingPlayedGameRepository;
    }

    /**
     * Initializes new played game.
     *
     * @param gameEngineGameId A scenario ID corresponding to game ID in game engine.
     * @return New, initialized and saved played game.
     */
    public Optional<PlayedGame> initialize(Integer gameEngineGameId) {
        // GET GAME:
        GameEngineGameDTO playedGameResponse = initializingPlayedGameRepository.getGameById(gameEngineGameId);
        if (playedGameResponse == null) {
            return Optional.empty();
        }

        PlayedGame playedGame = new PlayedGame();
        playedGame.setPlayers(playedGameResponse.getPlayers());
        playedGame.setBoard(playedGameResponse.getBoard());
        playedGame.setCardDeck(playedGameResponse.getCardDeck());
        playedGame.setUsedCardDeck(playedGameResponse.getUsedCardDeck());
        playedGame.setCharactersInGame(playedGameResponse.getCharactersInGame());

        // GET CARDS:
        EnemyCardList enemyCardList = initializingPlayedGameRepository.getGameEnemyCards(gameEngineGameId);
        if (enemyCardList == null) {
            return Optional.empty();
        }
        for (EnemyCard c : enemyCardList.getEnemyCardList()) {
            c.setCardType(CardType.ENEMY_CARD);
            c.setHealth(c.getInitialHealth());
            playedGame.addCardToDeck(c);
        }

        ItemCardList itemCardList = initializingPlayedGameRepository.getGameItemCards(gameEngineGameId);
        if (itemCardList == null) {
            return Optional.empty();
        }
        for (ItemCard c : itemCardList.getItemCardList()) {
            c.setCardType(CardType.ITEM_CARD);
            playedGame.addCardToDeck(c);
        }

        // GET CHARACTERS:
        CharacterList characterList = initializingPlayedGameRepository.getGameCharacters(gameEngineGameId);
        if (characterList == null) {
            return Optional.empty();
        }
        for (Character c : characterList.getCharacterList()) {
            c.setStrength(c.getInitialStrength());
            c.setHealth(c.getInitialHealth());
            playedGame.addCharacterToGame(c);
        }

        // GET BOARD:
        PlayedBoard playedBoard = initializingPlayedGameRepository.getGamePlayedBoard(gameEngineGameId);
        if (playedBoard == null) {
            return Optional.empty();
        }

        // GET FIELDS:
        FieldList fieldList = initializingPlayedGameRepository.getGameFieldList(gameEngineGameId);
        if (fieldList == null) {
            return Optional.empty();
        }
        for (Field c : fieldList.getFieldList()) {
            if (c.getEnemy() != null) {
                c.getEnemy().setCardType(CardType.ENEMY_CARD);
                c.getEnemy().setHealth(c.getEnemy().getInitialHealth());
            }
            playedBoard.addFieldsInBoard(c);
        }
        playedGame.setBoard(playedBoard);

        PlayedGame playedGame1 = playedGameRepository.save(playedGame);
        initializingPlayedGameRepository.addGameToUserDatabase(playedGame1.getId());
        return Optional.of(playedGame1);
    }

}
