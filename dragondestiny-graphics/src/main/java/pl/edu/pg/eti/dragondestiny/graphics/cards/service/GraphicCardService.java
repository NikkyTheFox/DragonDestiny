package pl.edu.pg.eti.dragondestiny.graphics.cards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pg.eti.dragondestiny.graphics.cards.entity.GraphicCard;
import pl.edu.pg.eti.dragondestiny.graphics.cards.repository.GraphicCardRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class GraphicCardService {

    private final GraphicCardRepository graphicCardRepository;

    @Autowired
    public GraphicCardService(GraphicCardRepository graphicCardRepository) {
        this.graphicCardRepository = graphicCardRepository;
    }

    public Optional<GraphicCard> getGraphic(Integer id) {
        return graphicCardRepository.findById(id);
    }

    public GraphicCard saveGraphic(Integer id, MultipartFile file) throws IOException {
        byte[] imageData = file.getBytes();
        GraphicCard graphic = new GraphicCard();
        graphic.setId(id);
        graphic.setImageData(imageData);
        return graphicCardRepository.save(graphic);
    }
}
