import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { Player } from '../../interfaces/played-game/player/player';
import { SharedService } from "../../services/shared.service";
import { Subscription } from 'rxjs';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';

@Component({
  selector: 'app-start-game',
  templateUrl: './start-game.component.html',
  styleUrls: ['./start-game.component.css']
})
export class StartGameComponent implements OnInit, OnDestroy{
  gameId!: string;
  toDeleteSubscription: Subscription[] = []
  webSocketMessagePipe!: Subscription;
  messageData!: NotificationMessage;

  constructor(private playedGameService: PlayedGameService, private router: Router, private shared: SharedService){

  }

  ngOnInit(){
    this.gameId = this.shared.getGame()!.id;
    this.webSocketMessagePipe = this.shared.getSocketMessage().subscribe( (data: any) => {

      this.messageData = this.shared.parseNotificationMessage(data);
      if(this.messageData.notificationOption === NotificationEnum.GAME_STARTED){
        this.router.navigate(['main']);
      }
    });
  }

  //to check if logic is not already in PlayedGameService.java
  startGame(){
    this.toDeleteSubscription.push(
      this.playedGameService.getPlayers(this.gameId).subscribe( (data: any) => {
        let playerList: Player[] = data.playerList;
        let charactersFlag = true;
        playerList.forEach( (player: Player) => {
          if(this.isEmpty(player.character)){
            charactersFlag = false;
          }
        });
        if(charactersFlag){
          this.toDeleteSubscription.push(
            this.playedGameService.startGame(this.gameId).subscribe(() => {
            // this.router.navigate(['main']);
            })
          )
        }
        else {
          window.alert('Not every player has chosen a character');
        }
      })
    )
  }

  isEmpty(character: any){
    // Check if character is null, undefined, or an empty object
    return character === null || character === undefined || Object.keys(character).length === 0;
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
    this.webSocketMessagePipe?.unsubscribe();
  }
}
