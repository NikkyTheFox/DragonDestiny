import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { GamePlayerRequest } from '../../../../interfaces/game-player-request';
import { SharedService } from "../../../../services/shared.service";

@Component({
  selector: 'app-character',
  templateUrl: './character.component.html',
  styleUrls: ['./character.component.css']
})
export class CharacterComponent implements OnInit, OnChanges{
  requestStructure!: GamePlayerRequest;
  character!: PlayedGameCharacter;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
  }

  ngOnChanges(changes: SimpleChanges){
    this.playedGameService.getPlayersCharacter(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: PlayedGameCharacter) => {
      this.character = data;
    });
  }
}
