import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './sidebar.component';
import { DeckComponent } from './card-deck/deck.component';
import { CharacterComponent } from './character/character.component';
import { GameControlsComponent } from './game-controls/game-controls.component';
import { GameControlsConfirmComponent } from './game-controls/game-controls-end-turn/game-controls-confirm.component';
import { GameControlsDiceComponent } from './game-controls/game-controls-dice/game-controls-dice.component';
import { GameControlsOptionsComponent } from './game-controls/game-controls-options/game-controls-options.component';
import { CharacterPortraitComponent } from './character/character-portrait/character-portrait.component';
import { CharacterStatisticsComponent } from './character/character-statistics/character-statistics.component';
import { CharacterStatisticsStatComponent } from './character/character-statistics/character-statistics-stat/character-statistics-stat.component';

@NgModule({
  declarations: [
    SidebarComponent,
    DeckComponent,
    CharacterComponent,
    CharacterPortraitComponent,
    CharacterStatisticsComponent,
    CharacterStatisticsStatComponent,
    GameControlsComponent,
    GameControlsConfirmComponent,
    GameControlsDiceComponent,
    GameControlsOptionsComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    SidebarComponent,
    DeckComponent,
    CharacterComponent,
    CharacterPortraitComponent,
    CharacterStatisticsComponent,
    CharacterStatisticsStatComponent,
    GameControlsComponent,
    GameControlsConfirmComponent,
    GameControlsDiceComponent,
    GameControlsOptionsComponent
  ]
})
export class SidebarModule { }
