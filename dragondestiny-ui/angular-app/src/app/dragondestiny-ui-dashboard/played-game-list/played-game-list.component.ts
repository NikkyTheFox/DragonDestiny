import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { GameDataService } from '../../services/game-data.service';
import { Router } from '@angular/router';
import { GameList } from '../../interfaces/user/game/game-list';
import { SharedService } from "../../services/shared.service";

@Component({
  selector: 'app-played-games-list',
  templateUrl: './played-game-list.component.html',
  styleUrls: ['./played-game-list.component.css']
})
export class PlayedGameListComponent implements OnInit{
  playerLogin!: string;
  gameIdsList!: GameList;

  constructor(private userService: UserService, private dataService: GameDataService, private router: Router, private shared: SharedService){

  }

  ngOnInit(){
    this.playerLogin = this.dataService.getPlayerLogin();
    this.userService.getUsersGames(this.playerLogin).subscribe((data: GameList)=>{
      this.gameIdsList = data;
    })
  }

  continueGame(clickedGameId: string){
    this.dataService.chosenGame = clickedGameId;
    this.shared.setRequestByID(this.dataService.chosenGame, this.dataService.getPlayerLogin());
    this.shared.dataLoaded.subscribe( () => {
      this.router.navigate(['/main']);
    });
  }
}
