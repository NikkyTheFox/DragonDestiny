package pl.edu.pg.eti.dragondestiny.graphics.cards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.eti.dragondestiny.graphics.cards.entity.GraphicCard;

public interface GraphicCardRepository extends JpaRepository<GraphicCard, Integer> {

}
