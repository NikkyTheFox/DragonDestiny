package com.example.game.game.dto;


import com.example.game.board.dto.BoardDTO;
import com.example.game.card.card.entity.Card;
import com.example.game.field.dto.FieldDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameDTO {
    private Integer id;
    private Integer boardId;

    private List<Card> cardDeck = new ArrayList<>();
//    private List<Card> usedCardDeck = new ArrayList<>();

}
