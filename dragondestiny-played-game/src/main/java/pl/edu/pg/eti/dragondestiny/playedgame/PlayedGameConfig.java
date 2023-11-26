package pl.edu.pg.eti.dragondestiny.playedgame;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.card.DTO.CardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.DTO.EnemyCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.enemycard.object.EnemyCard;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.DTO.ItemCardDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.cards.itemcard.object.ItemCard;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldOptionDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.object.FieldOption;

@Configuration
public class PlayedGameConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Customize the modelMapper configuration
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(EnemyCard.class, EnemyCardDTO.class).include(CardDTO.class);
        modelMapper.typeMap(ItemCard.class, ItemCardDTO.class).include(CardDTO.class);
        modelMapper.addConverter(ctx ->
                new AbstractConverter<FieldOption, FieldOptionDTO>() {
                    protected FieldOptionDTO convert(FieldOption source) {
                        return source.toDTO();
                    }
                }
        );
        return modelMapper;
    }
}
