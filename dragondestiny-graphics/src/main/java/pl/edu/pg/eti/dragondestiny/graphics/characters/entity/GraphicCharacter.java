package pl.edu.pg.eti.dragondestiny.graphics.characters.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "character_graphics")
public class GraphicCharacter {

    @Id
    @Column(name = "id")
    private Integer id;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
}
