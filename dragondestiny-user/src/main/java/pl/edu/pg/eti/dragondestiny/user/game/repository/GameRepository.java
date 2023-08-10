package pl.edu.pg.eti.dragondestiny.user.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {

    /**
     * Retrieves all games by specified user.
     *
     * @param user The user for which games are to be found.
     * @return A list of games.
     */
    List<Game> findAllByUserList(User user);

}
