import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './game-ui-playboard/main/main.component';
import { HeaderComponent } from './header/header.component';
import { RighthandSidebarComponent } from './game-ui-playboard/righthand-sidebar/righthand-sidebar.component';
import { MainSectionComponent } from './game-ui-playboard/main-section/main-section.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderLeftSectionComponent } from './header-left-section/header-left-section.component';
import { HeaderMidSectionComponent } from './header-mid-section/header-mid-section.component';
import { HeaderRightSectionComponent } from './header-right-section/header-right-section.component';
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
import { MainSectionBoardRowTopComponent } from './game-ui-playboard/main-section-board-row-top/main-section-board-row-top.component';
import { MainSectionBoardRowBotComponent } from './game-ui-playboard/main-section-board-row-bot/main-section-board-row-bot.component';
import { MainSectionBoardFieldComponent } from './game-ui-playboard/main-section-board-field/main-section-board-field.component';
import { MainSectionBoardFieldInnerComponent } from './game-ui-playboard/main-section-board-field-inner/main-section-board-field-inner.component';
import { PlayedGamesListComponent } from './game-ui-dashboard/played-games-list/played-games-list.component';
import { SignupComponent } from './game-ui-login/signup/signup.component';
import { GameUiDashboardComponent } from './game-ui-dashboard/game-ui-dashboard/game-ui-dashboard.component';
import { GameUiDashboardSidebarComponent } from './game-ui-dashboard/game-ui-dashboard-sidebar/game-ui-dashboard-sidebar.component';
import { LoginInComponent } from './game-ui-login/login-in/login-in.component';

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
    MainSectionBoardRowTopComponent,
    MainSectionBoardRowBotComponent,
    MainSectionBoardFieldComponent,
    MainSectionBoardFieldInnerComponent,
    PlayedGamesListComponent,
    SignupComponent,
    GameUiDashboardComponent,
    GameUiDashboardSidebarComponent,
    LoginInComponent
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
