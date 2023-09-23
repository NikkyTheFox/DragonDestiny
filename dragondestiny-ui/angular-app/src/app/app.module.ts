import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DragondestinyUiHeaderModule } from './dragondestiny-ui-header/dragondestiny-ui-header.module';
import { DragondestinyUiDashboardModule } from './dragondestiny-ui-dashboard/dragondestiny-ui-dashboard.module';
import { DragondestinyUiFooterModule } from './dragondestiny-ui-footer/dragondestiny-ui-footer.module';
import { DragondestinyUiLoginModule } from './dragondestiny-ui-login/dragondestiny-ui-login.module';
import { DragondestinyUiPlayboardModule } from './dragondestiny-ui-playboard/dragondestiny-ui-playboard.module';
import { DragondestinyUiPrepareGameModule } from './dragondestiny-ui-prepare-game/dragondestiny-ui-prepare-game.module';
import { GameEngineService } from './services/game-engine/game-engine.service';
import { PlayedGameService } from './services/played-game/played-game-service';
import { UserService } from './services/user/user.service';
import { GameDataService } from './services/game-data.service';
import { SharedService } from './services/shared.service';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CommonModule,
    FormsModule,
    DragondestinyUiHeaderModule,
    DragondestinyUiDashboardModule,
    DragondestinyUiFooterModule,
    DragondestinyUiLoginModule,
    DragondestinyUiPlayboardModule,
    DragondestinyUiPrepareGameModule
  ],
  providers: [
    GameEngineService,
    PlayedGameService,
    UserService,
    GameDataService,
    SharedService],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
