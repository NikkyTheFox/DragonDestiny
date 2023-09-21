import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { RequestStructureGameidPlayerlogin } from '../../../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-character',
  templateUrl: './character.component.html',
  styleUrls: ['./character.component.css']
})
export class CharacterComponent implements OnChanges {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;
  character!: PlayedGameCharacter;

  constructor(private playedGameService: PlayedGameService) {
  }

  ngOnChanges(changes: SimpleChanges){
    this.playedGameService.getPlayersCharacter(this.requestStructure.gameId, this.requestStructure.playerLogin).subscribe( (data: PlayedGameCharacter) => {
      this.character = data;
    });
  }
}
