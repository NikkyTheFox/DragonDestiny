import { Component, Input } from '@angular/core';
import { PlayedGameService } from '../../services/played-game/played-game-service';

import { GameDataService } from '../../services/game-data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.component.html',
  styleUrls: ['./create-game.component.css']
})
export class CreateGameComponent {
  @Input() playerLogin!: string;
  selectedOption: number = 1;

  constructor(private playedGameService: PlayedGameService, private dataService: GameDataService, private router: Router) {
  }

  initializeGame() {
    this.playedGameService.initializeGame(this.selectedOption).subscribe((data: any)=>{
      this.playedGameService.addPlayerToGameByLogin(data.id, this.playerLogin).subscribe();
      this.dataService.chosenGame = data.id;
      this.router.navigate(['/preparegame']);
    });
  }
}