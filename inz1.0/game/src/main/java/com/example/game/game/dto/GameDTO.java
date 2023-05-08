package com.example.game.game.dto;


import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Data
public class GameDTO {
    private Integer id;
    private Integer boardId;
    private Integer numOfCards;

}
