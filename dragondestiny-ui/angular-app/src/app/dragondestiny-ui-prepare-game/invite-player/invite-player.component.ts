import { Component, Input } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { PlayedGameService } from '../../services/played-game/played-game-service';
import { User } from '../../interfaces/user/user/user';

@Component({
  selector: 'app-invite-player',
  templateUrl: './invite-player.component.html',
  styleUrls: ['./invite-player.component.css']
})
export class InvitePlayerComponent {
  @Input() gameId!: string;
  playerToInvite!: string;

  constructor(private userService: UserService, private playedGameService: PlayedGameService) {
  }

  sendInvite() {
    this.userService.getUserByLogin(this.playerToInvite).subscribe( (data: User) => {
        this.playedGameService.addPlayerToGameByLogin(this.gameId, data.login).subscribe();
    },
      (error: any)=>{
      window.alert('No such user. Please provide valid nickname.');
      });
  }
}
