package pl.edu.pg.eti.dragondestiny.graphics.characters.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pg.eti.dragondestiny.graphics.characters.entity.GraphicCharacter;
import pl.edu.pg.eti.dragondestiny.graphics.characters.service.GraphicCharacterService;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/graphics/characters")
public class GraphicCharacterController {

    private final GraphicCharacterService graphicCharacterService;

    @Autowired
    public GraphicCharacterController(GraphicCharacterService graphicCharacterService) {
        this.graphicCharacterService = graphicCharacterService;
    }

    @GetMapping(value = "{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getGraphicCharacters(@PathVariable(name = "id") Integer id) {
        Optional<GraphicCharacter> graphic = graphicCharacterService.getGraphic(id);
        return graphic.map(value -> ResponseEntity.ok().body(graphic.get().getImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("{id}")
    public ResponseEntity<GraphicCharacter> uploadGraphicCharacters(@PathVariable(name = "id") Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        GraphicCharacter savedGraphicCard = graphicCharacterService.saveGraphic(id, file);
        return ResponseEntity.ok(savedGraphicCard);
    }
}
