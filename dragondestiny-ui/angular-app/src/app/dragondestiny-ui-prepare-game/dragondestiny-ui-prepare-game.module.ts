import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InvitePlayerComponent } from './invite-player/invite-player.component';
import { PrepareGameComponent } from './prepare-game.component';
import { SelectCharacterComponent } from './select-character/select-character.component';
import { StartGameComponent } from './start-game/start-game.component';
import { FormsModule } from '@angular/forms';
import { DragondestinyUiPrepareGameRoutingModule } from './dragondestiny-ui-prepare-game-routing.module';



@NgModule({
  declarations: [
    InvitePlayerComponent,
    PrepareGameComponent,
    SelectCharacterComponent,
    StartGameComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    DragondestinyUiPrepareGameRoutingModule
  ]
})
export class DragondestinyUiPrepareGameModule { }
