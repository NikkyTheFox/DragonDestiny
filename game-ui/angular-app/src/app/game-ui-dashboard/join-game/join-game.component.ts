import {Component, Input} from '@angular/core';
import {GamePlayedGameService} from "../../services/game-played-game-service";
import {PlayedGame} from "../../interfaces/game-played-game/played-game";
import {Router} from "@angular/router";
import {GameDataService} from "../../services/game-data.service";

@Component({
  selector: 'app-join-game',
  templateUrl: './join-game.component.html',
  styleUrls: ['./join-game.component.css']
})
export class JoinGameComponent {
  @Input() playerLogin!: string;
  gameId: string;

  constructor(private playedGameService: GamePlayedGameService, private router: Router, private dataService: GameDataService) {
    this.gameId = "";
  }

  joinGame() {
    this.playedGameService.getGame(this.gameId).subscribe( (data: PlayedGame) => {
      this.playedGameService.addPlayerToGameByLogin(this.gameId, this.playerLogin).subscribe();
      this.dataService.chosenGame = this.gameId;
      this.router.navigate(["preparegame"]);
    },
      (error: any) => {
      window.alert("Game not found");
    });
  }
}
