import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainComponent } from './main.component';
import { GameScreenComponent } from './game-screen/game-screen.component';
import { ContextBarComponent } from './game-screen/context-bar/context-bar.component';
import { ContextBarItemsComponent } from './game-screen/context-bar/context-bar-items/context-bar-items.component';
import { ContextBarCharactersComponent } from './game-screen/context-bar/context-bar-characters/context-bar-characters.component';
import { BoardComponent }  from './game-screen/board/board.component';
import { BoardFieldComponent } from './game-screen/board/board-row/board-field/board-field.component';
import { PawnComponent } from './game-screen/pawn/pawn.component';
import { BoardRowComponent } from './game-screen/board/board-row/board-row.component';
import { NotificationComponent } from './game-screen/notification/notification.component';
import { NotificationDrawCardComponent } from './game-screen/notification/notification-draw-card/notification-draw-card.component';
import { NotificationDieComponent } from './game-screen/notification/notification-die/notification-die.component';
import { NotificationFightEnemyComponent } from './game-screen/notification/notification-fight-enemy/notification-fight-enemy.component';
import { NotificationAttackComponent } from './game-screen/notification/notification-attack/notification-attack.component';
import { NotificationDefendComponent } from './game-screen/notification/notification-defend/notification-defend.component';
import { NotificationUpdateComponent } from './game-screen/notification/notification-update/notification-update.component';
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
    BoardRowComponent,
    NotificationComponent,
    NotificationDrawCardComponent,
    NotificationDieComponent,
    NotificationFightEnemyComponent,
    NotificationAttackComponent,
    NotificationDefendComponent,
    NotificationUpdateComponent
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
export class MainModule{

}
