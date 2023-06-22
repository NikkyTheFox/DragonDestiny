package pl.edu.pg.eti.game.playedgame.field;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class FieldOptionList {

    private List<FieldOption> possibleOptions = new ArrayList<>();

}
