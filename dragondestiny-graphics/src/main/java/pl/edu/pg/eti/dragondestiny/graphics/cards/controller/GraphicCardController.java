package pl.edu.pg.eti.dragondestiny.graphics.cards.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pg.eti.dragondestiny.graphics.cards.entity.GraphicCard;
import pl.edu.pg.eti.dragondestiny.graphics.cards.service.GraphicCardService;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/graphics/cards")
public class GraphicCardController {
    private final GraphicCardService graphicCardService;

    @Autowired
    public GraphicCardController(GraphicCardService graphicCardService) {

        this.graphicCardService = graphicCardService;
    }

    @GetMapping(value = "{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getGraphicCards(@PathVariable(name = "id") Integer id) {
        Optional<GraphicCard> graphic = graphicCardService.getGraphic(id);
        return graphic.map(value -> ResponseEntity.ok().body(graphic.get().getImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("{id}")
    public ResponseEntity<GraphicCard> uploadGraphicCards(@PathVariable(name = "id") Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        GraphicCard savedGraphicCard = graphicCardService.saveGraphic(id, file);
        return ResponseEntity.ok(savedGraphicCard);
    }
}
