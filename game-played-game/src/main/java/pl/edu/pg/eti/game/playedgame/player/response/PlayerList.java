package pl.edu.pg.eti.game.playedgame.player.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.game.playedgame.player.entity.Player;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerList {

    private List<Player> playerList;

}
