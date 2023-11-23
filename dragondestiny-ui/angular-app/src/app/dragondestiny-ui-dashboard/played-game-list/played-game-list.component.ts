import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { GameDataService } from '../../services/game-data.service';
import { Router } from '@angular/router';
import { GameList } from '../../interfaces/user/game/game-list';
import { SharedService } from '../../services/shared.service';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { Game } from '../../interfaces/user/game/game';
import { PlayedGame } from '../../interfaces/played-game/played-game/played-game';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-played-games-list',
  templateUrl: './played-game-list.component.html',
  styleUrls: ['./played-game-list.component.css']
})
export class PlayedGameListComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  playerLogin!: string;
  gameList: PlayedGame[] = [];

  constructor(private userService: UserService,
              private dataService: GameDataService,
              private router: Router,
              private shared: SharedService,
              private playedGameService: PlayedGameService){

  }

  ngOnInit(){
    this.playerLogin = this.dataService.getPlayerLogin();
    this.fetchUserGames();
  }

  fetchUserGames(){
    this.toDeleteSubscription.push(
      this.userService.getUsersGames(this.playerLogin).subscribe((data: GameList)=>{
        data.gameList.forEach( (game: Game) => {
          this.fetchGameData(game.id);
        });
      })
    );
  }

  fetchGameData(playedGameId: string){
    this.toDeleteSubscription.push(
      this.playedGameService.getGame(playedGameId).subscribe( (data: PlayedGame) => {
        this.gameList.push(data);
      })
    );
  }

  continueGame(clickedGameId: string){
    this.dataService.chosenGame = clickedGameId;
    this.shared.setRequestByID(this.dataService.chosenGame, this.dataService.getPlayerLogin());
    this.toDeleteSubscription.push(
      this.shared.getDataLoadedEvent().subscribe( () => {
        this.router.navigate(['/main']);
      })
    );
  }

  joinGame(clickedGameId: string){
    this.dataService.chosenGame = clickedGameId;
    this.shared.setRequestByID(clickedGameId, this.playerLogin);
    this.toDeleteSubscription.push(
      this.shared.getDataLoadedEvent().subscribe( () => {
        this.router.navigate(['/preparegame']);
      })
    );
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
