import {Component, Input} from '@angular/core';
import {GameUserService} from "../../services/game-user.service";
import {GamePlayedGameService} from "../../services/game-played-game-service";
import {GameUserShort} from "../../interfaces/game-user/game-user-short";

@Component({
  selector: 'app-invite-player',
  templateUrl: './invite-player.component.html',
  styleUrls: ['./invite-player.component.css']
})
export class InvitePlayerComponent {
  @Input() gameId!: string;
  playerToInvite: string;

  constructor(private userService: GameUserService, private playedGameService: GamePlayedGameService) {
    this.playerToInvite = "";
  }

  sendInvite() {
    this.userService.getUserByLogin(this.playerToInvite).subscribe( (data: GameUserShort) => {
        this.playedGameService.addPlayerToGameByLogin(this.gameId, data.login).subscribe();
    },
      (error: any)=>{
      window.alert("No such user. Please provide valid nickname.");
      });
  }
}
