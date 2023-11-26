package pl.edu.pg.eti.dragondestiny.graphics.characters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pg.eti.dragondestiny.graphics.characters.entity.GraphicCharacter;
import pl.edu.pg.eti.dragondestiny.graphics.characters.repository.GraphicCharacterRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class GraphicCharacterService {

    private final GraphicCharacterRepository graphicCharacterRepository;

    @Autowired
    public GraphicCharacterService(GraphicCharacterRepository graphicCharacterRepository) {
        this.graphicCharacterRepository = graphicCharacterRepository;
    }

    public Optional<GraphicCharacter> getGraphic(Integer id) {
        return graphicCharacterRepository.findById(id);
    }

    public GraphicCharacter saveGraphic(Integer id, MultipartFile file) throws IOException {
        byte[] imageData = file.getBytes();
        GraphicCharacter graphic = new GraphicCharacter();
        graphic.setId(id);
        graphic.setImageData(imageData);
        return graphicCharacterRepository.save(graphic);
    }
}
