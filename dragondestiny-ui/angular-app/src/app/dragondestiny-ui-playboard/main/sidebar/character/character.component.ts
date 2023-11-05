import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { SharedService } from "../../../../services/shared.service";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-character',
  templateUrl: './character.component.html',
  styleUrls: ['./character.component.css']
})
export class CharacterComponent implements OnInit, OnDestroy{
  equipEventSubscription!: Subscription;
  characterSubscription!: Subscription;

  requestStructure!: GameDataStructure;
  character!: PlayedGameCharacter;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.handleCharacter();
    this.equipEventSubscription = this.shared.getEquipItemCardClickEvent().subscribe( () => {
      this.handleCharacter();
    });
  }

  handleCharacter(){
    this.characterSubscription = this.playedGameService.getPlayersCharacter(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: PlayedGameCharacter) => {
      this.character = data;
    });
  }

  ngOnDestroy(): void {
    this.characterSubscription?.unsubscribe();
    this.equipEventSubscription?.unsubscribe();
  }
}
