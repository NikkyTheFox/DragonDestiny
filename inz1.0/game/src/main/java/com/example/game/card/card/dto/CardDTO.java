package com.example.game.card.card.dto;

import com.example.game.card.card.entity.CardType;
import lombok.Data;

@Data
public class CardDTO {

    Integer id;
    String name;
    String description;
    CardType cardType;
}
