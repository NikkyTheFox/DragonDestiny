import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Character } from '../../../../interfaces/game-engine/character/character';
import { PlayedGameCharacter } from '../../../../interfaces/played-game/character/character';
import { GameEngineService } from '../../../../services/game-engine/game-engine.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-character-portrait',
  templateUrl: './character-portrait.component.html',
  styleUrls: ['./character-portrait.component.css']
})
export class CharacterPortraitComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];

  @Input() character!: PlayedGameCharacter;
  gameEngineCharacter!: Character;

  constructor(private gameEngineService: GameEngineService){
  }

  ngOnInit(){
    this.toDeleteSubscription.push(
      this.gameEngineService.getCharacter(this.character.id).subscribe((data: Character) => {
        this.gameEngineCharacter = data;
      })
    );
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });   
  }
}
