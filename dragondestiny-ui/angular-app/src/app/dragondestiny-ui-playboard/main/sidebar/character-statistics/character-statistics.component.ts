import { Component, Input, OnInit } from '@angular/core';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { Player } from "../../../../interfaces/played-game/player/player";
import { PlayedGameService } from "../../../../services/played-game/played-game-service";
import { GameDataStructure } from "../../../../interfaces/game-data-structure";

@Component({
  selector: 'app-character-statistics',
  templateUrl: './character-statistics.component.html',
  styleUrls: ['./character-statistics.component.css']
})
export class CharacterStatisticsComponent implements OnInit{
  @Input() character!: PlayedGameCharacter;
  @Input() requestStructure!: GameDataStructure;
  player!: Player;

  constructor(private playedGameService: PlayedGameService){
  }
   ngOnInit(){
    this.playedGameService.getPlayer(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: Player) => {
      this.player = data;
    });
   }
}
