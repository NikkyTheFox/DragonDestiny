package pl.edu.pg.eti.dragondestiny.engine;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pg.eti.dragondestiny.engine.card.card.dto.CardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.dto.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.enemycard.entity.EnemyCard;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.dto.ItemCardDTO;
import pl.edu.pg.eti.dragondestiny.engine.card.itemcard.entity.ItemCard;

@Configuration
public class GameEngineConfig {

    /**
     * Model mapper used to map entities to Data Transfer Objects (DTO).
     *
     * @return A modelMapper for the game.
     */
    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        // Customize the modelMapper configuration
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(EnemyCard.class, EnemyCardDTO.class).include(CardDTO.class);
        modelMapper.typeMap(ItemCard.class, ItemCardDTO.class).include(CardDTO.class);

        return modelMapper;
    }

}