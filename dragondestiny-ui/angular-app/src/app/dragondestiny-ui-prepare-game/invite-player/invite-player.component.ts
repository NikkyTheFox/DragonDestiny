import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { User } from '../../interfaces/user/user/user';
import { SharedService } from '../../services/shared.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-invite-player',
  templateUrl: './invite-player.component.html',
  styleUrls: ['./invite-player.component.css']
})
export class InvitePlayerComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  gameId!: string;
  playerToInvite!: string;

  constructor(private userService: UserService, private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    this.gameId = this.shared.getRequest().game!.id;
  }

  sendInvite(){
    this.toDeleteSubscription.push(
      this.userService.getUserByLogin(this.playerToInvite).subscribe( (data: User) => {
        this.toDeleteSubscription.push(
          this.playedGameService.addPlayerToGameByLogin(this.gameId, data.login).subscribe()
        );
    },
      (error: any) => {
      window.alert('No such user. Please provide valid nickname.');
    })
    );
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
