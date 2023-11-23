import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { SharedService } from '../../../../services/shared.service';
import { Subscription } from 'rxjs';
import { Player } from 'src/app/interfaces/played-game/player/player';

@Component({
  selector: 'app-character',
  templateUrl: './character.component.html',
  styleUrls: ['./character.component.css']
})
export class CharacterComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  player!: Player;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.fetchPlayer();
    this.toDeleteSubscription.push(
      this.shared.getUpdateStatisticsEvent().subscribe( () => {
        this.fetchPlayer();
      })
    );
  }

  fetchPlayer(){
    this.toDeleteSubscription.push(
      this.playedGameService.getPlayer(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: Player) => {
        this.player = data;
      })
    );
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
