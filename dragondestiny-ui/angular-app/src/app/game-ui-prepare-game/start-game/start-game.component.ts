import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {PlayedGameService} from "../../services/played-game-service";
import {PlayedGamePlayer} from "../../interfaces/game-played-game/played-game-player";

@Component({
  selector: 'app-start-game',
  templateUrl: './start-game.component.html',
  styleUrls: ['./start-game.component.css']
})
export class StartGameComponent {
  @Input() gameId!: string;

  constructor(private playedGameService: PlayedGameService, private router: Router) {
  }
  startGame() {
    this.playedGameService.getPlayers(this.gameId).subscribe( (data: any) => {
      let playerList: PlayedGamePlayer[] = data.playerList;
      let charactersFlag = true;
      playerList.forEach( (player: PlayedGamePlayer) => {
        if(this.isEmpty(player.character)){
          charactersFlag = false;
        }
      });
      if(charactersFlag){
        this.playedGameService.startGame(this.gameId).subscribe((data: any) => {
          this.router.navigate(["main"]);
        });
      }
      else {
        window.alert("Not every player has chosen a character");
      }
    });

  }

  isEmpty(character: any): boolean {
    // Check if character is null, undefined, or an empty object
    return character === null || character === undefined || Object.keys(character).length === 0;
  }
}
