package pl.edu.pg.eti.dragondestiny.playedgame.field.object;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldOptionDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.field.DTO.FieldOptionEnum;
import pl.edu.pg.eti.dragondestiny.playedgame.player.DTO.PlayerDTO;
import pl.edu.pg.eti.dragondestiny.playedgame.player.object.Player;


/**
 * Represent actions possible to take while standing on field.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum FieldOption {
    TAKE_ONE_CARD(FieldOptionEnum.TAKE_ONE_CARD, 1, 0, null),
    TAKE_TWO_CARDS(FieldOptionEnum.TAKE_TWO_CARDS, 2, 0, null),
    LOSE_ONE_ROUND(FieldOptionEnum.LOSE_ONE_ROUND, 0, 1, null),
    LOSE_TWO_ROUNDS(FieldOptionEnum.LOSE_TWO_ROUNDS, 0, 2, null),
    BRIDGE_FIELD(FieldOptionEnum.BRIDGE_FIELD, 0, 0, null),
    BOSS_FIELD(FieldOptionEnum.BOSS_FIELD, 0, 0, null),
    FIGHT_WITH_PLAYER(FieldOptionEnum.FIGHT_WITH_PLAYER, 0, 0, null),
    FIGHT_WITH_ENEMY_ON_FIELD(FieldOptionEnum.FIGHT_WITH_ENEMY_ON_FIELD, 0, 0, null);

    public FieldOptionEnum fieldOptionEnum;
    public Integer numOfCardsToTake;
    public Integer numOfTurnsToBlock;
    public Player enemyPlayer;

    public FieldOptionDTO toDTO(ModelMapper modelMapper) {
        return new FieldOptionDTO(fieldOptionEnum, numOfCardsToTake, numOfTurnsToBlock, enemyPlayer != null ? modelMapper.map(enemyPlayer, PlayerDTO.class) : null);
    }
}
