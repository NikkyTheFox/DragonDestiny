import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';


import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './main/main.component';
import { HeaderComponent } from './header/header.component';
import { RighthandSidebarComponent } from './righthand-sidebar/righthand-sidebar.component';
import { MainSectionComponent } from './main-section/main-section.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderLeftSectionComponent } from './header-left-section/header-left-section.component';
import { HeaderMidSectionComponent } from './header-mid-section/header-mid-section.component';
import { HeaderRightSectionComponent } from './header-right-section/header-right-section.component';
import { RighthandSidebarCharacterInfoComponent } from './righthand-sidebar-character-info/righthand-sidebar-character-info.component';
import { RighthandSidebarCharacterInfoPortraitComponent } from './righthand-sidebar-character-info-portrait/righthand-sidebar-character-info-portrait.component';
import { RighthandSidebarCharacterInfoStatisticsComponent } from './righthand-sidebar-character-info-statistics/righthand-sidebar-character-info-statistics.component';
import { RighthandSidebarCharacterInfoStatisticsStatComponent } from './righthand-sidebar-character-info-statistics-stat/righthand-sidebar-character-info-statistics-stat.component';
import { RighthandSidebarCardsDeckComponent } from './righthand-sidebar-cards-deck/righthand-sidebar-cards-deck.component';
import { MainSectionBoardComponent } from './main-section-board/main-section-board.component';
import { MainSectionAdditionalFieldComponent } from './main-section-additional-field/main-section-additional-field.component';
import { MainSectionAdditionalFieldOtherCharactersComponent } from './main-section-additional-field-other-characters/main-section-additional-field-other-characters.component';
import { MainSectionAdditionalFieldItemsComponent } from './main-section-additional-field-items/main-section-additional-field-items.component';
import { MainSectionBoardRowComponent } from './main-section-board-row/main-section-board-row.component';
import { MainSectionBoardRowTopComponent } from './main-section-board-row-top/main-section-board-row-top.component';
import { MainSectionBoardRowBotComponent } from './main-section-board-row-bot/main-section-board-row-bot.component';
import { MainSectionBoardFieldComponent } from './main-section-board-field/main-section-board-field.component';
import { MainSectionBoardFieldInnerComponent } from './main-section-board-field-inner/main-section-board-field-inner.component';

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
    MainSectionBoardFieldInnerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
