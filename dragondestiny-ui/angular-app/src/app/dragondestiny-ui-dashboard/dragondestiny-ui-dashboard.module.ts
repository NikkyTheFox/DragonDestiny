import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateGameComponent } from './create-game/create-game.component';
import { DashboardComponent} from './dashboard.component';
import { DashboardSidebarComponent } from './dashboard-sidebar/dashboard-sidebar.component';
import { JoinGameComponent } from './join-game/join-game.component';
import { PlayedGameListComponent } from './played-game-list/played-game-list.component';
import { FormsModule } from '@angular/forms';
import { DragondestinyUiDashboardRoutingModule } from './dragondestiny-ui-dashboard-routing.module';

@NgModule({
  declarations: [
    CreateGameComponent,
    DashboardComponent,
    DashboardSidebarComponent,
    JoinGameComponent,
    PlayedGameListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    DragondestinyUiDashboardRoutingModule
  ]
})
export class DragondestinyUiDashboardModule{

}
