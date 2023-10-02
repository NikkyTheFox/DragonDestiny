import { Component, OnInit } from '@angular/core';
import { PlayedGameService } from '../../services/played-game/played-game-service';

import { GameDataService } from '../../services/game-data.service';
import { Router } from '@angular/router';
import {SharedService} from "../../services/shared.service";

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.component.html',
  styleUrls: ['./create-game.component.css']
})
export class CreateGameComponent implements OnInit{
  playerLogin!: string;
  selectedOption: number = 1;

  constructor(private playedGameService: PlayedGameService, private dataService: GameDataService, private shared: SharedService, private router: Router){

  }

  ngOnInit(){
    this.playerLogin = this.dataService.getPlayerLogin();
  }

  initializeGame(){
    this.playedGameService.initializeGame(this.selectedOption).subscribe( (data: any) => {
      this.playedGameService.addPlayerToGameByLogin(data.id, this.playerLogin).subscribe( () => {
        this.dataService.chosenGame = data.id;
        this.shared.setRequestByID(data.id, this.playerLogin);
        this.router.navigate(['/preparegame']);
        }
      );
    });
  }
}
