package pl.edu.pg.eti.game.user.repository;

import pl.edu.pg.eti.game.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Method to retrieve user by login and password combination.
     *
     * @param login
     * @param password
     * @return
     */
    Optional<User> findUserByLoginAndPassword(String login, String password);


}
