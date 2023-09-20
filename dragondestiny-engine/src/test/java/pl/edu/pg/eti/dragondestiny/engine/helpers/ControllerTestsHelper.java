package pl.edu.pg.eti.dragondestiny.engine.helpers;

import pl.edu.pg.eti.dragondestiny.engine.board.dto.BoardDTO;
import pl.edu.pg.eti.dragondestiny.engine.board.dto.BoardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.board.entity.BoardList;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.card.entity.CardList;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCardList;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardListDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCardList;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.dto.CharacterListDTO;
import pl.edu.pg.eti.dragondestiny.engine.character.entity.CharacterList;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.dto.FieldListDTO;
import pl.edu.pg.eti.dragondestiny.engine.field.entity.FieldList;
import pl.edu.pg.eti.dragondestiny.engine.game.dto.GameDTO;
import pl.edu.pg.eti.dragondestiny.engine.game.dto.GameListDTO;
import pl.edu.pg.eti.dragondestiny.engine.game.entity.GameList;

import java.util.ArrayList;
import java.util.List;

public class ControllerTestsHelper {

    public static BoardListDTO convertBoardListToDTO(BoardList boardList) {
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardList.getBoardList().forEach(board -> {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(board.getId());
            boardDTO.setXsize(board.getXsize());
            boardDTO.setYsize(board.getYsize());
            boardDTOList.add(boardDTO);
        });
        return new BoardListDTO(boardDTOList);
    }

    public static FieldListDTO convertFieldListToDTO(FieldList fieldList) {
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        fieldList.getFieldList().forEach(field -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setId(field.getId());
            fieldDTO.setType(field.getType());
            if (field.getEnemy() != null) {
                EnemyCardDTO enemyCardDTO = new EnemyCardDTO();
                enemyCardDTO.setId(field.getEnemy().getId());
                enemyCardDTO.setName(field.getEnemy().getName());
                enemyCardDTO.setInitialHealth(field.getEnemy().getInitialHealth());
                enemyCardDTO.setInitialStrength(field.getEnemy().getInitialStrength());
                enemyCardDTO.setDescription(field.getEnemy().getDescription());
                fieldDTO.setEnemy(enemyCardDTO);
            }
            fieldDTO.setXPosition(field.getXPosition());
            fieldDTO.setYPosition(field.getYPosition());
            fieldDTOList.add(fieldDTO);
        });
        return new FieldListDTO(fieldDTOList);
    }

    public static EnemyCardListDTO convertEnemyCardListToDTO(EnemyCardList cardList) {
        List<EnemyCardDTO> cardDTOList = new ArrayList<>();
        cardList.getEnemyCardList().forEach(card -> {
            EnemyCardDTO cardDTO = new EnemyCardDTO();
            cardDTO.setId(card.getId());
            cardDTO.setName(card.getName());
            cardDTO.setDescription(card.getDescription());
            cardDTO.setInitialStrength(card.getInitialStrength());
            cardDTO.setInitialHealth(card.getInitialHealth());
            cardDTOList.add(cardDTO);
        });
        return new EnemyCardListDTO(cardDTOList);
    }

    public static CardListDTO convertCardListToDTO(CardList cardList) {
        List<CardDTO> cardDTOList = new ArrayList<>();
        cardList.getCardList().forEach(card -> {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setId(card.getId());
            cardDTO.setName(card.getName());
            cardDTO.setDescription(card.getDescription());
            cardDTOList.add(cardDTO);
        });
        return new CardListDTO(cardDTOList);
    }

    public static ItemCardListDTO convertItemCardListToDTO(ItemCardList cardList) {
        List<ItemCardDTO> cardDTOList = new ArrayList<>();
        cardList.getItemCardList().forEach(card -> {
            ItemCardDTO cardDTO = new ItemCardDTO();
            cardDTO.setId(card.getId());
            cardDTO.setName(card.getName());
            cardDTO.setDescription(card.getDescription());
            cardDTO.setHealth(card.getHealth());
            cardDTO.setStrength(card.getStrength());
            cardDTOList.add(cardDTO);
        });
        return new ItemCardListDTO(cardDTOList);
    }

    public static CharacterListDTO convertCharacterListToDTO(CharacterList cardList) {
        List<CharacterDTO> characterDTOList = new ArrayList<>();
        cardList.getCharacterList().forEach(card -> {
            CharacterDTO cardDTO = new CharacterDTO();
            cardDTO.setId(card.getId());
            cardDTO.setName(card.getName());
            if (card.getField() != null) {
                FieldDTO fieldDTO = new FieldDTO();
                fieldDTO.setId(card.getField().getId());
                fieldDTO.setType(card.getField().getType());
                if (card.getField().getEnemy() != null) {
                    EnemyCardDTO enemyCardDTO = new EnemyCardDTO();
                    enemyCardDTO.setId(card.getField().getEnemy().getId());
                    enemyCardDTO.setDescription(card.getField().getEnemy().getDescription());
                    enemyCardDTO.setName(card.getField().getEnemy().getName());
                    enemyCardDTO.setInitialStrength(card.getField().getEnemy().getInitialStrength());
                    enemyCardDTO.setInitialHealth(card.getField().getEnemy().getInitialHealth());
                    fieldDTO.setEnemy(enemyCardDTO);
                }
                fieldDTO.setYPosition(card.getField().getYPosition());
                fieldDTO.setXPosition(card.getField().getXPosition());
                cardDTO.setField(fieldDTO);
            }
            cardDTO.setInitialHealth(card.getInitialHealth());
            cardDTO.setInitialStrength(card.getInitialStrength());
            cardDTO.setProfession(card.getProfession());
            cardDTO.setStory(card.getStory());
            characterDTOList.add(cardDTO);
        });
        return new CharacterListDTO(characterDTOList);
    }

    public static GameListDTO convertGameListToDTO(GameList gameList) {
        List<GameDTO> gameDTOList = new ArrayList<>();
        gameList.getGameList().forEach(game -> {
            GameDTO gameDTO = new GameDTO();
            gameDTO.setId(game.getId());
            gameDTOList.add(gameDTO);
        });
        return new GameListDTO(gameDTOList);
    }
}
