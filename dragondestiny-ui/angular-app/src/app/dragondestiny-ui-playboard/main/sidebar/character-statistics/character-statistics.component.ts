import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { Player } from '../../../../interfaces/played-game/player/player';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-character-statistics',
  templateUrl: './character-statistics.component.html',
  styleUrls: ['./character-statistics.component.css']
})
export class CharacterStatisticsComponent implements OnInit, OnChanges{
  @Input() player!: Player;
  @Input() requestStructure!: GameDataStructure;

  character!: PlayedGameCharacter;

  constructor(private playedGameService: PlayedGameService){
  }

  ngOnInit(){
    this.character = this.player.character;
  }

  ngOnChanges(changes: SimpleChanges): void {
      this.character = this.player.character;
  }
}
