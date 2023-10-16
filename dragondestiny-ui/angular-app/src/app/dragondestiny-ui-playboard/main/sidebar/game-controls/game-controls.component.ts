import {Component, Input, OnInit} from '@angular/core';
import {PlayedGameService} from "../../../../services/played-game/played-game-service";
import {SharedService} from "../../../../services/shared.service";
import {GameDataStructure} from "../../../../interfaces/game-data-structure";
import {Round} from "../../../../interfaces/played-game/round/round";

@Component({
  selector: 'app-game-controls',
  templateUrl: './game-controls.component.html',
  styleUrls: ['./game-controls.component.css']
})
export class GameControlsComponent implements OnInit{
  requestStructure!: GameDataStructure;
  isActive: boolean = false;
  activePlayerLogin!: string;
  activePlayerCharacter!: number;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService){

  }
  ngOnInit() {
    this.requestStructure = this.shared.getRequest();
    this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
      this.activePlayerLogin = data.activePlayer.login;
      this.activePlayerCharacter = data.activePlayer.character.id;
      this.isActive = this.activePlayerLogin === this.requestStructure.player!.login;
    });
  }
}
