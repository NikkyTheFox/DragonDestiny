import {Component, Input, SimpleChanges} from '@angular/core';
import {GamePlayedGameService} from "../../services/game-played-game-service";
import {PlayedGameCharacter} from "../../interfaces/game-played-game/played-game-character";

@Component({
  selector: 'app-righthand-sidebar-character-info',
  templateUrl: './righthand-sidebar-character-info.component.html',
  styleUrls: ['./righthand-sidebar-character-info.component.css']
})
export class RighthandSidebarCharacterInfoComponent {
  @Input() gameId!: string;
  @Input() playerId!: string;
  character: PlayedGameCharacter = {
    id: 0,
    initialStrength: 0,
    initialHealth: 0,
    additionalHealth: 0,
    additionalStrength: 0,
    positionField: null
  }

  constructor(private playedGameService: GamePlayedGameService) {
  }

  ngOnChanges(changes: SimpleChanges){
    this.playedGameService.getPlayerCharacter(this.gameId, this.playerId).subscribe( (data: PlayedGameCharacter) => {
      this.character = data;
    });
  }
}
