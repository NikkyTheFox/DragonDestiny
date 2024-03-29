import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './game-ui-playboard/main/main.component';
import { HeaderComponent } from './game-ui-header/header/header.component';
import { RighthandSidebarComponent } from './game-ui-playboard/righthand-sidebar/righthand-sidebar.component';
import { MainSectionComponent } from './game-ui-playboard/main-section/main-section.component';
import { FooterComponent } from './game-ui-footer/footer.component';
import { HeaderLeftSectionComponent } from './game-ui-header/header-left-section/header-left-section.component';
import { HeaderMidSectionComponent } from './game-ui-header/header-mid-section/header-mid-section.component';
import { HeaderRightSectionComponent } from './game-ui-header/header-right-section/header-right-section.component';
import { RighthandSidebarCharacterInfoComponent } from './game-ui-playboard/righthand-sidebar-character-info/righthand-sidebar-character-info.component';
import { RighthandSidebarCharacterInfoPortraitComponent } from './game-ui-playboard/righthand-sidebar-character-info-portrait/righthand-sidebar-character-info-portrait.component';
import { RighthandSidebarCharacterInfoStatisticsComponent } from './game-ui-playboard/righthand-sidebar-character-info-statistics/righthand-sidebar-character-info-statistics.component';
import { RighthandSidebarCharacterInfoStatisticsStatComponent } from './game-ui-playboard/righthand-sidebar-character-info-statistics-stat/righthand-sidebar-character-info-statistics-stat.component';
import { RighthandSidebarCardsDeckComponent } from './game-ui-playboard/righthand-sidebar-cards-deck/righthand-sidebar-cards-deck.component';
import { MainSectionBoardComponent } from './game-ui-playboard/main-section-board/main-section-board.component';
import { MainSectionAdditionalFieldComponent } from './game-ui-playboard/main-section-additional-field/main-section-additional-field.component';
import { MainSectionAdditionalFieldOtherCharactersComponent } from './game-ui-playboard/main-section-additional-field-other-characters/main-section-additional-field-other-characters.component';
import { MainSectionAdditionalFieldItemsComponent } from './game-ui-playboard/main-section-additional-field-items/main-section-additional-field-items.component';
import { MainSectionBoardRowComponent } from './game-ui-playboard/main-section-board-row/main-section-board-row.component';
import { MainSectionBoardFieldComponent } from './game-ui-playboard/main-section-board-field/main-section-board-field.component';
import { PlayedGamesListComponent } from './game-ui-dashboard/played-games-list/played-games-list.component';
import { SignupComponent } from './game-ui-login/signup/signup.component';
import { GameUiDashboardComponent } from './game-ui-dashboard/game-ui-dashboard/game-ui-dashboard.component';
import { GameUiDashboardSidebarComponent } from './game-ui-dashboard/game-ui-dashboard-sidebar/game-ui-dashboard-sidebar.component';
import { LoginInComponent } from './game-ui-login/login-in/login-in.component';
import { CreateGameComponent } from './game-ui-dashboard/create-game/create-game.component';
import { PrepareGameComponent } from './game-ui-prepare-game/prepare-game/prepare-game.component';
import { SelectCharacterComponent } from './game-ui-prepare-game/select-character/select-character.component';
import { InvitePlayerComponent } from './game-ui-prepare-game/invite-player/invite-player.component';
import { StartGameComponent } from './game-ui-prepare-game/start-game/start-game.component';
import { RighthandSidebarGameControlComponent } from './game-ui-playboard/righthand-sidebar-game-control/righthand-sidebar-game-control.component';
import { RighthandSidebarGameControlDiceComponent } from './game-ui-playboard/righthand-sidebar-game-control-dice/righthand-sidebar-game-control-dice.component';
import { RighthandSidebarGameControlConfirmComponent } from './game-ui-playboard/righthand-sidebar-game-control-confirm/righthand-sidebar-game-control-confirm.component';
import { JoinGameComponent } from './game-ui-dashboard/join-game/join-game.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    HeaderComponent,
    RighthandSidebarComponent,
    MainSectionComponent,
    FooterComponent,
    HeaderLeftSectionComponent,
    HeaderMidSectionComponent,
    HeaderRightSectionComponent,
    RighthandSidebarCharacterInfoComponent,
    RighthandSidebarCharacterInfoPortraitComponent,
    RighthandSidebarCharacterInfoStatisticsComponent,
    RighthandSidebarCharacterInfoStatisticsStatComponent,
    RighthandSidebarCardsDeckComponent,
    MainSectionBoardComponent,
    MainSectionAdditionalFieldComponent,
    MainSectionAdditionalFieldOtherCharactersComponent,
    MainSectionAdditionalFieldItemsComponent,
    MainSectionBoardRowComponent,
    MainSectionBoardFieldComponent,
    PlayedGamesListComponent,
    SignupComponent,
    GameUiDashboardComponent,
    GameUiDashboardSidebarComponent,
    LoginInComponent,
    CreateGameComponent,
    PrepareGameComponent,
    SelectCharacterComponent,
    InvitePlayerComponent,
    StartGameComponent,
    RighthandSidebarGameControlComponent,
    RighthandSidebarGameControlDiceComponent,
    RighthandSidebarGameControlConfirmComponent,
    JoinGameComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CommonModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
