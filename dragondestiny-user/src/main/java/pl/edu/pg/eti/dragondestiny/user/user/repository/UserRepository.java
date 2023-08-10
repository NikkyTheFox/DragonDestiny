package pl.edu.pg.eti.dragondestiny.user.user.repository;

import pl.edu.pg.eti.dragondestiny.user.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository with methods for retrieval and manipulation of data in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Method to retrieve user by login and password combination.
     *
     * @param userLogin An identifier of a user.
     * @param password A password to be checked.
     * @return A retrieved user..
     */
    Optional<User> findUserByLoginAndPassword(String userLogin, String password);

}
