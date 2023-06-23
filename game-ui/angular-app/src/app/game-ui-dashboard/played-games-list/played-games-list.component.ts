import { Component, Input } from '@angular/core';
import {GameUserService} from "../../services/game-user.service";
import {GameUserPlayedGame} from "../../interfaces/game-user/game-user-played-game";
import {GameDataService} from "../../services/game-data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-played-games-list',
  templateUrl: './played-games-list.component.html',
  styleUrls: ['./played-games-list.component.css']
})
export class PlayedGamesListComponent {
  // @ts-ignore
  @Input() playerLogin: string;
  gameIdsList: GameUserPlayedGame[];
  constructor(private userService: GameUserService, private dataService: GameDataService, private router: Router) {
    this.gameIdsList = [];
  }

  ngOnInit(){
    this.userService.getUserPlayedGames(this.playerLogin).subscribe((data: any)=>{
      this.gameIdsList = data.gameList;
    })
  }

  chooseGame(clickedGameId: string){
    this.dataService.chosenGame = clickedGameId;
    this.router.navigate(["/main"]);
  }
}
