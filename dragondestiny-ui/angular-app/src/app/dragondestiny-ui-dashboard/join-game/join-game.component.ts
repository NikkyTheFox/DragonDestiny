import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { PlayedGame } from '../../interfaces/played-game/played-game/played-game';
import { Router } from '@angular/router';
import { GameDataService } from '../../services/game-data.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-join-game',
  templateUrl: './join-game.component.html',
  styleUrls: ['./join-game.component.css']
})
export class JoinGameComponent implements OnInit, OnDestroy{
  gameSubscription!: Subscription;
  addPlayerSubscription!: Subscription;
  toDeleteSubscription: Subscription[] = [];
  playerLogin!: string;
  gameId!: string;

  constructor(private playedGameService: PlayedGameService, private router: Router, private dataService: GameDataService){

  }

  ngOnInit(){
    this.playerLogin = this.dataService.getPlayerLogin();
  }

  joinGame(){
    this.gameSubscription = this.playedGameService.getGame(this.gameId).subscribe( (data: PlayedGame) => {
      if(data.isStarted){
        window.alert('Game has already started, you cannot join it.');
      }
      else{
        this.addPlayerSubscription = this.playedGameService.addPlayerToGameByLogin(this.gameId, this.playerLogin).subscribe();
        this.dataService.chosenGame = this.gameId;
        this.router.navigate(['preparegame']);
      }
    },
      (error: any) => {
      window.alert('Game not found');
    });
  }

  ngOnDestroy(): void {
      this.addPlayerSubscription?.unsubscribe();
      this.gameSubscription?.unsubscribe();
  }
}
