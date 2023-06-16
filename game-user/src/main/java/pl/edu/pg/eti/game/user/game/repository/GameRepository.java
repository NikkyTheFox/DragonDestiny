package pl.edu.pg.eti.game.user.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.game.user.user.entity.User;
import pl.edu.pg.eti.game.user.game.entity.Game;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {

    /**
     * Method to retrieve all games by user.
     *
     * @param user - user to find all games played
     * @return list of games
     */
    List<Game> findAllByUserList(User user);

}
