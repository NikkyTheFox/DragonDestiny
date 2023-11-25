package pl.edu.pg.eti.dragondestiny.graphics.characters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.eti.dragondestiny.graphics.characters.entity.GraphicCharacter;

public interface GraphicCharacterRepository extends JpaRepository<GraphicCharacter, Integer> {

}
