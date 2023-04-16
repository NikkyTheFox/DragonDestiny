package com.example.game.card.itemcard.dto;

import com.example.game.card.card.dto.CardDTO;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ItemCardDTO extends CardDTO {
    Integer additionalStrength;
    Integer additionalHealth;
}
