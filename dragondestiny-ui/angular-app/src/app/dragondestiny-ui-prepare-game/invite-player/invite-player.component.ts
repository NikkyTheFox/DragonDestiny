import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { User } from '../../interfaces/user/user/user';
import { SharedService } from "../../services/shared.service";

@Component({
  selector: 'app-invite-player',
  templateUrl: './invite-player.component.html',
  styleUrls: ['./invite-player.component.css']
})
export class InvitePlayerComponent implements OnInit{
  gameId!: string;
  playerToInvite!: string;

  constructor(private userService: UserService, private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    console.log("XD");
    const test = this.shared.getRequest();
    console.log(test.game);
    this.gameId = this.shared.getRequest().game!.id;

  }

  sendInvite(){
    this.userService.getUserByLogin(this.playerToInvite).subscribe( (data: User) => {
        this.playedGameService.addPlayerToGameByLogin(this.gameId, data.login).subscribe();
    },
      (error: any) => {
      window.alert('No such user. Please provide valid nickname.');
      });
  }
}
