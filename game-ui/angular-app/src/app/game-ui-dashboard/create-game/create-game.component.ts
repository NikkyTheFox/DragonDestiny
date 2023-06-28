import {Component, Input} from '@angular/core';
import {GamePlayedGameService} from "../../services/game-played-game-service";

import {GameDataService} from "../../services/game-data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.component.html',
  styleUrls: ['./create-game.component.css']
})
export class CreateGameComponent {
  // @ts-ignore
  @Input() playerLogin: string;
  selectedOption: number;

  constructor(private playedGameService: GamePlayedGameService, private dataService: GameDataService, private router: Router) {
    this.selectedOption = 1;
  }

  ngOnInit(){
    this.selectedOption = 1;
  }

  initializeGame() {
    this.playedGameService.initializeGame(this.selectedOption).subscribe((data: any)=>{
      this.playedGameService.addPlayerToGameByLogin(data.id, this.playerLogin).subscribe();
      this.dataService.chosenGame = data.id;
      this.router.navigate(["/preparegame"]);
    });
  }
}
