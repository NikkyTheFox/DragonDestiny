import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainComponent } from './main.component';
import { GameScreenComponent } from './game-screen/game-screen.component';
import { ContextBarComponent } from './game-screen/context-bar/context-bar.component';
import { ContextBarItemsComponent } from './game-screen/context-bar-items/context-bar-items.component';
import { ContextBarCharactersComponent } from './game-screen/context-bar-characters/context-bar-characters.component';
import { BoardComponent }  from './game-screen/board/board.component';
import { BoardFieldComponent } from './game-screen/board-field/board-field.component';
import { PawnComponent } from './game-screen/pawn/pawn.component';
import { BoardRowComponent } from './game-screen/board-row/board-row.component';
import { SidebarModule } from './sidebar/sidebar.module';



@NgModule({
  declarations: [
    MainComponent,
    GameScreenComponent,
    ContextBarComponent,
    ContextBarItemsComponent,
    ContextBarCharactersComponent,
    BoardComponent,
    BoardFieldComponent,
    PawnComponent,
    BoardRowComponent
  ],
  imports: [
    CommonModule,
    SidebarModule
  ],
  exports: [
    MainComponent,
    GameScreenComponent,
    ContextBarComponent,
    ContextBarItemsComponent,
    ContextBarCharactersComponent,
    BoardComponent,
    BoardFieldComponent,
    PawnComponent,
    BoardRowComponent
  ]
})
export class MainModule { }
