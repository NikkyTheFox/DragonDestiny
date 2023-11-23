import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { GameDataService } from '../../services/game-data.service';
import { Router } from '@angular/router';
import { SharedService } from '../../services/shared.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.component.html',
  styleUrls: ['./create-game.component.css']
})
export class CreateGameComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];

  playerLogin!: string;
  selectedOption: number = 1;

  constructor(private playedGameService: PlayedGameService, private dataService: GameDataService, private shared: SharedService, private router: Router){

  }

  ngOnInit(){
    this.playerLogin = this.dataService.getPlayerLogin();
  }

  initializeGame(){
    this.toDeleteSubscription.push(
      this.playedGameService.initializeGame(this.selectedOption).subscribe( (data: any) => {
        this.addPlayer(data.id);
      })
    )
  }

  addPlayer(playerGameId: string){
    this.toDeleteSubscription.push(
      this.playedGameService.addPlayerToGameByLogin(playerGameId, this.playerLogin).subscribe( () => {
        this.dataService.chosenGame = playerGameId;
        this.shared.setRequestByID(playerGameId, this.playerLogin);
        this.redirectToGame();
      })
    )
  }

  redirectToGame(){
    this.toDeleteSubscription.push(
      this.shared.getDataLoadedEvent().subscribe( () => {
        this.router.navigate(['/preparegame']);
      })
    )
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
