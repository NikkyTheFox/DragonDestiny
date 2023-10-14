import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { Player } from '../../interfaces/played-game/player/player';
import { SharedService } from "../../services/shared.service";

@Component({
  selector: 'app-start-game',
  templateUrl: './start-game.component.html',
  styleUrls: ['./start-game.component.css']
})
export class StartGameComponent implements OnInit{
 gameId!: string;

  constructor(private playedGameService: PlayedGameService, private router: Router, private shared: SharedService){

  }

  ngOnInit(){
    this.gameId = this.shared.getGame()!.id;
  }

  //to check if logic is not already in PlayedGameService.java
  startGame(){
    this.playedGameService.getPlayers(this.gameId).subscribe( (data: any) => {
      let playerList: Player[] = data.playerList;
      let charactersFlag = true;
      playerList.forEach( (player: Player) => {
        if(this.isEmpty(player.character)){
          charactersFlag = false;
        }
      });
      if(charactersFlag){
        this.playedGameService.startGame(this.gameId).subscribe(() => {
          this.router.navigate(['main']);
        });
      }
      else {
        window.alert('Not every player has chosen a character');
      }
    });

  }

  isEmpty(character: any){
    // Check if character is null, undefined, or an empty object
    return character === null || character === undefined || Object.keys(character).length === 0;
  }
}
