package pl.edu.pg.eti.game.user.repository;

import pl.edu.pg.eti.game.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
