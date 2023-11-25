package pl.edu.pg.eti.dragondestiny.graphics.cards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "card_graphics")
public class GraphicCard {

    @Id
    @Column(name = "id")
    private Integer id;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
}
